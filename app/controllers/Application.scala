package controllers

import models.BasicUser
import play.api._
import play.api.Play.current
import play.api.cache._
import securesocial.core.{SecureSocial, RuntimeEnvironment}
import play.api.mvc.{ Action, RequestHeader }
import scala.concurrent.ExecutionContext.Implicits.global


//object Application extends Controller with SecuredConnection {
class Application(override implicit val env: RuntimeEnvironment[BasicUser]) extends securesocial.core.SecureSocial[BasicUser] {

  def helloUser = SecuredAction {
    implicit request =>
      Ok(views.html.index(request.user.main))
  }

  def linkResult = SecuredAction { implicit request =>
    Ok(views.html.linkResult(request.user))
  }

  def currentUser = Action.async { implicit request =>
    SecureSocial.currentUser[BasicUser].map { maybeUser =>
      val userId = maybeUser.map(_.main.userId).getOrElse("unknown")
      Ok(s"Your id is $userId")
    }
  }



  /*

  val routeCache = {
    val jsRoutesClass = classOf[routes.javascript]
    val controllers = jsRoutesClass.getFields.map(_.get(null))
    controllers.flatMap { controller =>
      controller.getClass.getDeclaredMethods.map { action =>
        action.invoke(controller).asInstanceOf[play.core.Router.JavascriptReverseRoute]
      }
    }
  }

  def jsRoutes(varName: String = "jsRoutes") = Cached(_ => "jsRoutes", duration = 86400) {
    Action { implicit request =>
      Ok(Routes.javascriptRouter(varName)(routeCache: _*)).as(JAVASCRIPT)
    }
  }
   */
}