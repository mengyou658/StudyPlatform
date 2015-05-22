package models.user

import securesocial.core.OAuth1Info

import scala.language.implicitConversions
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by maximcherkasov on 01.05.15.
 */
case class OAuth1(id: Option[Long] = None, token: String, secret: String) {
  def oAuth1Info: OAuth1Info = {
    OAuth1Info(token, secret)
  }
}

class OAuth1s(tag: Tag) extends Table[OAuth1](tag, "oauth1") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def token = column[String]("token")
  def secret = column[String]("secret")

  def * = (id.?, token, secret) <> (OAuth1.tupled, OAuth1.unapply)
}