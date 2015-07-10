package controllers

import models.group.{StudyGroup, StudyGroupJson}
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Writes, Json}
import securesocial.core.RuntimeEnvironment
import services.course.CourseService
import services.group.GroupService

/**
 * Created by m.cherkasov on 23.06.15.
 */
class GroupController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2Classes = Json.reads[StudyGroupJson]
  implicit object ProductWrites extends Writes[StudyGroup] {

    def writes(s: StudyGroup) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsNumber(s.userId),
      "description" -> JsString(s.description.getOrElse("")),
      "created" -> JsNumber(s.created.getMillis),
      "updated" -> JsNumber(s.updated.getMillis)
    )
  }
  def list() = SecuredAction.async {
    implicit request =>
      GroupService.findByUserId(request.user.main.userId) map {
        list =>
          Ok(Json.toJson(list))
      }
  }
}
