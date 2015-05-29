package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.FlashCardsTableQueries.packs
import models.study.flashcards.{FlashCardsPackJson, FlashCardsPack}
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._


import scala.concurrent.Future

/**
 * Created by m.cherkasov on 27.05.15.
 */
object FlashCardsPackService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def findByUserId(userId: String): Future[List[FlashCardsPack]] = withSession {
    implicit session =>
      Future successful {
        users.filter(u => u.id === userId).firstOption match {
          case Some(u) =>
            packs
              .filter(p => p.userId === u.mainId  || p.shared === true)
              .list
          case None =>
            List.empty[FlashCardsPack]
        }
      }
  }

  def findByPackId(userId: String, packId: Long): Future[Option[FlashCardsPack]] = withSession {
    implicit session =>
      Future successful {
        users.filter(u => u.id === userId).firstOption match {
          case Some(u) =>
            packs
            .filter (pack => pack.id === packId && (pack.userId === u.mainId || pack.shared === true))
            .firstOption
        }
      }
  }

  def create(userId: String, list: FlashCardsPackJson): Future[FlashCardsPack] = withSession {
    implicit session =>
      Future successful {
        val user = users.filter(u => u.id === userId).first
        packs.filter(_.name === list.name).firstOption match {
        case None =>
          val id = (packs returning packs.map(_.id)) +=
            FlashCardsPack(None, user.mainId, list.name, list.shared, list.description,
              new DateTime(), new DateTime())

          packs.filter(_.id === id).first
        case Some(existsList) =>
          existsList
      }
    }
  }
}
