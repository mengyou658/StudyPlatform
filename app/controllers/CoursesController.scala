package controllers

import models.courses.{Course, CourseJson}
import models.user.BasicUser
import play.api.libs.json._
import securesocial.core.RuntimeEnvironment
import services.course.CourseService

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 23.06.15.
 */
class CoursesController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2Classes = Json.reads[CourseJson]
  implicit object ProductWrites extends Writes[Course] {

    def writes(s: Course) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsNumber(s.userId),
      "name" -> JsString(s.name),
      "shortName" -> JsString(s.name),
      "description" -> JsString(s.shortName),
      "created" -> JsNumber(s.created.getMillis),
      "updated" -> JsNumber(s.updated.getMillis)
    )
  }
  def list() = SecuredAction.async {
    implicit request =>
      CourseService.findByUserId(request.user.main.userId) map {
        list =>
          Ok(Json.toJson(list))
      }
  }

  def getCourse(courseId: String) = SecuredAction.async {
    implicit request =>
      try {
        CourseService.findById(request.user.main.userId, courseId.toLong) map {
          course =>
//            FlashCardService.findBySet(request.user.main.userId, setId.toLong)

            Ok(Json.toJson(course.get))
        }
      } catch {
        case  e: NumberFormatException =>
          Future(BadRequest("setId is not number"))
      }
  }

  def save() = SecuredAction.async(parse.json) {
    implicit request =>
      request.body.asOpt[CourseJson] match {
        case Some(c) =>
          CourseService.save(request.user.main.userId, c) map {
            list =>
              Ok(Json.toJson(list))
          }
        case None =>
          Future(BadRequest(Json.obj("message" -> ("Bad request" + request.body))))
      }
  }
}
