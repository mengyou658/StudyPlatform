package controllers

import models.MyUser
import securesocial.core.{SecureSocial, RuntimeEnvironment}
import play.api.mvc.{ Action, RequestHeader }
import scala.concurrent.ExecutionContext.Implicits.global

//object Application extends Controller with SecuredConnection {
class Application(override implicit val env: RuntimeEnvironment[MyUser]) extends securesocial.core.SecureSocial[MyUser] {

  def helloUser = SecuredAction {
    implicit request =>
      Ok(views.html.index(request.user.main))
  }

  def linkResult = SecuredAction { implicit request =>
    Ok(views.html.linkResult(request.user))
  }

  def currentUser = Action.async { implicit request =>
    SecureSocial.currentUser[MyUser].map { maybeUser =>
      val userId = maybeUser.map(_.main.userId).getOrElse("unknown")
      Ok(s"Your id is $userId")
    }
  }

}