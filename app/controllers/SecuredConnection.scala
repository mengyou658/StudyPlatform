package controllers

import controllers.Application._
import models.{Users, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.text

/**
 * Created by m.cherkasov on 22.04.15.
 */



// https://github.com/TheTunnelBear/PlayAuthenticationSample
trait SecuredConnection {
  self:Controller =>

  def SecuredAction(f: User => Result) = Action { implicit request =>
    val user = SecuredUtils.parseUserFromRequest

//    var accessConditions: List[Conditions.Condition] = List.empty

    if(user.isEmpty)
      Redirect(routes.Authentication.login())
//      Forbidden("Invalid username or password")
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

object SecuredUtils {
  def parseTokenFromCookie(implicit request: RequestHeader) = {
    println(request.session.get("rema7.platform.auth"))
    println(request.cookies.get("rema7.platform.token"))

    request.session.get("rema7.platform.auth").flatMap {
//      token => Tables.users.findByToken(token)
        token => Users.findByToken(token)
    }
  }

//  def parseUserFromQueryString(implicit request:RequestHeader) = {
//    val query = request.queryString.map { case (k, v) => k -> v.mkString }
//    val email = query get "email"
//    val password = query get "password"
//
//    (email, password) match {
//      case (Some(e), Some(p)) => Users.findByEmailAndPassword(e,p)
////      case (Some(u), Some(p)) => User.find(u).filter(user => user.checkPassword(p))
//      case _ => None
//    }
//  }

  def parseUserFromRequest(implicit request:RequestHeader):Option[User] = {
//    parseUserFromQueryString orElse
      parseTokenFromCookie
  }

}