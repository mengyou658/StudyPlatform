package models.study.flashcards

import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions

/**
 * Created by m.cherkasov on 28.05.15.
 */
case class Category (id: Option[Long] = None,
                              name: String,
                              created: DateTime,
                              updated: DateTime)

class Categories(tag: Tag) extends Table[Category](tag, "flashcards_categories") {
  def id = column[Long]("id", O.PrimaryKey)
  def name = column[String]("name")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * =
    (id.?, name, created, updated) <>
      (Category.tupled, Category.unapply)
}

