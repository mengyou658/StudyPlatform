package models.user

import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by maximcherkasov on 30.04.15.
 */
case class UserAuthenticator(id: String, userId: String, expirationDate: DateTime, lastUsed: DateTime, creationDate: DateTime)

class UserAuthenticators(tag: Tag) extends Table[UserAuthenticator](tag, "authenticator") {
  def id = column[String]("id", O.PrimaryKey)
  def userId = column[String]("userId")
  def expirationDate = column[DateTime]("expirationDate")
  def lastUsed = column[DateTime]("lastUsed")
  def creationDate = column[DateTime]("creationDate")

  def * = (id, userId, expirationDate, lastUsed, creationDate) <> (UserAuthenticator.tupled, UserAuthenticator.unapply)
}
