package models.study.flashcards

import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class FlashCardsPack(id: Option[Long] = None,
                         userId: Long,
                         name: String,
                         shared: Boolean = false,
                         description: Option[String] = None,
                         created: DateTime,
                         updated: DateTime)

class FlashCardsPackTable(tag: Tag) extends Table[FlashCardsPack](tag, "flashcard_packs") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def name = column[String]("name")
  def shared = column[Boolean]("shared")
  def description = column[String]("description")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, name, shared, description.?, created, updated) <> (FlashCardsPack.tupled, FlashCardsPack.unapply)
}

case class FlashCardsPackJson(name: String,
                    shared: Boolean = false,
                    description: Option[String] = None)



