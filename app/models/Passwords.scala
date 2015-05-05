package models


import securesocial.core.PasswordInfo

import scala.slick.driver.MySQLDriver.simple._
import scala.language.implicitConversions

/**
 * Created by maximcherkasov on 01.05.15.
 */
case class Password(id: Option[Long] = None, hasher: String, password: String, salt: Option[String] = None) {
  def passwordInfo: PasswordInfo = {
    PasswordInfo(hasher, password, salt)
  }
}

class Passwords(tag: Tag) extends Table[Password](tag, "password") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def hasher = column[String]("hasher")
  def password = column[String]("password")
  def salt = column[Option[String]]("salt")

  def * = (id.?, hasher, password, salt) <> (Password.tupled, Password.unapply)
}