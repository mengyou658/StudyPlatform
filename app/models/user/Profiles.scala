package models.user

import models.user.UserTableQueries.{passwords, oauth2s, oauth1s}
import securesocial.core.{AuthenticationMethod, BasicProfile, UserProfile}

import scala.slick.driver.MySQLDriver.simple._
/**
 * Created by maximcherkasov on 01.05.15.
 */
case class Profile(id: Option[Long] = None,
                   providerId: String,
                   userId: String,
                   firstName: Option[String] = None,
                   lastName: Option[String] = None,
                   fullName: Option[String] = None,
                   email: Option[String] = None,
                   avatarUrl: Option[String] = None,
                   authMethod: String,
                   oAuth1Id: Option[Long] = None,
                   oAuth2Id: Option[Long] = None,
                   passwordId: Option[Long] = None) extends UserProfile  {

  def basicProfile(implicit session: Session): BasicProfile = {
    BasicProfile(
      providerId,
      userId,
      firstName,
      lastName,
      fullName,
      email,
      avatarUrl,
      authMethod match {
        case "oauth1" => AuthenticationMethod.OAuth1
        case "oauth2" => AuthenticationMethod.OAuth2
        case "openId" => AuthenticationMethod.OpenId
        case "userPassword" => AuthenticationMethod.UserPassword
      },
      oauth1s.filter(_.id === oAuth1Id).firstOption.map(o1 => o1.oAuth1Info),
      oauth2s.filter(_.id === oAuth2Id).firstOption.map(o2 => o2.oAuth2Info),
      passwords.filter(_.id === passwordId).firstOption.map(p => p.passwordInfo)
    )
  }
}

class Profiles(tag: Tag) extends Table[Profile](tag, "profile") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def providerId = column[String]("providerId")
  def userId = column[String]("userId")
  def firstName = column[Option[String]]("firstName")
  def lastName = column[Option[String]]("lastName")
  def fullName = column[Option[String]]("fullName")
  def email = column[Option[String]]("email")
  def avatarUrl = column[Option[String]]("avatarUrl")
  def authMethod = column[String]("authMethod")

  def oAuth1Id = column[Option[Long]]("oauth1id")
  def oAuth2Id = column[Option[Long]]("oauth2Id")
  def passwordId = column[Option[Long]]("passwordId")

  def * = (
    id.?,
    providerId,
    userId,
    firstName,
    lastName,
    fullName,
    email,
    avatarUrl,
    authMethod,
    oAuth1Id,
    oAuth2Id,
    passwordId
    ) <> (Profile.tupled, Profile.unapply)

  def idk = index("profile_idx", (providerId, userId))
}