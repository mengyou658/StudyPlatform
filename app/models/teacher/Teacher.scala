package models.teacher

/**
 * Created by m.cherkasov on 23.06.15.
 */
import com.github.tototoshi.slick.MySQLJodaSupport._
import models.courses.CoursesTableQueries.courses
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import slick.driver.MySQLDriver.api._

import scala.language.implicitConversions


case class Teacher(id: Option[Long] = None,
                    userId: Long,
                    courseId: Long,
                    created: DateTime,
                    updated: DateTime)

class Teachers(tag: Tag) extends Table[Teacher](tag, "courses_teachers") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Long]("userId")
  def courseId = column[Long]("courseId")
  def created = column[DateTime]("created")
  def updated = column[DateTime]("updated")

  def * = (id.?, userId, courseId, created, updated) <> (Teacher.tupled, Teacher.unapply)

  def termsLangFk = foreignKey("user_fk", userId, users)(u => u.mainId)
  def definitionsLangFk = foreignKey("course_fk", courseId, courses)(c => c.id)
}
