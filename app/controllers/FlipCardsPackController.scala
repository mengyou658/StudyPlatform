package controllers

import models.study.flashcards.{FlashCardsPackJson, FlashCardsPack}
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Writes, Json}
import securesocial.core.RuntimeEnvironment
import services.study.cards.FlashCardsPackService

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 28.05.15.
 */
class FlipCardsPackController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2Product = Json.reads[FlashCardsPackJson]
  implicit object ProductWrites extends Writes[FlashCardsPack] {

    def writes(s: FlashCardsPack) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsNumber(s.userId),
      "name" -> JsString(s.name),
      "description" -> JsString(s.description.getOrElse("")),
      "created" -> JsNumber(s.created.getMillis),
      "updated" -> JsNumber(s.updated.getMillis)
    )
  }

  def list() = SecuredAction.async {
    implicit request =>
      FlashCardsPackService.findByUserId(request.user.main.userId) map {
        list =>
          Ok(Json.toJson(list))
      }
  }

  def getPack(packId: String) = SecuredAction.async {
    implicit request =>
      FlashCardsPackService.findByPackId(request.user.main.userId, packId.toLong) map {
        card =>
          Ok(Json.toJson(card))
      }
  }

  def save() = SecuredAction.async(parse.json) {
    implicit request =>

      request.body.asOpt[FlashCardsPackJson] match {
        case Some(c) =>
          FlashCardsPackService.create(request.user.main.userId, c) map {
            list =>
              println(list)
              Ok(Json.toJson(list))
          }
        case None =>
          Future(BadRequest(Json.obj("message" -> ("Bad request" + request.body))))
      }
  }
}
