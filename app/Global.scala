/**
 * Created by maximcherkasov on 28.04.15.
 */
import java.lang.reflect.Constructor

import models.{InMemoryUserService, MyEventListener, MyUser}
import play.api.{GlobalSettings, Logger}
import securesocial.core.RuntimeEnvironment
import securesocial.core.providers.{FacebookProvider, UsernamePasswordProvider}

import scala.collection.immutable.ListMap

object Global extends GlobalSettings {
  /**
   * The runtime environment
   */
  object SecureSocialRuntimeEnvironment extends RuntimeEnvironment.Default[MyUser] {
    //override lazy val routes = new CustomRoutesService()
    override lazy val userService: InMemoryUserService = new InMemoryUserService()
    override lazy val eventListeners = List(new MyEventListener())
    override lazy val providers = ListMap(
      include(new UsernamePasswordProvider[MyUser](userService, avatarService, viewTemplates, passwordHashers))
      // ... other providers
     ,include(new FacebookProvider(routes, cacheService, oauth2ClientFor(FacebookProvider.Facebook) ))
    )
  }

  /**
   * An implementation that checks if the controller expects a RuntimeEnvironment and
   * passes the instance to it if required.
   */
  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    val instance = controllerClass.getConstructors.find { c =>
      val params = c.getParameterTypes
      params.length == 1 && params(0) == classOf[RuntimeEnvironment[MyUser]]
    }.map {
      _.asInstanceOf[Constructor[A]].newInstance(SecureSocialRuntimeEnvironment)
    }
    instance.getOrElse(super.getControllerInstance(controllerClass))
  }
}