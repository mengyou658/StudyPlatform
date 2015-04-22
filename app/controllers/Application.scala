package controllers

import play.api.data.Form
import play.api.mvc._

object Application extends Controller with Authentication {

  def index = Action {
    //check authorization
    Ok(views.html.index("Your new application is ready."))
  }

  def helloUser = AuthenticateMe {
    user => Ok(s"hello ${user.username}")
  }
}