package models.study.flashcards

import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class FlashCard(id: Option[Long] = None,
                     userId: Long,
                     partOfSpeech: Option[String],
                     original: String,
                     transcription: Option[String] = None,
                     translation: String,
                     created: DateTime,
                     updated: DateTime)

class FlashCards(tag: Tag) extends Table[FlashCard](tag, "flash_cards") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def partOfSpeech = column[String]("partOfSpeech")
  def original = column[String]("original")
  def transcription = column[String]("transcription")
  def translation = column[String]("translation")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * =
    (id.?, userId, partOfSpeech.?, original, transcription.?, translation, created, updated) <>
    (FlashCard.tupled, FlashCard.unapply)
}


case class FlashCardJson(original: String,
                         transcription: Option[String] = None,
                         translation: String)
