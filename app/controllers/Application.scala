package controllers

import models.user.BasicUser

import play.api.data.Form
import play.api.i18n.Lang
import play.twirl.api.Html
import securesocial.controllers.{ChangeInfo, RegistrationInfo, ViewTemplates}
import securesocial.core.{RuntimeEnvironment}
import play.api.mvc.{RequestHeader }
import services.course.CourseService
import services.social.SocialAccountService

import scala.concurrent.Future


//object Application extends Controller with SecuredConnection {
class Application(override implicit val env: RuntimeEnvironment[BasicUser]) extends securesocial.core.SecureSocial[BasicUser] {

  private val socialService = new SocialAccountService

  def index = SecuredAction {
    implicit request =>
//      socialService.findByUserId(request.user.main.userId) map {
//        list =>
//          println(list)
              Ok(views.html.main(request.user.main))
//      }

  }

}

class MyViews(env: RuntimeEnvironment[_]) extends ViewTemplates {
  implicit val implicitEnv = env

  override def getLoginPage(form: Form[(String, String)], msg: Option[String])(implicit request: RequestHeader, lang: Lang): Html = {
    views.html.login(form, msg)
  }

  override def getPasswordChangePage(form: Form[ChangeInfo])(implicit request: RequestHeader, lang: Lang): Html = {
    securesocial.views.html.passwordChange(form)
  }

  override def getNotAuthorizedPage(implicit request: RequestHeader, lang: Lang): Html = {
    securesocial.views.html.notAuthorized()
  }

  override def getStartSignUpPage(form: Form[String])(implicit request: RequestHeader, lang: Lang): Html = {
//    securesocial.views.html.Registration.startSignUp(form)
    views.html.registration.startSingUp(form)
  }

  override def getSignUpPage(form: Form[RegistrationInfo], token: String)(implicit request: RequestHeader, lang: Lang): Html = {
//    securesocial.views.html.Registration.signUp(form, token)
    views.html.registration.singUp(form,token)
  }

  override def getResetPasswordPage(form: Form[(String, String)], token: String)(implicit request: RequestHeader, lang: Lang): Html = {
//    securesocial.views.html.Registration.resetPasswordPage(form, token)
    views.html.registration.resetPassword(form, token)
  }

  override def getStartResetPasswordPage(form: Form[String])(implicit request: RequestHeader, lang: Lang): Html = {
//    securesocial.views.html.Registration.startResetPassword(form)
    views.html.registration.startResetPassword(form)
  }
}