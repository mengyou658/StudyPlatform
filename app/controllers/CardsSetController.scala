package controllers

import models.study.flashcards.{CardsSetJson, CardsSet}
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Writes, Json}
import securesocial.core.RuntimeEnvironment
import services.study.cards.{FlashCardService, CardsSetService}

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 28.05.15.
 */
class CardsSetController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2CardSet = Json.reads[CardsSetJson]
  implicit object ProductWrites extends Writes[CardsSet] {

    def writes(s: CardsSet) = Json.obj(
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
      CardsSetService.findByUserId(request.user.main.userId) map {
        list =>
          Ok(Json.toJson(list))
      }
  }

  def getSet(setId: String) = SecuredAction.async {
    implicit request =>
      try {
        CardsSetService.findById(request.user.main.userId, setId.toLong) map {
          card =>
            FlashCardService.findBySet(request.user.main.userId, setId.toLong)

            Ok(Json.toJson(card))

        }
      } catch {
        case  e: NumberFormatException =>
          Future(BadRequest("setId is not number"))
      }
  }

  def removeSet(setId: String) = SecuredAction.async {
    implicit request =>
      try {
        CardsSetService.remove(request.user.main.userId, setId.toLong) map {
          card =>
            Ok(Json.toJson(card))
        }
      } catch {
        case  e: NumberFormatException =>
          Future(BadRequest("setId is not number"))
      }
  }

  def save() = SecuredAction.async(parse.json) {
    implicit request =>

      request.body.asOpt[CardsSetJson] match {
        case Some(c) =>
          CardsSetService.save(request.user.main.userId, c) map {
            list =>
              println(list)
              Ok(Json.toJson(list))
          }
        case None =>
          Future(BadRequest(Json.obj("message" -> ("Bad request" + request.body))))
      }
  }
}
