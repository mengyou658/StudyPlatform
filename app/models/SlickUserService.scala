package models

//import models.UserTableQueries.profiles

import models.UserTableQueries._
import org.joda.time.DateTime
import com.github.tototoshi.slick.MySQLJodaSupport._
import play.api.Logger
import securesocial.core._
import securesocial.core.providers.{MailToken, UsernamePasswordProvider => UserPass}
import securesocial.core.services.{SaveMode, UserService}
import slick.driver.MySQLDriver.simple._

import scala.concurrent.Future


/**
 * Created by maximcherkasov on 28.04.15.
 */


class SlickUserService extends UserService[BasicUser] with  WithDefaultSession {
  val logger = Logger(this.getClass)

  override def find(providerId: String, userId: String): Future[Option[BasicProfile]] = withSession {
    implicit session =>
    Future successful {
      logger.info("find(providerId: String, userId: String)")

      profiles.filter(sp => sp.providerId === providerId && sp.userId === userId)
        .firstOption
        .map(sp => sp.basicProfile)
    }
  }

  override def findByEmailAndProvider(email: String, providerId: String): Future[Option[BasicProfile]] = withSession {
    implicit session =>
      Future successful {
      logger.info("findByEmailAndProvider(email: String, providerId: String)")

      profiles
        .filter(sp => sp.email === email && sp.providerId === providerId)
        .firstOption
        .map(sp => sp.basicProfile)
    }
  }

  override def deleteToken(uuid: String): Future[Option[MailToken]] = withSession {
    implicit session =>
    Future successful {
      logger.info("deleteToken(uuid: String)")

      mailTokens.filter(_.uuid === uuid).firstOption.map(mt => {
        mailTokens.filter(_.uuid === uuid).delete
        mt
      })
    }
  }

  override def link(current: BasicUser, to: BasicProfile): Future[BasicUser] = withSession {
    implicit session =>
      Future successful {
//        users.filter(_.id === current.main.userId).firstOption
        if (current.identities.exists(i => i.providerId == to.providerId && i.userId == to.userId)) {
          current
        } else {
          logger.info("!!!: " + to.providerId)
          val profile = to.copy(userId = current.main.userId)
          val oAuth1InfoId = profile.oAuth1Info.map(o1 =>
            (oauth1s returning oauth1s.map(_.id)) += OAuth1(None, o1.token, o1.secret)
          )
          val oAuth2InfoId = profile.oAuth2Info.map(o2 =>
            (oauth2s returning oauth2s.map(_.id)) += OAuth2(None, o2.accessToken, o2.tokenType, o2.expiresIn, o2.refreshToken)
          )
          val passwordInfoId = profile.passwordInfo.map(p => {
            (passwords returning passwords.map(_.id)) += Password(None, p.hasher, p.password, p.salt)
          })
          val profileId = (profiles returning profiles.map(_.id)) += Profile(
            None,
            profile.providerId,
            profile.userId,
            profile.firstName,
            profile.lastName,
            profile.fullName,
            profile.email,
            profile.avatarUrl,
            profile.authMethod.method,
            oAuth1InfoId,
            oAuth2InfoId,
            passwordInfoId
          )
//          save(to.copy(userId = current.main.userId), SaveMode.SignUp)
//          profiles += Profile(
//            None,
//            to.providerId, current.main.userId, to.firstName, to.lastName, to.fullName, to.email,
//          to.avatarUrl, to.authMethod.method, to.oAuth1Info, to.oAuth2Info, to.passwordInfo
//          )
          current.copy(identities = to :: current.identities)
//          profiles.update(to)
//          profiles.filter(sp => sp.providerId === providerId && sp.userId === userId)
        }
      }
  }

  override def passwordInfoFor(user: BasicUser): Future[Option[PasswordInfo]] = withSession {
   implicit session =>
    Future successful {
      logger.info("passwordInfoFor(user: MyUser)")
      profiles
        .filter(p => p.providerId === UserPass.UsernamePassword && p.userId === user.main.userId)
        .firstOption match {
        case Some(profile) =>
          passwords.filter(_.id === profile.passwordId).firstOption.map(p => p.passwordInfo)
        case None => None
      }
    }
  }

  override def save(profile: BasicProfile, mode: SaveMode): Future[BasicUser] = withSession {
    implicit session =>
      Future successful {
      logger.info("save(profile: BasicProfile, mode: SaveMode)")
      mode match {
        case SaveMode.SignUp =>
          val oAuth1InfoId = profile.oAuth1Info.map(o1 =>
            (oauth1s returning oauth1s.map(_.id)) += OAuth1(None, o1.token, o1.secret)
          )
          val oAuth2InfoId = profile.oAuth2Info.map(o2 =>
            (oauth2s returning oauth2s.map(_.id)) += OAuth2(None, o2.accessToken, o2.tokenType, o2.expiresIn, o2.refreshToken)
          )
          val passwordInfoId = profile.passwordInfo.map(p => {
            (passwords returning passwords.map(_.id)) += Password(None, p.hasher, p.password, p.salt)
          })
          val profileId = (profiles returning profiles.map(_.id)) += Profile(
            None,
            profile.providerId,
            profile.userId,
            profile.firstName,
            profile.lastName,
            profile.fullName,
            profile.email,
            profile.avatarUrl,
            profile.authMethod.method,
            oAuth1InfoId,
            oAuth2InfoId,
            passwordInfoId
          )

          users += User(profile.userId, profileId)

          users.filter(_.id === profile.userId).first.basicUser
        case SaveMode.PasswordChange =>
          val passwordId = profiles
            .filter(p => p.userId === profile.userId && p.providerId === UserPass.UsernamePassword)
            .first.passwordId

          val passwordInfo = profile.passwordInfo.get

          val password = Password(
            passwordId,
            passwordInfo.hasher,
            passwordInfo.password,
            passwordInfo.salt
          )

          passwords.filter(_.id === passwordId).update(password)

          logger.debug("PasswordChange")

          users.filter(_.id === profile.userId).first.basicUser

        case SaveMode.LoggedIn =>
          users.filter(_.id === profile.userId).first.basicUser
      }
    }
  }

  override def findToken(uuid: String): Future[Option[MailToken]] = withSession {
   implicit session =>
    Future successful {
      logger.info("findToken(token: String)")
      mailTokens.filter(_.uuid === uuid).firstOption
    }
  }

  override def deleteExpiredTokens(): Unit = withSession {
    implicit session =>
    logger.info("deleteExpiredTokens()")
      mailTokens.filter(_.expirationTime < DateTime.now()).delete
      ()
  }

  override def updatePasswordInfo(user: BasicUser, info: PasswordInfo): Future[Option[BasicProfile]] = withSession {
    implicit session =>
      Future successful {
        val profile = profiles
          .filter(p => p.userId === user.main.userId && p.providerId === UserPass.UsernamePassword)
          .firstOption

        profile match {
          case Some(p) =>
            passwords.update(Password(p.passwordId, info.hasher, info.password, info.salt))
            profile.map(p => p.basicProfile)
          case None => None
        }
      }
  }

  override def saveToken(mailToken: MailToken): Future[MailToken] = withSession {
    implicit session =>
    Future successful {
      logger.info("saveToken(token: MailToken)")
      mailTokens += mailToken
      mailToken
    }
  }
}

