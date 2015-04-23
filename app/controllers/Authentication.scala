package controllers

import models.{Tables, User}
import play.api.mvc.{Result, Action, Controller, RequestHeader}

/**
 * Created by m.cherkasov on 22.04.15.
 */

// https://github.com/TheTunnelBear/PlayAuthenticationSample
trait Authentication {
  self:Controller =>

  def SecuredAction(f: User => Result) = Action { implicit request =>
    val user = AuthUtils.parseUserFromRequest

//    var accessConditions: List[Conditions.Condition] = List.empty

    if(user.isEmpty)
      Redirect(routes.Application.login())
//      Forbidden("Invalid username or password")
    else {
//      accessConditions.map(condition => condition(user.get)).collectFirst[String]{case Left(error) => error}
      user match {
//        case Some(error) => Forbidden(s"Conditions not met: $error")
//        case Some(user) => f(user.get)
        case _ => f(user.get).withNewSession.addingToSession(
          "com.rema7.studyplatform.auth" -> user.get.email
        )
      }
    }
  }
}

object AuthUtils {
  def parseTokenFromCookie(implicit request: RequestHeader) = {
    println(request.session.get("com.rema7.studyplatform.token"))
    request.session.get("com.rema7.studyplatform.token").flatMap {
      token => Tables.users.findByToken(token)
    }
  }

  def parseUserFromQueryString(implicit request:RequestHeader) = {
    val query = request.queryString.map { case (k, v) => k -> v.mkString }
    val email = query get "email"
    val password = query get "password"

    (email, password) match {
      case (Some(e), Some(p)) => Tables.users.findByEmailAndPassword(e,p)
//      case (Some(u), Some(p)) => User.find(u).filter(user => user.checkPassword(p))
      case _ => None
    }
  }

  def parseUserFromRequest(implicit request:RequestHeader):Option[User] = {
    parseUserFromQueryString orElse parseTokenFromCookie
  }

}