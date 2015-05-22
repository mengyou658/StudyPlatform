package controllers

import akka.actor.{Actor, Props, ActorRef}
import com.fasterxml.jackson.annotation.JsonValue
import models.user.BasicUser
import play.api.libs.json.{JsString, JsObject, Json, JsValue}
import play.api.mvc.{WebSocket, Controller}
import securesocial.core.RuntimeEnvironment
import play.api.Play.current

/**
 * Created by maximcherkasov on 10.05.15.
 */
class Profile(override implicit val env: RuntimeEnvironment[BasicUser]) extends securesocial.core.SecureSocial[BasicUser] {

  def list = SecuredAction(parse.json) {
    implicit request =>
      (request.body \ "method").asOpt[String].map {
        case "testRpc" =>
          Ok(Json.toJson("Test RPC called"))
        case _ =>
          Ok(Json.toJson(
            JsObject(
              Seq(
                "jsonrpc" -> JsString("2.0"),
                "id" -> JsString((request.body \ "id").asOpt[String].getOrElse("")),
                "error" -> JsObject(
                  Seq(
                    "code" -> JsString("-32601"),
                    "message" -> JsString("Method not found")
                  )
                )
              )
            )
          )
          )
      }.getOrElse{
        Ok(Json.toJson(
          JsObject(
            Seq(
              "jsonrpc" -> JsString("2.0"),
              "id" -> JsString((request.body \ "id").asOpt[String].getOrElse("")),
              "error" -> JsObject(
                Seq(
                  "code" -> JsString("-32601"),
                  "message" -> JsString("Method not found")
                )
              )
            )
          )
        )
        )
      }
  }

  def list1 = WebSocket.tryAcceptWithActor[JsValue, JsValue] {
    implicit request =>
    SecuredAction(Ok)(request).run.map {
      case Ok =>
        Right(MyWebSocketActor.props _)
      case r =>
        Left(r)
    }
  }

}

object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: JsValue =>
      println(util.Properties.versionString)
      println(msg)
      (msg \ "method").asOpt[String].map {
        case "testRpc" =>
          println("Test RPC called")
          out ! Json.toJson("Test RPC called")
        case _ =>
          out ! Json.toJson(
            JsObject(
              Seq(
                "jsonrpc" -> JsString("2.0"),
                "id" -> JsString((msg \ "id").asOpt[String].getOrElse("")),
                "error" -> JsObject(
                  Seq(
                    "code" -> JsString("-32601"),
                    "message" -> JsString("Method not found")
                  )
                )
              )
            )
          )
      }.getOrElse{
        out ! Json.toJson(
          JsObject(
            Seq(
              "jsonrpc" -> JsString("2.0"),
              "id" -> JsString((msg \ "id").asOpt[String].getOrElse("")),
              "error" -> JsObject(
                Seq(
                  "code" -> JsString("-32601"),
                  "message" -> JsString("Method not found")
                )
              )
            )
          )
        )
      }
    case _ =>
      out !  Json.toJson("!!! aaa")
  }
}