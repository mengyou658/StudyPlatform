package models.study.cards

import models.WithDefaultSession
import org.joda.time.DateTime
import scala.slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class FlipCard(id: Option[Long] = None,
               userId: String,
               original: String,
               originalTranscription: Option[String] = None,
               translation: String,
               created: DateTime,
               updated: DateTime)

class FlipCards(tag: Tag) extends Table[FlipCard](tag, "flip_cards") {
  def id = column[Long]("id", O.PrimaryKey)
  def userId = column[String]("userId")
  def original = column[String]("original")
  def originalTranscription = column[String]("originalTranscription")
  def translation = column[String]("translation")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * =
    (id.?, userId, original, originalTranscription.?, translation, created, updated) <>
    (FlipCard.tupled, FlipCard.unapply)
}

object FlipCardsTableQueries extends WithDefaultSession {

  object flipCards extends TableQuery(new FlipCards(_))
}
