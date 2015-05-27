package services.study.cards

import models.WithDefaultSession
import models.study.cards.FlipCard
import models.study.cards.FlipCardsTableQueries.flipCards
import play.api.Logger
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 27.05.15.
 */
class FlipCardService extends WithDefaultSession {
  val logger = Logger(this.getClass)
  val db = Database.forConfig("mydb")

  def findByUserId(userId: String):Future[Seq[FlipCard]]  = db.run {
    flipCards.filter(_.userId === userId).result
  }
}
