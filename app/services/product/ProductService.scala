package services.product

import models.WithDefaultSession
import models.product.Product
import models.product.ProductTableQueries.products
import models.user.UserTableQueries.users
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._

import scala.concurrent.Future

/**
 * Created by maximcherkasov on 24.05.15.
 */
class ProductService  extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[Product]] = withSession {
    implicit session =>
      Future successful {
        products
          .filter(p => p.userId === userId)
          .list
      }
  }

  def save(userId: String, product: Product): Future[Product] = withSession {
    implicit session =>
      Future successful {
        val existingProduct = products
          .filter(p => p.userId === userId && p.name === product.name).firstOption

        existingProduct match {
          case Some(ep) =>
            val pr = ep.copy(id = ep.id)
            products.filter(_.name === product.name).update(pr)
            pr
          case None =>
            products += Product(
              None,
              Some(userId),
              product.name
            )
            products.filter(p => p.userId === userId && p.name === product.name).first
        }
      }
  }
}
