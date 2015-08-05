package services.study.cards

import models.WithDefaultSession
import models.study.flashcards.FlashCardsTableQueries.{cards, cardsSets}
import models.study.flashcards.{FlashCard, CardsSetJson, CardsSet}
import models.system.Lang
import models.system.SystemTableQueries.langs
import models.user.UserTableQueries.users
import org.joda.time.DateTime
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import com.github.tototoshi.slick.MySQLJodaSupport._


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * Created by m.cherkasov on 27.05.15.
 */
object CardsSetService {
  val logger = Logger(this.getClass)

  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("mysql")
  val db = dbConfig.db // all database interactions are realised through this object
  import slick.driver.MySQLDriver.api._


  def findByUserId(userId: String): Future[List[(CardsSet, (Lang, Lang))]] =  {
      Future successful {
        val res = (for {
          u <- users if u.id === userId
          s <- cardsSets if s.userId === u.mainId
          lt <- langs if lt.id === s.termsLangId
          ld <- langs if ld.id === s.definitionsLangId
        } yield (s, (lt,ld))).result

        Await.result(db.run(res), Duration.Inf).sortBy(_._1.name).toList
      }
  }

  def findById(userId: String, setId: Long): Future[Option[(CardsSet, (Lang, Lang))]] =  {
      Future successful {
        val res =(for {
          u <- users if u.id === userId
          s <- cardsSets if s.id === setId && s.userId === u.mainId
          lt <- langs if lt.id === s.termsLangId
          ld <- langs if ld.id === s.definitionsLangId
        } yield (s, (lt,ld))).result

        Await.result(db.run(res), Duration.Inf).headOption
      }
  }

  def remove(userId: String, setId: Long): Future[Boolean] =  {

        val q1 = (for {
          usr <- users.filter(_.id === userId).result
          s <- usr match {
            case u =>
              cardsSets.filter(s => s.id === setId && s.userId === u.head.mainId).delete
          }
        } yield s).transactionally

      db.run({
        q1
      }) map {
        case 0 => false
        case _ => true
      }
  }

  def save(userId: String, set: CardsSetJson): Future[Option[(CardsSet, (Lang, Lang))]] = {
      Future successful {

        val user = Await.result(db.run({
          users.filter(u => u.id === userId).result
        }), Duration.Inf).head

        val termLang = Await.result(db.run({
          langs.filter(_.id === set.termsLang.id).result
        }), Duration.Inf).head

        val definitionLang = Await.result(db.run({
          langs.filter(_.id === set.definitionsLang.id).result
        }), Duration.Inf).head

        set.id match {
          case Some(id) =>

            cardsSets.filter(s => s.id === id && s.userId === user.mainId)
              .map(s => (s.name, s.description, s.termsLangId, s.definitionsLangId, s.updated))
              .update((set.name, set.description.getOrElse(""), termLang.id, definitionLang.id, new DateTime()))

            Some(
                Await.result(db.run({
                  cardsSets.filter(s => s.id === id && s.userId === user.mainId).result
                }), Duration.Inf).head,
                (termLang, definitionLang)
            )
          case None =>
            val newCardsSet = CardsSet(None, user.mainId, set.name, set.description,
              termLang.id,
              definitionLang.id,
              studying = false,
              new DateTime(), new DateTime())

            val action = for {
              _     <- cardsSets += newCardsSet
              cards <- cardsSets.result
            } yield cards

            Some(
              Await.result(db.run({
                action
              }), Duration.Inf).head,
              (termLang, definitionLang)
            )
        }
    }
  }
}
