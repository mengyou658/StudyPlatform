package models.user

import models.user.UserTableQueries.profiles

import scala.language.implicitConversions
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by maximcherkasov on 01.05.15.
 */
case class User(id: String, mainId: Long) {
  def basicUser(implicit session: Session): BasicUser = {
    val main = profiles.filter(_.id === mainId).first
    val identities = profiles.filter(p => p.userId === id && p.id =!= mainId).list

    BasicUser(main.basicProfile, identities.map(i => i.basicProfile))
  }
}

class Users(tag: Tag) extends Table[User](tag, "user") {
  def id = column[String]("id", O.PrimaryKey)
  def mainId = column[Long]("mainId")

  def * = (id, mainId) <> (User.tupled, User.unapply)
}