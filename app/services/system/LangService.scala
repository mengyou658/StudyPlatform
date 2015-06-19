package services.system

import models.WithDefaultSession
import models.system.Lang
import models.system.SystemTableQueries.langs
import play.api.Logger
import scala.slick.driver.MySQLDriver.simple._
import scala.concurrent.Future

/**
 * Created by m.cherkasov on 19.06.15.
 */
object LangService extends WithDefaultSession {
  val logger = Logger(this.getClass)

  def list: Future[List[Lang]] = withSession {
    implicit session =>
      Future successful {
        langs.list
      }
  }
}
