package models.user

import securesocial.core.OAuth2Info

import scala.language.implicitConversions
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by maximcherkasov on 01.05.15.
 */
case class OAuth2(id: Option[Long] = None, accessToken: String, tokenType: Option[String] = None,
                  expiresIn: Option[Int] = None, refreshToken: Option[String] = None) {
  def oAuth2Info: OAuth2Info = {
    OAuth2Info(accessToken, tokenType, expiresIn, refreshToken)
  }
}

class OAuth2s(tag: Tag) extends Table[OAuth2](tag, "oauth2") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def accessToken = column[String]("accessToken")
  def tokenType = column[Option[String]]("tokenType")
  def expiresIn = column[Option[Int]]("expiresIn")
  def refreshToken = column[Option[String]]("refreshToken")

  def * = (id.?, accessToken, tokenType, expiresIn, refreshToken) <> (OAuth2.tupled, OAuth2.unapply)
}