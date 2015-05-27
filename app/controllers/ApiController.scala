package controllers

import models.user.BasicUser
import play.api.libs.json.{JsValue, JsPath, Reads, Json}
import play.api.mvc.Action
import securesocial.core.RuntimeEnvironment
import play.api.libs.functional.syntax._

import scala.concurrent.Future

/**
 * Created by m.cherkasov on 26.05.15.
 */
class ApiController(override implicit val env: RuntimeEnvironment[BasicUser])
  extends securesocial.core.SecureSocial[BasicUser] {

  case class JsonRpc(id: String, jsonrpc: String , method: String, params: JsValue)
  private implicit val jsonRpcReads = Json.reads[JsonRpc]

/*
code	message	meaning
-32700	Parse error	Invalid JSON was received by the server.
An error occurred on the server while parsing the JSON text.
-32600	Invalid Request	The JSON sent is not a valid Request object.
-32601	Method not found	The method does not exist / is not available.
-32602	Invalid params	Invalid method parameter(s).
-32603	Internal error	Internal JSON-RPC error.
-32000 to -32099	Server error	Reserved for implementation-defined server-errors.

 */
  def getMethods(plugin: String) = Action.async(parse.json) {
    implicit request =>

      try {
        val instanse = Class.forName(plugin).newInstance()
        val jsonRequest = request.body.asOpt[JsonRpc]

        jsonRequest match {
          case Some(r) =>

            val method = instanse.getClass.getMethod(r.method)
            method.invoke(instanse)

            SecuredAction(Ok)(request).run.map {
              case Ok =>
                Ok(Json.obj("message" -> ("Bad request" + request.body)))
              case r1 =>
                BadRequest(Json.obj("message" -> ("Bad request" + request.body)))
            }
          case None =>
            Future(BadRequest(Json.obj("code" -> "-32700",
              "message" -> ("Bad request" + request.body))))
        }
      }
      catch {
        case e: NoSuchMethodException =>
          Future(BadRequest(Json.obj("code" -> "-32601",
            "message" -> ("Bad method: " + request.body))))
        case e: ClassNotFoundException =>
          Future(BadRequest(Json.obj("code" -> "-32700",
            "message" -> ("Bad plugin: " + plugin))))
      }
  }
}
