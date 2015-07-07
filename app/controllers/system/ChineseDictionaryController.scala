package controllers.system

import models.system.{ChineseWord, Lang}
import models.user.BasicUser
import play.api.libs.json.{JsNumber, JsString, Json, Writes}
import securesocial.core.RuntimeEnvironment
import services.system.{ChineseDictionaryService, LangService}

/**
 * Created by m.cherkasov on 19.06.15.
 */
class ChineseDictionaryController  (override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser] {

  implicit object WordWrites extends Writes[(String, String)] {

    def writes(l: (String, String)) = Json.obj(
      "simplified" -> JsString(l._1),
      "pinyin" -> JsString(l._2)
    )
  }

  def search(str: String) = SecuredAction.async {
    implicit request =>
     ChineseDictionaryService.search(str) map {
        list =>
          Ok(Json.toJson(list))
      }
  }
}
