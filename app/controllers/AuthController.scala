package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import securesocial.core._
import securesocial.core.providers.FacebookProvider

//import securesocial.core.providers.R

/**
 * Created by maximcherkasov on 04.05.15.
 */
class AuthController  extends Controller {
  private implicit val readsOAuth2Info = Json.reads[OAuth2Info]

//  def authenticateMobile(providerName: String) = Action(parse.json) { implicit request =>
//    // format: { "accessToken": "..." }
//    val oauth2Info = request.body.asOpt[OAuth2Info]
////    val provider = Registry.providers.get(providerName).get
////    val filledUser = provider.fillProfile(
////      SocialUser(IdentityId("", provider.id), "", "", "", None, None, provider.authMethod, oAuth2Info = oauth2Info))
////    UserService.find(filledUser.identityId) map { user =>
////      val newSession = Events.fire(new LoginEvent(user)).getOrElse(session)
////      Authenticator.create(user).fold(
////        error => throw error,
////        authenticator => Ok(Json.obj("sessionId" -> authenticator.id))
////          .withSession(newSession - SecureSocial.OriginalUrlKey - IdentityProvider.SessionId - OAuth1Provider.CacheKey)
////          .withCookies(authenticator.toCookie)
////      )
////    } getOrElse NotFound(Json.obj("error" -> "user not found"))
//  }
}
