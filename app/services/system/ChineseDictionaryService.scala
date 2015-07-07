package services.system

import models.WithDefaultSession
import models.system.ChineseWord
import models.system.SystemTableQueries.chineseDictionary
import play.api.Logger

import scala.concurrent.Future
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by m.cherkasov on 19.06.15.
 */
object ChineseDictionaryService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def search(str: String): Future[List[(String,String)]] = withSession {
    implicit session =>
      Future successful {
        (for {
          coffee <- chineseDictionary if coffee.pinyin_search like (str+"%")
        } yield (coffee.simplified, coffee.pinyin)).take(10).list
      }
  }

}
