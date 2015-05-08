package models.business

import scala.slick.direct.AnnotationMapper.column

import slick.driver.MySQLDriver.simple._

/**
 * Created by m.cherkasov on 08.05.15.
 */
case class BusinessProfile (id: Option[Long] = None,
                            email: String,
                            hasher: String,
                            password: String,
                            salt: Option[String] = None)

class BusinessProfiles(tag: Tag) extends Table[BusinessProfile](tag, "business_profile") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def hasher = column[String]("hasher")
  def password = column[String]("password")
  def salt = column[Option[String]]("salt")

  def * = (
    id.?,
    email,
    hasher,
    password,
    salt
    ) <> (BusinessProfile.tupled, BusinessProfile.unapply)
}