package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.{FlashCardJson, FlashCard}
import models.study.flashcards.FlashCardsTableQueries.{cardsSets, cards}
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

  def findBySet(userId: String, setId: Long): Future[List[FlashCard]] = withSession {
    implicit session =>
      Future successful {
        users.filter(u => u.id === userId).firstOption match {
          case Some(u) =>
            cards
              .filter (c => c.cardsSetId === setId && (c.userId === u.mainId))
              .list
        }
      }
  }

  def save(userId: String, setId: Long, card: FlashCardJson): Future[Option[FlashCard]] =
    withSession {
      implicit session =>
        Future successful {
          val user = users.filter(u => u.id === userId).first
          val set = cardsSets.filter(s => s.id === setId && s.userId === user.mainId).first
          set.id match {
            case Some(xSetId) =>
              card.id match {
                case Some(id) =>
                  cards.filter(c => c.id === id && c.cardsSetId === setId)
                      .map(c => (c.term, c.transcription, c.definition, c.updated))
                      .update((card.term, card.transcription.getOrElse(""), card.definition, new DateTime()))

                  val updateSet = set.copy(id = set.id, updated = new DateTime())
                  cardsSets.update(updateSet)
                  Some(cards.filter(c => c.id === id && c.cardsSetId === setId).first)
                case None =>
                  val id = (cards returning cards.map(_.id)) +=
                    FlashCard(
                      None,
                      user.mainId,
                      xSetId,
                      card.term,
                      card.transcription,
                      card.definition,
                      created = new DateTime(),
                      updated = new DateTime()
                    )

                  Some(cards.filter(_.id === id).first)
              }

            case None =>
              None
          }
        }
    }

  def removeFromSet(userId: String, setId: Long, cardId: Long): Future[Boolean] =
    withSession {
      implicit session =>
        Future successful {
          val user = users.filter(u => u.id === userId).first
          val set = cardsSets.filter(s => s.id === setId && s.userId === user.mainId).first
          set.id match {
            case Some(xSetId) =>
              cards.filter(c => c.id === cardId && c.cardsSetId === set.id).delete
              true
            case None =>
              false
          }
        }
    }
}
