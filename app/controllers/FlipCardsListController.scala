package controllers

import models.study.flashcards.{PackJson, Pack}
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Writes, Json}
import securesocial.core.RuntimeEnvironment
import services.study.cards.FlipCardsListService

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 28.05.15.
 */
class FlipCardsListController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2Product = Json.reads[PackJson]
  implicit object ProductWrites extends Writes[Pack] {

    def writes(s: Pack) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsString(s.userId),
      "name" -> JsString(s.name),
      "description" -> JsString(s.description.getOrElse("")),
      "created" -> JsNumber(s.created.getMillis),
      "updated" -> JsNumber(s.updated.getMillis)
    )
  }

  def list() = SecuredAction.async {
    implicit request =>
      FlipCardsListService.findByUserId(request.user.main.userId) map {
        list =>
          Ok(Json.toJson(list))
      }
  }


  def save() = SecuredAction.async(parse.json) {
    implicit request =>

      request.body.asOpt[PackJson] match {
        case Some(c) =>
          FlipCardsListService.create(request.user.main.userId, c) map {
            list =>
              println(list)
              Ok(Json.toJson(list))
          }
        case None =>
          Future(BadRequest(Json.obj("message" -> ("Bad request" + request.body))))
      }
  }
}
