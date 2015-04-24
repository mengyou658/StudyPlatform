package controllers

import play.api.mvc._

object Application extends Controller with SecuredConnection {
  def helloUser = SecuredAction {
    user =>
      Ok(s"hello ${user.email}")
  }
}