package models.study.flashcards

import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class Pack(id: Option[Long] = None,
                         userId: String,
                         name: String,
                         shared: Boolean = false,
                         description: Option[String] = None,
                         created: DateTime,
                         updated: DateTime)

class Packs(tag: Tag) extends Table[Pack](tag, "flashcard_packs") {
  def id = column[Long]("id", O.PrimaryKey)
  def userId = column[String]("userId")
  def name = column[String]("name")
  def shared = column[Boolean]("shared")
  def description = column[String]("description")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, name, shared, description.?, created, updated) <> (Pack.tupled, Pack.unapply)
}

case class PackJson(name: String,
                    shared: Boolean = false,
                    description: Option[String] = None)



