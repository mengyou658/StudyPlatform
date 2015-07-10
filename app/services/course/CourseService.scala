package services.course

import com.github.tototoshi.slick.MySQLJodaSupport._
import models.WithDefaultSession
import models.courses.CoursesTableQueries.courses
import models.courses.{CourseJson, Course}
import models.study.flashcards.FlashCardsTableQueries.cardsSets
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger

import scala.concurrent.Future
import slick.driver.MySQLDriver.api._
//import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by m.cherkasov on 27.05.15.
 */
object CourseService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[Course]] = withSession {
    implicit session =>
      Future successful {
        var aaa = (for {
          u <- users if u.id === userId
          s <- courses if s.userId === u.mainId
        } yield s).result
        List.empty
      }
  }

  def findById(userId: String, courseId: Long): Future[Option[Course]] = withSession {
    implicit session =>
      Future successful {
        (for {
          u <- users if u.id === userId
          s <- courses if s.id === courseId && s.userId === u.mainId
        } yield s).result._2
      }
  }

  def remove(userId: String, courseId: Long): Future[Boolean] = withSession {
    implicit session =>
      Future successful {

        val q1 = for {
          u <- users if u.id === userId
          s <- courses if s.id === courseId && s.userId === u.mainId
        } yield s

//        q1.list.foreach(s => cardsSets.filter(s1=> s1.id === s.id).delete)

        true
      }
  }

  def save(userId: String, course: CourseJson): Future[Option[Course]] = withSession {
    implicit session =>
      Future successful {
        val user = users.filter(u => u.id === userId).result.head

        course.id match {
          case Some(id) =>

            courses.filter(s => s.id === id && s.userId === user.mainId)
              .map(s => (s.name, s.description, s.updated))
              .update((course.name, course.description.getOrElse(""), new DateTime()))

            Some(courses.filter(s => s.id === id && s.userId === user.mainId).first)
          case None =>

            val id = (courses returning courses.map(_.id)) +=
              Course(None, user.mainId, course.name, course.shortName, course.description, new DateTime(), new DateTime())

            Some(courses.filter(_.id === id).first)
        }
    }
  }
}
