package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.FlashCard
import models.study.flashcards.FlashCardsTableQueries.cards
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

  def findByUserId(userId: String):Future[List[FlashCard]]  = withSession {
    implicit session =>
    Future successful {
      cards.filter(_.userId === userId).list
    }
  }

  def create(userId: String, card: FlashCard):Future[FlashCard]  = withSession {
    implicit session =>
      Future successful {
        val id = (cards returning cards.map(_.id)) += FlashCard(
          None, userId,
          card.partOfSpeech,
          card.original,
          card.originalTranscription,
          card.translation,
          new DateTime(), new DateTime()
        )

        cards.filter(_.id === id).first
      }
  }
}
