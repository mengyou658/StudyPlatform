package models
import slick.driver.MySQLDriver.api._

/**
 * Created by m.cherkasov on 22.05.15.
 */
trait WithDefaultSession {

  def withSession[T](block: (Session => T)) = {
    val a = _root_.play.api.Configuration.empty
    val conf = play.api.Play.maybeApplication.map(_.configuration).getOrElse(a)
    val databaseURL = conf.getString("db.default.url").get
    val databaseDriver = conf.getString("db.default.driver").get
    val databaseUser = conf.getString("db.default.user").getOrElse("")
    val databasePassword = conf.getString("db.default.password").getOrElse("")

    val database = Database.forURL(url = databaseURL,
      driver = databaseDriver,
      user = databaseUser,
      password = databasePassword)

    database withSession {
      session =>
        block(session)
    }
  }
}