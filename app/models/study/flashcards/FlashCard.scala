package models.study.flashcards

import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class FlashCard(id: Option[Long] = None,
                    userId: String,
                    partOfSpeech: Option[String],
                    original: String,
                    originalTranscription: Option[String] = None,
                    translation: String,
                    created: DateTime,
                    updated: DateTime)

class FlashCards(tag: Tag) extends Table[FlashCard](tag, "flash_cards") {
  def id = column[Long]("id", O.PrimaryKey)
  def userId = column[String]("userId")
  def partOfSpeech = column[String]("partOfSpeech")
  def original = column[String]("original")
  def originalTranscription = column[String]("originalTranscription")
  def translation = column[String]("translation")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * =
    (id.?, userId, partOfSpeech.?, original, originalTranscription.?, translation, created, updated) <>
    (FlashCard.tupled, FlashCard.unapply)
}
