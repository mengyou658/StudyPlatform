package controllers

import models.user.BasicUser
import play.api.libs.json.{Writes, JsString, Json}
import securesocial.core.RuntimeEnvironment
import services.product.ProductService
import models.product.Product

import scala.concurrent.Future

/**
 * Created by maximcherkasov on 24.05.15.
 */
class ProductController(override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser] {

  private implicit val readsJson2Product = Json.reads[Product]

  private val productService = new ProductService

  implicit object ProductWrites extends Writes[Product] {

    def writes(s: Product) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsString(s.userId.getOrElse("")),
      "name" -> JsString(s.name)
    )
  }

  def list() = SecuredAction.async {
    implicit request =>
//      val xxx: Core = Class.forName("rema7.core.Core1").newInstance().asInstanceOf[Core]
//      xxx.hello()
//      try {
//        val xxx = Class.forName("rema7.core.Core1")
//      } catch (ClassNotFoundException) {
//
//      }

      productService.findByUserId(request.user.main.userId) map {
        list =>
          Ok(Json.toJson(list))
      }
  }

  def save() = SecuredAction.async(parse.json) {
    implicit request =>

      request.body.asOpt[Product] match {
        case Some(p) =>
          productService.save(request.user.main.userId, p) map {
            list =>
              println(list)
              Ok(Json.toJson(list))
          }
        case None =>
          Future(BadRequest(Json.obj("message" -> ("Bad request" + request.body))))
      }
  }


}
