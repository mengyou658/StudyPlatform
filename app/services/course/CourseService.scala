package services.course

import models.courses.{CourseJson, Course}
import models.courses.CoursesTableQueries.courses
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import com.github.tototoshi.slick.MySQLJodaSupport._
import scala.concurrent.ExecutionContext.Implicits.global


//import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by m.cherkasov on 27.05.15.
 */

object CourseService  {
  val logger = Logger(this.getClass)

  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("mysql")
  val db = dbConfig.db // all database interactions are realised through this object
  import slick.driver.MySQLDriver.api._

  def findByUserId(userId: String): Future[List[Course]] = {
      Future successful {
        val res = (for {
          u <- users if u.id === userId
          s <- courses if s.userId === u.mainId
        } yield s).result

        Await.result(db.run(res), Duration.Inf).toList
      }
  }

  def findById(userId: String, courseId: Long): Future[Option[Course]] = {
      Future successful {
        val res = (for {
          u <- users if u.id === userId
          s <- courses if s.id === courseId && s.userId === u.mainId
        } yield s).result

        Await.result(db.run(res), Duration.Inf).headOption
      }
  }

  def remove(userId: String, courseId: Long): Future[Boolean] = {
        val user = Await.result(db.run({
          users.filter(u => u.id === userId).result
        }), Duration.Inf).head

        db.run({
          courses.filter(s=> s.id === courseId && s.userId === user.mainId).delete
        }) map {
          case 0 => false
          case _ => true
        }
  }

  def save(userId: String, course: CourseJson): Future[Option[Course]] = {
      Future successful {

        val user = Await.result(db.run({
          users.filter(u => u.id === userId).result
        }), Duration.Inf).head

        course.id match {
          case Some(id) =>

            courses.filter(s => s.id === id && s.userId === user.mainId)
              .map(s => (s.name, s.description, s.updated))
              .update((course.name, course.description.getOrElse(""), new DateTime()))

            Await.result(db.run({
              courses.filter(s => s.id === id && s.userId === user.mainId).result
            }), Duration.Inf).headOption

          case None =>
            var courseNew = Course(None, user.mainId, course.name, course.shortName, course.description, new DateTime(), new DateTime())
            val id = (courses returning courses.map(_.id)) += courseNew

//            courseNew.id = id

            Some(courseNew)
        }
    }
  }
}
