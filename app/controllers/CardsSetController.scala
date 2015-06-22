package controllers

import models.study.flashcards.{CardsSetJson, CardsSet}
import models.system.{Lang, LangJson}
import models.user.BasicUser
import play.api.libs.json._
import securesocial.core.RuntimeEnvironment
import services.study.cards.{FlashCardService, CardsSetService}

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 28.05.15.
 */
class CardsSetController (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser]{

  private implicit val readsJson2Lang = Json.reads[LangJson]
  private implicit val readsJson2CardSet = Json.reads[CardsSetJson]

  implicit object ProductWrites extends Writes[(CardsSet, (Lang, Lang))] {

    def writes(s: (CardsSet, (Lang, Lang))) = Json.obj(
      "id" -> JsString(s._1.id.getOrElse(-1).toString),
      "userId" -> JsNumber(s._1.userId),
      "name" -> JsString(s._1.name),
      "description" -> JsString(s._1.description.getOrElse("")),
      "termsLang" -> s._2._1,
      "definitionsLang" -> s._2._2,
      "created" -> JsNumber(s._1.created.getMillis),
      "updated" -> JsNumber(s._1.updated.getMillis)
    )
  }

  implicit val langWrites = new Writes[Lang] {
    def writes(lang: Lang) = Json.obj(
      "id" -> lang.id,
      "name" -> lang.name,
      "code" -> lang.code
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
          cardSet =>
            FlashCardService.findBySet(request.user.main.userId, setId.toLong)

            Ok(Json.toJson(cardSet.get))
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
