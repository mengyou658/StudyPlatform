package models.product

/**
 * Created by maximcherkasov on 24.05.15.
 */

import models.WithDefaultSession

import scala.slick.driver.MySQLDriver.simple._
import scala.language.implicitConversions

case class Product(id: Option[Long] = None, userId: Option[String] = None, name: String)

class Products(tag: Tag) extends Table[Product](tag, "product") {
  def id = column[Long]("id", O.PrimaryKey)
  def name = column[String]("name")
  def userId = column[String]("userId")

  def * = (id.?, userId.?, name) <> (Product.tupled, Product.unapply)
}

object ProductTableQueries extends WithDefaultSession {

  object products extends TableQuery(new Products(_))
}