package controllers

import models.Users1
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import views.html.helper.form


/**
 * Created by m.cherkasov on 24.04.15.
 */

case class LoginForm(email:String,
                     password:String)

case class AuthForm(email:String,
                     password:String,
                     repeatedPassword: String)


object Authentication extends Controller with SecuredConnection {

  val loginForm = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def loginSubmit = Action { implicit request =>

    val login = loginForm.bindFromRequest()

    login.fold(
      hasErrors = { form =>
        Redirect(routes.Authentication.registration()).flashing(
          Flash(form.data) + ("error" -> "Password mismatch")
        )
      },
      success = { user =>
        Users1.login(user.email, user.password) match {
          case Some(u) =>
            Redirect(routes.Application.helloUser()).withSession(
              "rema7.platform.auth" -> u.accessToken.getOrElse("")
            ).withCookies(Cookie("rema7.platform.token", u.accessToken.getOrElse("")))
          case None =>
            println("User not found.")
            Redirect(routes.Authentication.registration()).flashing(
              Flash(login.data) + ("error" -> "Login or password mismatch")
            )
        }
      }
    )

    Redirect(routes.Application.helloUser())
  }

  val authForm = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "repeatedPassword" -> nonEmptyText
    )(AuthForm.apply)(AuthForm.unapply) verifying(
      "Passwords must match",
        f => f.password == f.repeatedPassword
      )
  )


  def registration = Action { implicit request =>
     val form = if (request.flash.get("error").isDefined)
       authForm.bind(request.flash.data)
     else
       authForm

      Ok(views.html.register(form))
  }

  def registerSubmit = Action { implicit request =>
    val auth = authForm.bindFromRequest()

    auth.fold(
      hasErrors = { form =>
        Redirect(routes.Authentication.registration()).flashing(
          Flash(form.data) + ("error" -> "Password mismatch")
        )
      },
      success = { user =>
        Users1.register(user.email, user.password) match {
          case Some(u) =>
            Redirect(routes.Application.helloUser())
          case None =>
            Redirect(routes.Authentication.registration()).flashing(
              Flash(auth.data) + ("error" -> "User already exists")
            )
        }
      }
    )
  }

  def logout = SecuredAction { implicit request =>
    Ok(views.html.login(loginForm)).discardingCookies(DiscardingCookie("token"))
  }
}