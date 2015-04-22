package controllers

import models.User
import play.api.mvc.{Result, Action, Controller, RequestHeader}

/**
 * Created by m.cherkasov on 22.04.15.
 */

// https://github.com/TheTunnelBear/PlayAuthenticationSample
trait Authentication {
  self:Controller =>

  def AuthenticateMe(f: User => Result) = Action { implicit request =>
    val user = AuthUtils.parseUserFromRequest

//    var accessConditions: List[Conditions.Condition] = List.empty

    if(user.isEmpty)
      Forbidden("Invalid username or password")
    else {
//      accessConditions.map(condition => condition(user.get)).collectFirst[String]{case Left(error) => error}
      user match {
//        case Some(error) => Forbidden(s"Conditions not met: $error")
//        case Some(user) => f(user.get)
        case _ => f(user.get)
      }
    }
  }
}

object AuthUtils {
  def parseUserFromCookie(implicit request: RequestHeader) =
    request.session.get("username").flatMap(username => User.find(username))

  def parseUserFromQueryString(implicit request:RequestHeader) = {
    val query = request.queryString.map { case (k, v) => k -> v.mkString }
    val username = query get "username"
    val password = query get "password"

    (username, password) match {
      case (Some(u), Some(p)) => User.find(u).filter(user => user.checkPassword(p))
      case _ => None
    }
  }

  def parseUserFromRequest(implicit request:RequestHeader):Option[User] = {
    parseUserFromQueryString orElse  parseUserFromCookie
  }

}