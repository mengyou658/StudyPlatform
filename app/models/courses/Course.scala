package models.courses

/**
 * Created by m.cherkasov on 23.06.15.
 */
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions


case class Course(id: Option[Long] = None,
                    userId: Long,
                    name: String,
                    shortName: String,
                    description: Option[String] = None,
                    created: DateTime,
                    updated: DateTime)

class Courses(tag: Tag) extends Table[Course](tag, "courses") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def name = column[String]("name")
  def shortName = column[String]("shortName")
  def description = column[String]("description")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, name, shortName, description.?, created, updated) <> (Course.tupled, Course.unapply)
}

case class CourseJson(id: Option[Long] = None,
                        name: String,
                        shortName: String,
                        description: Option[String] = None
                         )
