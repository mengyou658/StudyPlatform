package controllers


import akka.util.HashCode
import io.really.jwt.JWT
import models.BasicUser
import play.api._
import play.api.Play.current
import play.api.cache._
import play.api.data.Form
import play.api.i18n.Lang
import play.api.libs.json.Json
import play.api.libs.ws.WS
import play.mvc.Result
import play.twirl.api.Html
import securesocial.controllers.{ChangeInfo, RegistrationInfo, ViewTemplates}
import securesocial.core.{SecureSocial, RuntimeEnvironment}
import play.api.mvc.{ Action, RequestHeader }
import scala.collection.immutable.HashMap
import scala.concurrent.ExecutionContext.Implicits.global

import play.libs.F.Function

import scala.concurrent.Future


//object Application extends Controller with SecuredConnection {
class Application(override implicit val env: RuntimeEnvironment[BasicUser]) extends securesocial.core.SecureSocial[BasicUser] {

  def index = SecuredAction {
    implicit request =>
      Ok(views.html.main())
  }

  case class InstagramCode(code: String, clientId: String, redirectUri: String)

  private implicit val readsInstagramm2Info = Json.reads[InstagramCode]

  def addSocialAccount(providerName: String)  = SecuredAction.async(parse.json) {
    implicit request =>
      println(request.user)

      providerName match {
        case "instagram" =>
          val instagram: Option[InstagramCode] = request.body.asOpt[InstagramCode]

          instagram match {
            case Some(x) =>

              val data1 = Map(
                "client_secret" -> Seq("a0613631c49c4862b4e2577fed7960b5"),
                "grant_type" -> Seq("authorization_code"),
                "redirect_uri" -> Seq(x.redirectUri),
                "code" -> Seq(x.code),
                "client_id" -> Seq(x.clientId)
              )

              val url: String = "https://api.instagram.com/oauth/access_token/"

              WS.url(url).post(data1).map(
                response =>
                  if (response.status == 200) {
                    println(response.status + "  " + response.body)
                    val respData = Json.obj(
                      "token" -> (Json.parse(response.body) \ "access_token"),
                      "user" -> (Json.parse(response.body) \ "user")
                    )
                    Ok(respData)
                  }
                  else {
                    println(response.status + "  " + response.body)
                    BadGateway(response.body)
                  }
              )
            case None =>
              Future(BadRequest(Json.obj("message" -> "Bad instagram request")))
          }

        case _ =>
          Future(BadRequest(Json.obj("message" -> ("Bad provider name: " + providerName))))
      }
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