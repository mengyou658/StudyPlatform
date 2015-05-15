package controllers


import play.api.mvc.{Action, Controller}
import play.filters.csrf.CSRFAddToken
import securesocial.core._
import securesocial.core.utils._

//import securesocial.core.providers.R

/**
 * Created by maximcherkasov on 04.05.15.
 */
class AuthController extends Controller {
//  private implicit val readsOAuth2Info = Json.reads[OAuth2Info]
//  val Company = "userName"
//  val Email = "firstName"

//  val form = Form[CompanyRegistrationInfo](
//    mapping(
//      Company -> nonEmptyText.verifying(Messages("aa"), company => {
//        // todo: see if there's a way to avoid waiting here :-\
//        import scala.concurrent.duration._
//        Await.result(env.userService.find(providerId, userName), 20.seconds).isEmpty
//      }),
//      FirstName -> nonEmptyText,
//      LastName -> nonEmptyText,
//      Password ->
//        tuple(
//          Password1 -> nonEmptyText.verifying(PasswordValidator.constraint),
//          Password2 -> nonEmptyText
//        ).verifying(Messages(PasswordsDoNotMatch), passwords => passwords._1 == passwords._2)
//    ) // binding
//      ((userName, firstName, lastName, password) => RegistrationInfo(Some(userName), firstName, lastName, password._1)) // unbinding
//      (info => Some((info.userName.getOrElse(""), info.firstName, info.lastName, ("", ""))))
//  )

//  def login = Action {
//      implicit request =>
//        Ok(views.html.login())
//  }

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


