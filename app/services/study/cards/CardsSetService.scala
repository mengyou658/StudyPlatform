package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.FlashCardsTableQueries.{cards, cardsSets}
import models.study.flashcards.{CardsSetJson, CardsSet}
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._
import com.github.tototoshi.slick.MySQLJodaSupport._


import scala.concurrent.Future

/**
 * Created by m.cherkasov on 27.05.15.
 */
object CardsSetService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[CardsSet]] = withSession {
    implicit session =>
      Future successful {
        (for {
          u <- users if u.id === userId
          s <- cardsSets if s.userId === u.mainId
        } yield s).list

      }
  }

  def findById(userId: String, setId: Long): Future[Option[CardsSet]] = withSession {
    implicit session =>
      Future successful {
        (for {
          u <- users if u.id === userId
          s <- cardsSets if s.id === setId && s.userId === u.mainId
        } yield s).firstOption
      }
  }

  def remove(userId: String, setId: Long): Future[Boolean] = withSession {
    implicit session =>
      Future successful {

        val q1 = for {
          u <- users if u.id === userId
          s <- cardsSets if s.id === setId && s.userId === u.mainId
        } yield s

        q1.list.foreach(s => cardsSets.filter(s1=> s1.id === s.id).delete)

        true
      }
  }

  def save(userId: String, set: CardsSetJson): Future[Option[CardsSet]] = withSession {
    implicit session =>
      Future successful {
        val user = users.filter(u => u.id === userId).first
        set.id match {
          case Some(id) =>
            cardsSets.filter(s => s.id === id && s.userId === user.mainId)
              .map(s => (s.name, s.description, s.updated))
              .update((set.name, set.description.getOrElse(""), new DateTime()))
            Some(cardsSets.filter(s => s.id === id && s.userId === user.mainId).first)
          case None =>
            val id = (cardsSets returning cardsSets.map(_.id)) +=
              CardsSet(None, user.mainId, set.name, set.description,
                new DateTime(), new DateTime())

            Some(cardsSets.filter(_.id === id).first)
        }
    }
  }
}
