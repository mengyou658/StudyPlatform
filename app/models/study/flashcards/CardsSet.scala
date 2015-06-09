package models.study.flashcards

import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class CardsSet(id: Option[Long] = None,
                         userId: Long,
                         name: String,
                         description: Option[String] = None,
                         created: DateTime,
                         updated: DateTime)

class CardsSets(tag: Tag) extends Table[CardsSet](tag, "cards_sets") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def name = column[String]("name")
  def description = column[String]("description")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, name, description.?, created, updated) <> (CardsSet.tupled, CardsSet.unapply)
}

case class CardsSetJson(name: String,
                    description: Option[String] = None)



