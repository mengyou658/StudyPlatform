package models.study.cards

import models.WithDefaultSession
import org.joda.time.DateTime
import scala.slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 26.05.15.
 */
case class FlipCardList(id: Option[Long] = None, userId: Option[String] = None, name: String,
                     description: Option[String] = None, created: DateTime)

class FlipCardLists(tag: Tag) extends Table[FlipCardList](tag, "flipcard_lists") {
  def id = column[Long]("id", O.PrimaryKey)
  def userId = column[String]("userId")
  def name = column[String]("name")
  def description = column[String]("description")
  def created = column[DateTime]("created")

  def * = (id.?, userId.?, name, description.?, created) <> (FlipCardList.tupled, FlipCardList.unapply)
}

object FlipCardListTableQueries extends WithDefaultSession {

  object flipCardList extends TableQuery(new FlipCardLists(_))
}
