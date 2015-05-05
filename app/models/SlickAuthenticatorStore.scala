package models

/**
 * Created by maximcherkasov on 30.04.15.
 */

import models.UserTableQueries.{users, userAuthenticators}
import play.api.Logger
import securesocial.core.authenticator.{Authenticator, CookieAuthenticator, HttpHeaderAuthenticator}
import scala.concurrent.Future
import scala.reflect.ClassTag
import slick.driver.MySQLDriver.simple._
import scala.concurrent.ExecutionContext.Implicits.global

class SlickAuthenticatorStore[A <: Authenticator[BasicUser]] extends securesocial.core.authenticator.AuthenticatorStore[A]
with WithDefaultSession {
  val logger: Logger = Logger(this.getClass)

  override def find(id: String)(implicit ct: ClassTag[A]): Future[Option[A]] = withSession {
    implicit session =>
    Future successful {
      logger.info("Find authenticator")

      userAuthenticators.filter(_.id === id).firstOption match {
        case Some(userAuthenticator) =>
          users.filter(_.id === userAuthenticator.userId).firstOption match {
            case Some(sbu) =>
              val basicUser = sbu.basicUser
              ct.runtimeClass.getSimpleName match {
                case "CookieAuthenticator" =>
                  Option(
                    CookieAuthenticator(
                      userAuthenticator.id,
                      basicUser,
                      userAuthenticator.expirationDate,
                      userAuthenticator.lastUsed,
                      userAuthenticator.creationDate,
                      this.asInstanceOf[SlickAuthenticatorStore[CookieAuthenticator[BasicUser]]]
                    ).asInstanceOf[A]
                  )
                case "HttpHeaderAuthenticator" =>
                  Option(
                    HttpHeaderAuthenticator(
                      userAuthenticator.id,
                      basicUser,
                      userAuthenticator.expirationDate,
                      userAuthenticator.lastUsed,
                      userAuthenticator.creationDate,
                      this.asInstanceOf[SlickAuthenticatorStore[HttpHeaderAuthenticator[BasicUser]]]
                    ).asInstanceOf[A]
                  )
                case _ => None
              }
            case None => None
          }
        case None => None
      }
    }
  }
  override def delete(id: String): Future[Unit] = withSession {
    implicit session =>
      Future successful {
      userAuthenticators.filter(ua => ua.id === id).delete
      ()
    }
  }

  override def save(authenticator: A, timeoutInSeconds: Int): Future[A] = withSession {
    implicit session =>
      Future successful {
        val userAuthenticator: UserAuthenticator = UserAuthenticator(
          authenticator.id,
          authenticator.user.main.userId,
          authenticator.expirationDate,
          authenticator.lastUsed,
          authenticator.creationDate
        )

        userAuthenticators.filter(_.id === authenticator.id).firstOption match {
          case Some(ua) =>
            userAuthenticators.update(userAuthenticator)
          case None =>
            userAuthenticators += userAuthenticator
        }

        authenticator
      }
  }
}
