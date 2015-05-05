package models

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by maximcherkasov on 01.05.15.
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
//object profiles extends TableQuery(new Profiles(_)) with WithDefaultSession
object UserTableQueries extends WithDefaultSession {
  object profiles extends TableQuery(new Profiles(_))
  object users extends TableQuery(new Users(_))
  object mailTokens extends TableQuery(new MailTokens(_))
  object userAuthenticators extends TableQuery(new UserAuthenticators(_))
  object oauth1s extends TableQuery(new OAuth1s(_))
  object oauth2s extends TableQuery(new OAuth2s(_))
  object passwords extends TableQuery(new Passwords(_))
}