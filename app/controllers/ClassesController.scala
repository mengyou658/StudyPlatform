package controllers

import models.classes.{StudyClass, StudyClassJson}
import models.user.BasicUser
import play.api.libs.json._
import securesocial.core.RuntimeEnvironment
import services.classes.ClassService

/**
 * Created by m.cherkasov on 23.06.15.
 */
class ClassesController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2Classes = Json.reads[StudyClassJson]
  implicit object ProductWrites extends Writes[StudyClass] {

    def writes(s: StudyClass) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsNumber(s.userId),
      "description" -> JsString(s.description.getOrElse("")),
      "created" -> JsNumber(s.created.getMillis),
      "updated" -> JsNumber(s.updated.getMillis)
    )
  }
  def list() = SecuredAction.async {
    implicit request =>
      ClassService.findByUserId(request.user.main.userId) map {
        list =>
          Ok(Json.toJson(list))
      }
  }
}
