package models.user

import models.WithDefaultSession

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by maximcherkasov on 01.05.15.
 */

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