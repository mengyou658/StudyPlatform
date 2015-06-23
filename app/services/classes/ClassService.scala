package services.classes

import com.github.tototoshi.slick.MySQLJodaSupport._
import models.WithDefaultSession
import models.classes.ClassesTableQueries.classes
import models.classes.{StudyClassJson, StudyClass}
import models.study.flashcards.FlashCardsTableQueries.cardsSets
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger

import scala.concurrent.Future
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by m.cherkasov on 27.05.15.
 */
object ClassService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[StudyClass]] = withSession {
    implicit session =>
      Future successful {
        (for {
          u <- users if u.id === userId
          s <- classes if s.userId === u.mainId
        } yield s).list.sortBy(_.name)
      }
  }

  def findById(userId: String, setId: Long): Future[Option[StudyClass]] = withSession {
    implicit session =>
      Future successful {
        (for {
          u <- users if u.id === userId
          s <- classes if s.id === setId && s.userId === u.mainId
        } yield s).firstOption
      }
  }

  def remove(userId: String, setId: Long): Future[Boolean] = withSession {
    implicit session =>
      Future successful {

        val q1 = for {
          u <- users if u.id === userId
          s <- classes if s.id === setId && s.userId === u.mainId
        } yield s

//        q1.list.foreach(s => cardsSets.filter(s1=> s1.id === s.id).delete)

        true
      }
  }

  def save(userId: String, cls: StudyClassJson): Future[Option[StudyClass]] = withSession {
    implicit session =>
      Future successful {
        val user = users.filter(u => u.id === userId).first
        
        cls.id match {
          case Some(id) =>

            classes.filter(s => s.id === id && s.userId === user.mainId)
              .map(s => (s.name, s.description, s.updated))
              .update((cls.name, cls.description.getOrElse(""), new DateTime()))

            Some(classes.filter(s => s.id === id && s.userId === user.mainId).first)
          case None =>

            val id = (classes returning classes.map(_.id)) +=
              StudyClass(None, user.mainId, cls.name, cls.description, new DateTime(), new DateTime())

            Some(classes.filter(_.id === id).first)
        }
    }
  }
}
