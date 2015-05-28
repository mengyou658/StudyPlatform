package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.FlashCardsTableQueries.packs
import models.study.flashcards.{PackJson, Pack}
import org.joda.time.DateTime
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._


import scala.concurrent.Future

/**
 * Created by m.cherkasov on 27.05.15.
 */
object FlipCardsListService extends WithDefaultSession {
  val logger = Logger(this.getClass)


  def findByUserId(userId: String): Future[List[Pack]] = withSession {
    implicit session =>
      Future successful {
        packs
          .filter(sa => sa.userId === userId)
          .list
      }
  }

  def create(userId: String, list: PackJson): Future[Pack] = withSession {
    implicit session =>
      Future successful {
        packs.filter(_.name === list.name).firstOption match {
        case None =>
          val id = (packs returning packs.map(_.id)) +=
            Pack(None, userId, list.name, list.shared, list.description,
              new DateTime(), new DateTime())

          packs.filter(_.id === id).first
        case Some(existsList) =>
          existsList
      }
    }
  }
}
