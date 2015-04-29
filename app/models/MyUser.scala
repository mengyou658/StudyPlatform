package models

import org.joda.time.DateTime
import com.github.tototoshi.slick.MySQLJodaSupport._
import securesocial.core._
import scala.slick.driver.MySQLDriver
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.ProvenShape
import securesocial.core.providers.MailToken

/**
 * Created by maximcherkasov on 28.04.15.
 */
case class MyUser(main: BasicProfile, identities: List[BasicProfile])

case class Profile(id: Option[Long] = None,
                   providerId: String,
                   userId: String,
                   firstName: Option[String] = None,
                   lastName: Option[String] = None,
                   fullName: Option[String] = None,
                   email: Option[String] = None,
                   avatarUrl: Option[String] = None,
                   authMethod: AuthenticationMethod,
                   oAuth1Info: Option[OAuth1Info],
                   oAuth2Info: Option[OAuth2Info],
                   passwordInfo: Option[PasswordInfo] = None) extends UserProfile {

//  def basicProfile(implicit session: Session): BasicProfile = {
  def basicProfile: BasicProfile = {
    BasicProfile(
      providerId,
      userId,
      firstName,
      lastName,
      fullName,
      email,
      avatarUrl,
      authMethod,
      oAuth1Info,
      oAuth2Info,
      passwordInfo
    )
  }
}

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

class Profiles(tag: Tag) extends Table[Profile](tag, "user") {

  implicit def string2AuthenticationMethod: MySQLDriver.BaseColumnType[AuthenticationMethod] = MappedColumnType.base[AuthenticationMethod, String](
    authenticationMethod => authenticationMethod.method,
    string => AuthenticationMethod(string)
  )

  implicit def tuple2OAuth1Info(tuple: (Option[String], Option[String])): Option[OAuth1Info] = tuple match {
    case (Some(token), Some(secret)) => Some(OAuth1Info(token, secret))
    case _ => None
  }

  implicit def tuple2OAuth2Info(tuple: (Option[String], Option[String], Option[Int], Option[String])): Option[OAuth2Info] = tuple match {
    case (Some(token), tokenType, expiresIn, refreshToken) => Some(OAuth2Info(token, tokenType, expiresIn, refreshToken))
    case _ => None
  }

  implicit def tuple2PasswordInfo(tuple: (Option[String], Option[String], Option[String])): Option[PasswordInfo] = tuple match {
    case (Some(password), Some(haser), salt) => Some(PasswordInfo(password, haser, salt))
    case _ => None
  }


  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def providerId = column[String]("providerId")
  def userId = column[String]("userId")
  def firstName = column[Option[String]]("firstName")
  def lastName = column[Option[String]]("lastName")
  def fullName = column[Option[String]]("fullName")
  def email = column[Option[String]]("email")
  def avatarUrl = column[Option[String]]("avatarUrl")
  def authMethod = column[AuthenticationMethod]("authMethod")

  // oAuth 1
  //  def oAuth1Id = column[Option[Long]]("oauth1_id")
  def token = column[Option[String]]("token")
  def secret = column[Option[String]]("secret")

  // oAuth 2
  //  def oAuth2Id = column[Option[Long]]("oauth2_id")
  def accessToken = column[Option[String]]("accessToken")
  def tokenType = column[Option[String]]("tokenType")
  def expiresIn = column[Option[Int]]("expiresIn")
  def refreshToken = column[Option[String]]("refreshToken")

//  def passwordId = column[Option[Long]]("password_id")
  def hasher = column[Option[String]]("hasher")
  def password = column[Option[String]]("password")
  def salt = column[Option[String]]("salt")


