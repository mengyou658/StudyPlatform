package services.group

import com.github.tototoshi.slick.MySQLJodaSupport._
import models.WithDefaultSession
import models.group.GroupTableQueries.groups
import models.group.{StudyGroupJson, StudyGroup}
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger

import scala.concurrent.Future
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by m.cherkasov on 27.05.15.
 */
object GroupService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[StudyGroup]] = withSession {
    implicit session =>
      Future successful {
        (for {
          u <- users if u.id === userId
          s <- groups if s.userId === u.mainId
        } yield s).list.sortBy(_.name)
      }
  }

  def findById(userId: String, groupId: Long): Future[Option[StudyGroup]] = withSession {
    implicit session =>
      Future successful {
        (for {
          u <- users if u.id === userId
          g <- groups if g.id === groupId && g.userId === u.mainId
        } yield g).firstOption
      }
  }

  def remove(userId: String, groupId: Long): Future[Boolean] = withSession {
    implicit session =>
      Future successful {

        val q1 = for {
          u <- users if u.id === userId
          g <- groups if g.id === groupId && g.userId === u.mainId
        } yield g

//        q1.list.foreach(s => cardsSets.filter(s1=> s1.id === s.id).delete)

        true
      }
  }

  def save(userId: String, grp: StudyGroupJson): Future[Option[StudyGroup]] = withSession {
    implicit session =>
      Future successful {
        val user = users.filter(u => u.id === userId).first
        
        grp.id match {
          case Some(id) =>

            groups.filter(g => g.id === id && g.userId === user.mainId)
              .map(s => (s.name, s.description, s.updated))
              .update((grp.name, grp.description.getOrElse(""), new DateTime()))

            Some(groups.filter(g => g.id === id && g.userId === user.mainId).first)
          case None =>

            val id = (groups returning groups.map(_.id)) +=
              StudyGroup(None, user.mainId, grp.name, grp.description, new DateTime(), new DateTime())

            Some(groups.filter(_.id === id).first)
        }
    }
  }
}
