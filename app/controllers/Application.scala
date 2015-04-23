package controllers

import models.{Tables, LoginForm, User}
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

object Application extends Controller with Authentication {

  def index = Action {
    //check authorization
    Ok(views.html.index("Your new application is ready."))
  }

  def helloUser = SecuredAction {
    user =>
      Ok(s"hello ${user.email}").withCookies(
        Cookie("token", "aaa")
      )
  }

  val loginForm = Form(
    mapping(
      "email" -> text,
      "password" -> text
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def login = Action { implicit request =>
      Ok(views.html.login(loginForm))
  }

  def authenticate = SecuredAction { user =>
//    val user = loginForm.bindFromRequest()
//
//    user.fold(
//      hasErrors = { form =>
//        Redirect(routes.Application.login())
//      },
//      success = { newFood =>
//        Tables.foods.save(newFood)
        Redirect(routes.Application.helloUser())
//      }
//    )
  }

  def registration = Action { implicit request =>
    Ok(views.html.register(loginForm))
  }

  def register = Action { implicit request =>
    val user = loginForm.bindFromRequest()

    user.fold(
      hasErrors = { form =>
        Redirect(routes.Application.login())
      },
      success = { user =>
        Tables.users.register(user.email, user.password)
        Redirect(routes.Application.helloUser())
      }
    )
  }



  def logout = SecuredAction { implicit request =>
    Ok(views.html.login(loginForm)).discardingCookies(DiscardingCookie("token"))
  }

}