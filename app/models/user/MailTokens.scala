package models.user

import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime
import securesocial.core.providers.MailToken
import slick.lifted.ProvenShape

import scala.slick.driver.MySQLDriver.simple._


/**
 * Created by maximcherkasov on 01.05.15.
 */
class MailTokens(tag: Tag) extends Table[MailToken](tag, "token") {

  def uuid = column[String]("uuid")

  def email = column[String]("email")

  def creationTime = column[DateTime]("creationTime")

  def expirationTime = column[DateTime]("expirationTime")

  def isSignUp = column[Boolean]("isSignUp")

  def * : ProvenShape[MailToken] = {
    val shapedValue = (uuid, email, creationTime, expirationTime, isSignUp).shaped

    shapedValue.<>({
      tuple =>
        MailToken(
          uuid = tuple._1,
          email = tuple._2,
          creationTime = tuple._3,
          expirationTime = tuple._4,
          isSignUp = tuple._5
        )
    }, {
      (t: MailToken) =>
        Some {
          (t.uuid,
            t.email,
            t.creationTime,
            t.expirationTime,
            t.isSignUp
            )
        }
    })
  }
}