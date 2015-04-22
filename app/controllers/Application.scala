package controllers

import models.User
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

object Application extends Controller with Authentication {

  def index = Action {
    //check authorization
    Ok(views.html.index("Your new application is ready."))
  }

  def helloUser = AuthenticateMe {
    user =>
      Ok(s"hello ${user.username}").withCookies(
        Cookie("token", "aaa")
      )
  }

  val userForm = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )((username, password) => User.apply(username, password, isPremium = false, 0))
      ((user: User) => Option(user.username, user.password))
  )

  def login = Action { request =>
      Ok(views.html.login(userForm))
  }

  def authenticate = Action { implicit request =>
    val user = userForm.bindFromRequest()

    Ok(views.html.login(userForm))
  }


  def logout = AuthenticateMe { implicit request =>
    Ok(views.html.login(userForm)).discardingCookies(DiscardingCookie("token"))
  }

}