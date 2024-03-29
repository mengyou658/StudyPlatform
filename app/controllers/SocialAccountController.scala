package controllers

import com.fasterxml.jackson.annotation.JsonValue
import models.social.SocialAccount
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Writes, Json}
import play.api.libs.ws.WS
import securesocial.core.RuntimeEnvironment
import services.social.SocialAccountService

import scala.concurrent.Future
import play.api.Play.current

/**
 * Created by m.cherkasov on 22.05.15.
 */
class SocialAccountController(override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser] {

  case class oauth2Code(code: String, clientId: String, redirectUri: String)
  private implicit val readsInstagramm2Info = Json.reads[oauth2Code]


  case class InstagramAccount(access_token: String, user: InstagramUser)
  case class InstagramUser(username: String, bio: String, website: String, profile_picture: String,
                           full_name: String, id: String)
  private implicit val readsInstagramUser2User = Json.reads[InstagramUser]
  private implicit val readsInstagram2Account = Json.reads[InstagramAccount]

  case class VkAccount(access_token: String, user_id: Int, expires_in: Int)
  private implicit val readsVk2Account = Json.reads[VkAccount]


  private val socialService = new SocialAccountService()

  def addSocialAccount(providerName: String)  = SecuredAction.async(parse.json) {
    implicit request =>
      println(request.user)

      val user = request.user

      providerName match {
        case "instagram" =>
          val instagram: Option[oauth2Code] = request.body.asOpt[oauth2Code]

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

                    val account: Option[InstagramAccount] =Json.parse(response.body).asOpt[InstagramAccount]
                    account match {
                      case Some(a) =>
                        val userJson = Json.parse(response.body) \ "user"
                        val respData = Json.obj(
                          "token" -> a.access_token,
                          "user" ->  userJson
                        )
                        socialService.save(SocialAccount(
                          None,
                          user.main.userId,
                          "instagram",
                          a.user.id,
                          a.access_token,
                          Some(a.user.username),
                          Some(a.user.profile_picture)
                        ))

                        Ok(respData)
                      case None =>
                        println(response.status + "  " + response.body)
                        BadGateway(response.body)
                    }

                  }
                  else {
                    println(response.status + "  " + response.body)
                    BadGateway(response.body)
                  }
              )
            case None =>
              Future(BadRequest(Json.obj("message" -> "Bad instagram access token request")))
          }
        case "vk" =>
          val vk: Option[oauth2Code] = request.body.asOpt[oauth2Code]

          vk match {
            case Some(x) =>
              val url: String = "https://oauth.vk.com/access_token?client_secret=v9N6PT5OJAPclX9ykM4D"+
                "&redirect_uri="+x.redirectUri+
                "&code="+x.code+
                "&client_id="+x.clientId

              println(url)
              WS.url(url).get().map(
                response =>
                  if (response.status == 200) {
                    println(response.status + "  " + response.body)
                    val account: Option[VkAccount] =Json.parse(response.body).asOpt[VkAccount]
                    account match {
                      case Some(a) =>

                        val respData = Json.obj(
                          "token" -> a.access_token
                        )
                        socialService.save(SocialAccount(
                          None,
                          user.main.userId,
                          "vk",
                          a.user_id.toString,
                          a.access_token,
                          Some(""),
                          Some("")
                        ))
                        Ok(respData)
                    }
                      //                    val account: Option[InstagramAccount] =Json.parse(response.body).asOpt[InstagramAccount]
//                    account match {
//                      case Some(a) =>
//                        val userJson = Json.parse(response.body) \ "user"
//                        val respData = Json.obj(
//                          "token" -> a.access_token,
//                          "user" ->  userJson
//                        )
//                        socialService.save(SocialAccount(
//                          None,
//                          user.main.userId,
//                          "instagram",
//                          a.user.id,
//                          a.access_token,
//                          Some(a.user.username),
//                          Some(a.user.profile_picture)
//                        ))

//                        Ok(respData)
//                      case None =>
//                        println(response.status + "  " + response.body)
//                        BadGateway(response.body)
//                    }

                  }
                  else {
                    println(response.status + "  " + response.body)
                    BadGateway(response.body)
                  })
            case None =>
              Future(BadRequest(Json.obj("message" -> "Bad vk access token request")))
          }
          Future(BadRequest(Json.obj("message" -> ("Bad provider name: " + providerName))))
        case _ =>
          Future(BadRequest(Json.obj("message" -> ("Bad provider name: " + providerName))))
      }
  }

  implicit object SocialAccountWrites extends Writes[SocialAccount] {

    def writes(s: SocialAccount) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsString(s.userId),
      "provider" -> JsString(s.provider),
      "accountId" -> JsString(s.accountId),
      "accessToken" -> JsString(s.accessToken),
      "username" -> JsString(s.username.getOrElse("")),
      "profilePicture" -> JsString(s.profilePicture.getOrElse(""))
    )
  }

  def list() = SecuredAction.async {
    implicit request =>
      socialService.findByUserId(request.user.main.userId) map {
        list =>
          println(list)
          Ok(Json.toJson(list))
      }

  }
}
