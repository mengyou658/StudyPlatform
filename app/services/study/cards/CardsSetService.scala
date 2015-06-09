package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.FlashCardsTableQueries.cardsSets
import models.study.flashcards.{CardsSetJson, CardsSet}
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._


import scala.concurrent.Future

/**
 * Created by m.cherkasov on 27.05.15.
 */
object CardsSetService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[CardsSet]] = withSession {
    implicit session =>
      Future successful {
        users.filter(u => u.id === userId).firstOption match {
          case Some(u) =>
            cardsSets
              .filter(p => p.userId === u.mainId )
              .list
          case None =>
            List.empty[CardsSet]
        }
      }
  }

  def findById(userId: String, setId: Long): Future[Option[CardsSet]] = withSession {
    implicit session =>
      Future successful {
        users.filter(u => u.id === userId).firstOption match {
          case Some(u) =>
            cardsSets
            .filter (s => s.id === setId && s.userId === u.mainId)
            .firstOption
        }
      }
  }

  def create(userId: String, set: CardsSetJson): Future[CardsSet] = withSession {
    implicit session =>
      Future successful {
        val user = users.filter(u => u.id === userId).first

        cardsSets.filter(_.name === set.name).firstOption match {
          case None =>
            val id = (cardsSets returning cardsSets.map(_.id)) +=
              CardsSet(None, user.mainId, set.name, set.description,
                new DateTime(), new DateTime())

            cardsSets.filter(_.id === id).first
          case Some(existsSet) =>
            existsSet
      }
    }
  }
}
