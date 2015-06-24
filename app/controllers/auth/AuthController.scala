package controllers.auth

import models.user.BasicUser
import play.api.libs.json.Json
import play.api.mvc._
import securesocial.core._
import securesocial.core.authenticator.{CookieAuthenticator, AuthenticatorStore}
import securesocial.core.providers.FacebookProvider

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 24.06.15.
 */
class AuthController(override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser] {
  private implicit val readsOAuth2Info = Json.reads[OAuth2Info]

  private def builder() = {
    //todo: this should be configurable maybe
    env.authenticatorService.find(CookieAuthenticator.Id).getOrElse {
//      logger.error(s"[securesocial] missing CookieAuthenticatorBuilder")
      throw new AuthenticationException()
    }
  }

  def authenticateMobile(providerName: String) = Action.async(parse.json) {
    implicit request =>

    // format: { "accessToken": "..." }
    val oauth2Info = request.body.asOpt[OAuth2Info]

    env.providers.get(providerName).get match {
      case provider : FacebookProvider =>
        Future(NotFound(Json.obj("error" -> "!!!!")))

      //        provider.fillProfile(oauth2Info.get) flatMap {
//          profile =>
//          env.userService.find(profile.providerId,profile.userId) flatMap {
//            user =>
//              val newSession = Events.fire(new LoginEvent(user)).getOrElse(request.session)
////              builder().fromUser()
//              Future(NotFound(Json.obj("error" -> "!!!!")))
//          }
//        }
    }
//    val basicProfile = FacebookProvider.
//    val filledUser = provider.fillProfile(
//      SocialUser(IdentityId("", provider.id), "", "", "", None, None, provider.authMethod, oAuth2Info = oauth2Info))
//    UserService.find(filledUser.identityId) map { user =>
//      val newSession = Events.fire(new LoginEvent(user)).getOrElse(session)
//      Authenticator.create(user).fold(
//        error => throw error,
//        authenticator => Ok(Json.obj("sessionId" -> authenticator.id))
//          .withSession(newSession - SecureSocial.OriginalUrlKey - IdentityProvider.SessionId - OAuth1Provider.CacheKey)
//          .withCookies(authenticator.toCookie)
//      )
//    } getOrElse NotFound(Json.obj("error" -> "user not found"))

      Future(NotFound(Json.obj("error" -> "!!!!")))
  }
}

/*
GET/auth/authenticate/$provider<[^/]+>@securesocial.controllers.ProviderController@.authenticate(provider:String, redirectTo:Option[String])

POST/auth/authenticate/$provider<[^/]+>@securesocial.controllers.ProviderController@.authenticateByPost(provider:String, redirectTo:Option[String])

POST/auth/api/authenticate/$provider<[^/]+>
 */