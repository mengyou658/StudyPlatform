package models.group

/**
 * Created by m.cherkasov on 23.06.15.
 */
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.language.implicitConversions


case class StudyGroup(id: Option[Long] = None,
                      userId: Long,
                      name: String,
                      description: Option[String] = None,
                      created: DateTime,
                      updated: DateTime)

class StudyGroups(tag: Tag) extends Table[StudyGroup](tag, "study_group") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def name = column[String]("name")
  def description = column[String]("description")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, name, description.?, created, updated) <> (StudyGroup.tupled, StudyGroup.unapply)
}

case class StudyGroupJson(id: Option[Long] = None,
                          name: String,
                          description: Option[String] = None
                           )