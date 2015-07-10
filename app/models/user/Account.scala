package models.user

/**
 * Created by m.cherkasov on 10.07.15.
 */
import scala.language.implicitConversions
import scala.slick.driver.MySQLDriver.simple._

case class Account(id: Option[Long] = None, business: Boolean) {

}

class Accounts(tag: Tag) extends Table[Account](tag, "accounts") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def business = column[Boolean]("business")


  def * = (id.?, business) <> (Account.tupled, Account.unapply)
}