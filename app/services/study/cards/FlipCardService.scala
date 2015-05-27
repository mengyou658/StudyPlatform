package services.study.cards

import models.WithDefaultSession
import models.study.cards.FlipCard
import models.study.cards.FlipCardsTableQueries.flipCards
import org.joda.time.DateTime
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 27.05.15.
 */
object FlipCardService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String):Future[List[FlipCard]]  = withSession {
    implicit session =>
    Future successful {
      flipCards.filter(_.userId === userId).list
    }
  }

  def create(userId: String, card: FlipCard):Future[FlipCard]  = withSession {
    implicit session =>
      Future successful {
        val id = (flipCards returning flipCards.map(_.id)) += FlipCard(
          None, userId,
          card.partOfSpeech,
          card.original,
          card.originalTranscription,
          card.translation,
          new DateTime(), new DateTime()
        )

        flipCards.filter(_.id === id).first
      }
  }
}
