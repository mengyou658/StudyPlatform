package controllers.auth

import models.user.BasicUser
import play.api.Logger
import play.api.i18n.Messages
import play.api.libs.json.Json
import play.api.mvc._
import securesocial.core._
import securesocial.core.authenticator.{CookieAuthenticator, AuthenticatorStore}
import securesocial.core.providers.FacebookProvider
import securesocial.core.services.SaveMode

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 24.06.15.
 */
class AuthController(override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser] {

  val logger = Logger(this.getClass)
  private implicit val readsOAuth2Info = Json.reads[OAuth2Info]

  private def builder() = {
    //todo: this should be configurable maybe
    env.authenticatorService.find(CookieAuthenticator.Id).getOrElse {
      logger.error(s"[securesocial] missing CookieAuthenticatorBuilder")
      throw new AuthenticationException()
    }
  }

  def authenticateMobile(provider: String) = UserAwareAction.async(parse.json) {
    implicit request =>

//      env.providers.get(provider).get.authenticate() flatMap  {
//        maybeExisting =>
//          println(maybeExisting)
//          Future(NotFound(Json.obj("error" -> "!!!!")))
//      }

      val oauth2Info = request.body.asOpt[OAuth2Info]
      env.providers.get(provider).get match {
        case provider : FacebookProvider =>
          provider.fillProfile(oauth2Info.get) flatMap {
            profile =>
              env.userService.find(profile.providerId,profile.userId) flatMap  {
                maybeExisting =>
                  val mode = if (maybeExisting.isDefined) SaveMode.LoggedIn else SaveMode.SignUp
                  env.userService.save(profile, mode) flatMap {
                    userForAction =>
                      logger.debug(s"!!! - [securesocial] user completed authentication: provider = ${profile.providerId}, userId: ${profile.userId}, mode = $mode")
                      val evt = if (mode == SaveMode.LoggedIn) new LoginEvent(userForAction) else new SignUpEvent(userForAction)
                      val sessionAfterEvents = Events.fire(evt).getOrElse(request.session)

                      builder().fromUser(userForAction).flatMap { authenticator =>
//                        logger.error(sessionAfterEvents -
//                                                    IdentityProvider.SessionId -
//                                                    IdentityProvider.SessionId -
//                                                    OAuth1Provider.CacheKey)

                        Future.successful(
                          Ok(Json.obj("sessionId" -> authenticator.id))
                            .withSession(sessionAfterEvents - SecureSocial.OriginalUrlKey - IdentityProvider.SessionId - OAuth1Provider.CacheKey)
                            .withCookies(new Cookie("id", authenticator.id))
                        )

//                        authenticator.starting()
//                        Future.successful(Ok(Json.obj(
//                          "urlKey" -> "!!!!", IdentityProvider.SessionId
//                          "sessionId" -> IdentityProvider.SessionId
//                          )
//                        ))
//                        Redirect(toUrl(sessionAfterEvents)).withSession(sessionAfterEvents -
//                          IdentityProvider.SessionId -
//                          IdentityProvider.SessionId -
//                          OAuth1Provider.CacheKey).startingAuthenticator(authenticator)
                      }
                  }
              }
          }
      }
  }
//      env.providers.get(providerName).get.authenticate() flatMap  {
//        maybeExisting =>
//          println(maybeExisting)
//          Future(NotFound(Json.obj("error" -> "!!!!")))
//      }

    // format: { "accessToken": "..." }
//    val oauth2Info = request.body.asOpt[OAuth2Info]
//    env.providers.get(providerName).get match {
//      case provider : FacebookProvider =>
//
//        provider.fillProfile(oauth2Info.get) flatMap {
//          profile =>
//
//            env.userService.find(profile.providerId,profile.userId) flatMap {
//              maybeExisting =>
//                val mode = if (maybeExisting.isDefined) SaveMode.LoggedIn else SaveMode.SignUp
//                env.userService.save(profile, mode).flatMap { userForAction =>
//                }
//
////                val newSession = Events.fire(new LoginEvent(user)).getOrElse(request.session)
////                builder().fromUser(user.get.)
////              Future(NotFound(Json.obj("error" -> "!!!!")))
//          }
//        }
//    }
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


 // }
}

/*
GET/auth/authenticate/$provider<[^/]+>@securesocial.controllers.ProviderController@.authenticate(provider:String, redirectTo:Option[String])

POST/auth/authenticate/$provider<[^/]+>@securesocial.controllers.ProviderController@.authenticateByPost(provider:String, redirectTo:Option[String])

POST/auth/api/authenticate/$provider<[^/]+>
 */