package models.classes

/**
 * Created by m.cherkasov on 23.06.15.
 */
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions


case class StudyClass(id: Option[Long] = None,
                    userId: Long,
                    name: String,
                    description: Option[String] = None,
                    created: DateTime,
                    updated: DateTime)

class StudyClasses(tag: Tag) extends Table[StudyClass](tag, "study_classes") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def name = column[String]("name")
  def description = column[String]("description")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, name, description.?, created, updated) <> (StudyClass.tupled, StudyClass.unapply)
}

case class StudyClassJson(id: Option[Long] = None,
                        name: String,
                        description: Option[String] = None
                         )
