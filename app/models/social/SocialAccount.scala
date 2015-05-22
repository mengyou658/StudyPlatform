package models.social

import models.WithDefaultSession
import play.api.libs.json.{JsNumber, Json, JsString, Writes}

import scala.slick.driver.MySQLDriver.simple._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 21.05.15.
 */

case class SocialAccount(id: Option[Long] = None,
                         userId: String,
                         provider: String,
                         accountId: String,
                         accessToken: String,
                         username: Option[String] = None,
                         profilePicture:  Option[String] = None)

class SocialAccounts(tag: Tag) extends Table[SocialAccount](tag, "social_account") {
  def id = column[Long]("id", O.PrimaryKey)
  def userId = column[String]("userId")
  def provider = column[String]("provider")
  def accountId = column[String]("accountId")
  def accessToken = column[String]("accessToken")
  def username = column[Option[String]]("username")
  def profilePicture = column[Option[String]]("profilePicture")

  def * = (id.?, userId, provider, accountId, accessToken, username, profilePicture) <> (SocialAccount.tupled, SocialAccount.unapply)
}

object SocialAccountsTableQueries extends WithDefaultSession {

  object socialAccounts extends TableQuery(new SocialAccounts(_))
}