  def * : ProvenShape[Profile] = {
    val shapedValue = (id.?,
      userId,
      providerId,
      firstName,
      lastName,
      fullName,
      email,
      avatarUrl,
      authMethod,
      token,
      secret,
      accessToken,
      tokenType,
      expiresIn,
      refreshToken,
      hasher,
      password,
      salt
      ).shaped

    shapedValue.<>({
      p => Profile.apply(
        id = p._1,
        providerId = p._2,
        userId = p._3,
        firstName = p._4,
        lastName = p._5,
        fullName = p._6,
        email = p._7,
        avatarUrl = p._8,
        authMethod = p._9,
        oAuth1Info = (p._10, p._11),
        oAuth2Info = (p._12, p._13, p._14, p._15),
        passwordInfo = (p._16, p._17, p._18)
      )
      },{
      (p: Profile) => Some{(
        p.id,
        p.userId,
        p.providerId,
        p.firstName,
        p.lastName,
        p.fullName,
        p.email,
        p.avatarUrl,
        p.authMethod,
        p.oAuth1Info.map(_.token), p.oAuth1Info.map(_.secret),
        p.oAuth2Info.map(_.accessToken), p.oAuth2Info.flatMap(_.tokenType),
        p.oAuth2Info.flatMap(_.expiresIn), p.oAuth2Info.flatMap(_.refreshToken),
        p.passwordInfo.map(_.hasher), p.passwordInfo.map(_.password), p.passwordInfo.flatMap(_.salt)
        )}
      })
  }
}

//object profiles extends TableQuery(new Profiles(_)) with WithDefaultSession

object Tables extends WithDefaultSession {

  val Tokens = new TableQuery[MailTokens](new MailTokens(_)) {

    def findById(uuid: String): Option[MailToken] = withSession {
      implicit session =>
        val q = for {
          token <- this
          if token.uuid === uuid
        } yield token

        q.firstOption
    }

    def save(token: MailToken): MailToken = withSession {
      implicit session =>
        findById(token.uuid) match {
          case None =>
            this.insert(token)
            token
          case Some(existingToken) =>
            val tokenRow = for {
              t <- this
              if t.uuid === existingToken.uuid
            } yield t

            val updatedToken = token.copy(uuid = existingToken.uuid)
            tokenRow.update(updatedToken)
            updatedToken
        }
    }

    def delete(uuid:String) = withSession {
      implicit session =>
        val q = for {
          t <- this
          if t.uuid === uuid
        } yield t

        q.delete
    }



    def deleteExpiredTokens(currentDate:DateTime) = withSession {
      implicit session =>
        val q = for {
          t <- this
          if t.expirationTime < currentDate
        } yield t

        q.delete
    }

  }

  val Users = new TableQuery[Profiles](new Profiles(_)) {

    def findById(id: Long): Option[Profile] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.id === id
        } yield user

        q.firstOption
    }

    def findByUid(uid: String): MyUser = withSession {
      implicit session =>
        val q = for {
          user <- this
          if user.userId === uid
        } yield user

        val profiles: List[Profile] = q.list

        MyUser(profiles.head.basicProfile, List())
//        val main = profiles.
//        val identities = profiles.filter(p => p.userId == uid && p.id != mainId).list
    }
//    def basicUser(implicit session: Session): BasicUser = {
//      val main = profiles.filter(_.id === mainId).first
//      val identities = profiles.filter(p => p.userId === id && p.id =!= mainId).list
//
//      BasicUser(main.basicProfile, identities.map(i => i.basicProfile))
//    }

    def findByEmailAndProvider(email:String, providerId:String) : Option[Profile] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if (user.email === email) && (user.providerId === providerId)
        } yield user

        q.firstOption
    }

    def findByProviderIdAndUserId(providerId: String, userId: String): Option[Profile] = withSession {
      implicit session =>
        val q = for {
          user <- this
          if (user.userId === userId) && (user.providerId === providerId)
        } yield user

        q.firstOption
    }

    def all = withSession {
      implicit session =>
        val q = for {
          user <- this
        } yield user

        q.list
    }

    def save(user: Profile): Profile = withSession {
      implicit session =>
        findByProviderIdAndUserId(user.providerId, user.userId) match {
          case None =>
            val id = this.insert(user)
            user.copy(id = Some(id))

          case Some(existingUser) =>
            val userRow = for {
              u <- this
              if u.id === existingUser.id
            } yield u

            val updatedUser = user.copy(id = existingUser.id)
            userRow.update(updatedUser)
            updatedUser

        }
    }

  }
}

