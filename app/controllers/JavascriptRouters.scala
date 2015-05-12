package controllers

import play.api._
import play.api.mvc._
import play.api.cache._
import play.api.Play.current

/**
 * Created by maximcherkasov on 09.05.15.
 */
object JavascriptRouters extends Controller {
  def jsRoutes(varName: String = "jsRoutes") = Cached(_ =>"jsRoutes", duration = 86400) {
    Action { implicit request =>
      Ok(
        Routes.javascriptRouter(varName)(
          routes.javascript.Profile.list
//        routes.AuthController.
//                    routes.JavascriptRouters
          //          routes.javascript.Cluster.clusterMetricsWebsocket,
          //          services.routes.javascript.Factorial.websocket
          // TODO Add your routes here
        )
      ).as(JAVASCRIPT)
    }
  }
}
