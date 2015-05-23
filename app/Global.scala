/**
 * Created by maximcherkasov on 28.04.15.
 */
import java.lang.reflect.Constructor

import controllers.MyViews
import models.user.{MyEventListener, BasicUser}
import play.api.mvc.WithFilters
import play.api.{GlobalSettings, Logger}
import play.filters.gzip.GzipFilter
import securesocial.core.RuntimeEnvironment
import securesocial.core.authenticator.{HttpHeaderAuthenticatorBuilder, CookieAuthenticatorBuilder}
import securesocial.core.providers.{VkProvider, FacebookProvider, UsernamePasswordProvider}
import securesocial.core.services.AuthenticatorService
import services.{SlickAuthenticatorStore, SlickUserService}

import scala.collection.immutable.ListMap

object Global extends WithFilters(new GzipFilter(shouldGzip =
  (request, response) => {
    val contentType = response.headers.get("Content-Type")
    contentType.exists(_.startsWith("text/html")) || request.path.endsWith("jsroutes.js")
  }
)) with GlobalSettings {
  /**
   * The runtime environment
   */
  object MyRuntimeEnvironment extends RuntimeEnvironment.Default[BasicUser] {
    //override lazy val routes = new CustomRoutesService()
    override lazy val userService: SlickUserService = new SlickUserService()
    override lazy val eventListeners = List(new MyEventListener())
    override lazy val authenticatorService: AuthenticatorService[BasicUser] = new AuthenticatorService[BasicUser](
      new CookieAuthenticatorBuilder[BasicUser](new SlickAuthenticatorStore, idGenerator),
      new HttpHeaderAuthenticatorBuilder[BasicUser](new SlickAuthenticatorStore, idGenerator)
    )

    override lazy val viewTemplates = new MyViews(this)

    override lazy val providers = ListMap(
      include(new UsernamePasswordProvider[BasicUser](userService, avatarService, viewTemplates, passwordHashers))
      // ... other providers
     ,include(new FacebookProvider(routes, cacheService, oauth2ClientFor(FacebookProvider.Facebook) ))
     ,include(new VkProvider(routes, cacheService, oauth2ClientFor(VkProvider.Vk)))
    )
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A = {

    val instance  = controllerClass.getConstructors.find { c =>
      val params = c.getParameterTypes
      params.length == 1 && params(0) == classOf[RuntimeEnvironment[BasicUser]]

    }.map {

      _.asInstanceOf[Constructor[A]].newInstance(MyRuntimeEnvironment)
    }

    instance.getOrElse(super.getControllerInstance(controllerClass))
  }
}