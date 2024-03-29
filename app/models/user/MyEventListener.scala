package models.user

/**
 * Created by maximcherkasov on 28.04.15.
 */
import play.api.Logger
import play.api.mvc.{RequestHeader, Session}
import securesocial.core._

/**
 * A sample event listener
 */
class MyEventListener extends EventListener[BasicUser] {
  def onEvent(event: Event[BasicUser], request: RequestHeader, session: Session): Option[Session] = {
    val eventName = event match {
      case LoginEvent(u) => "login"
      case LogoutEvent(u) => "logout"
      case SignUpEvent(u) => "signup"
      case PasswordResetEvent(u) => "password reset"
      case PasswordChangeEvent(u) => "password change"
    }

    Logger.info("traced %s event for user %s".format(eventName, event.user.main.userId))

    // retrieving the current language
    Logger.info("current language is %s".format(request2lang(request)))

    // Not changing the session so just return None
    // if you wanted to change the session then you'd do something like
    // Some(session + ("your_key" -> "your_value"))
    None
  }
}