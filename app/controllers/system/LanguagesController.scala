package controllers.system

import models.system.Lang
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Writes, Json}
import securesocial.core.RuntimeEnvironment
import services.system.LangService

/**
 * Created by m.cherkasov on 19.06.15.
 */
class LanguagesController  (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser] {

  implicit object LangWrites extends Writes[Lang] {

    def writes(l: Lang) = Json.obj(
      "id" -> JsNumber(l.id),
      "name" -> JsString(l.name),
      "code" -> JsString(l.code)
    )
  }

  def list() = SecuredAction.async {
    implicit request =>
     LangService.list map {
        list =>
          Ok(Json.toJson(list))
      }
  }
}
