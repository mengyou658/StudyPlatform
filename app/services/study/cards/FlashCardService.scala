package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.{FlashCardJson, FlashCard}
import models.study.flashcards.FlashCardsTableQueries.cards
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 27.05.15.
 */
object FlashCardService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[FlashCard]] = withSession {
    implicit session =>
      Future successful {
        users.filter(u => u.id === userId).firstOption match {
          case Some(u) =>
            cards
              .filter(p => p.userId === u.mainId)
              .list
          case None =>
            List.empty[FlashCard]
        }
      }
  }

  def findById(userId: String, cardId: Long): Future[Option[FlashCard]] = withSession {
    implicit session =>
      Future successful {
        users.filter(u => u.id === userId).firstOption match {
          case Some(u) =>
            cards
              .filter (c => c.id === cardId && (c.userId === u.mainId))
              .firstOption
        }
      }
  }

  def create(userId: String, card: FlashCardJson): Future[FlashCard] = ???
//    withSession {
//    implicit session =>
//      Future successful {
//        val user = users.filter(u => u.id === userId).first
//        cards.filter(_.term === card.term).firstOption match {
//          case None =>
//            val id = (cards returning cards.map(_.id)) +=
//              FlashCard(None,
//                userId = user.mainId,
//                term = card.term,
//                transcription = card.transcription,
//                definition = card.definition,
//                created = new DateTime(),
//                updated = new DateTime())
//
//            cards.filter(_.id === id).first
//          case Some(exists) =>
//            exists
//        }
//      }
  //}
}
