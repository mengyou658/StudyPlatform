package controllers

import models.study.flashcards.{FlashCard, FlashCardJson}
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Json, Writes}
import securesocial.core.RuntimeEnvironment
import services.study.cards.FlashCardService

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 28.05.15.
 */
class FlashCardsController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2FlashCard = Json.reads[FlashCardJson]
  implicit object ProductWrites extends Writes[FlashCard] {

    def writes(s: FlashCard) = Json.obj(
      "id" -> JsString(s.id.getOrElse(-1).toString),
      "userId" -> JsNumber(s.userId),
      "term" -> JsString(s.term),
      "transcription" -> JsString(s.transcription.getOrElse("")),
      "definition" -> JsString(s.definition),
      "created" -> JsNumber(s.created.getMillis),
      "updated" -> JsNumber(s.updated.getMillis)
    )
  }

  def list() = SecuredAction.async {
    implicit request =>
      FlashCardService.findByUserId(request.user.main.userId) map {
        cards =>
          Ok(Json.toJson(cards))
      }
  }

  def getFlashCard(cardId: String) = SecuredAction.async {
    implicit request =>
      FlashCardService.findById(request.user.main.userId, cardId.toLong) map {
        card =>
          Ok(Json.toJson(card))
      }
  }

  def getFlashCardBySet(setId: String) = SecuredAction.async {
    implicit request =>
      try {
        FlashCardService.findBySet(request.user.main.userId, setId.toLong) map {
          card =>
            Ok(Json.toJson(card))
        }
      } catch {
        case  e: NumberFormatException =>
          Future(BadRequest("setId is not a number"))
      }
  }

  def removeFlashCardFromSet(setId: String, cardId: String) = SecuredAction.async {
    implicit request =>
      try {
        FlashCardService.removeFromSet(request.user.main.userId, setId.toLong, cardId.toLong) map {
          card =>
            Ok(Json.toJson(card))
        }
      } catch {
        case  e: NumberFormatException =>
          Future(BadRequest("setId or cardId is not a number"))
      }
  }

  def save(setId: String) = SecuredAction.async(parse.json) {
    implicit request =>

      request.body.asOpt[FlashCardJson] match {
        case Some(c) =>
          try {
            FlashCardService.save(request.user.main.userId, setId.toLong, c) map {
              card =>
                println(card)
                Ok(Json.toJson(card))
            }
          }catch {
            case  e: NumberFormatException =>
              Future(BadRequest("setId is not number"))
            case  e: NoSuchElementException =>
              Future(NotFound("Cards set or card in set not found"))
          }
        case None =>
          Future(BadRequest(Json.obj("message" -> ("Bad request" + request.body))))
      }
  }
}
