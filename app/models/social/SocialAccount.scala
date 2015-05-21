package models.social

import scala.slick.driver.MySQLDriver.simple._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 21.05.15.
 */
case class SocialAccount(id: Option[Long] = None,
                         userId: Long,
                         provider: String,
                         accountId: String,
                         accessToken: String,
                         username: Option[String] = None,
                         profilePicture:  Option[String] = None)

class SocialAccounts(tag: Tag) extends Table[SocialAccount](tag, "social_account") {
  def id = column[Long]("id", O.PrimaryKey)
  def uid = column[Long]("userId")
  def provider = column[String]("provider")
  def accountId = column[String]("accountId")
  def accessToken = column[String]("accessToken")
  def username = column[Option[String]]("username")
  def profilePicture = column[Option[String]]("profilePicture")

  def * = (id.?, uid, provider, accountId, accessToken, username, profilePicture) <> (SocialAccount.tupled, SocialAccount.unapply)
}