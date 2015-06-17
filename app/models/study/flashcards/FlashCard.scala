package models.study.flashcards

import models.study.flashcards.FlashCardsTableQueries.cardsSets
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class FlashCard(id: Option[Long] = None,
                     userId: Long,
                     cardsSetId: Long,
                     term: String,
                     transcription: Option[String] = None,
                     definition: String,
                     created: DateTime,
                     updated: DateTime)

class FlashCards(tag: Tag) extends Table[FlashCard](tag, "flashcards") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def cardsSetId = column[Long]("cardsSetId")
  def term = column[String]("term")
  def transcription = column[String]("transcription")
  def definition = column[String]("definition")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * =
    (id.?, userId, cardsSetId, term, transcription.?, definition, created, updated) <>
    (FlashCard.tupled, FlashCard.unapply)

  def eventFk = foreignKey("cards_set_fk", cardsSetId, cardsSets)(cs => cs.id)
}


case class FlashCardJson(id: Option[Long] = None,
                         term: String,
                         transcription: Option[String] = None,
                         definition: String)
