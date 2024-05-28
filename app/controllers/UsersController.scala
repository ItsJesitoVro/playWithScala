package controllers

import play.api.mvc._
import play.api.libs.json._
import models.User

import javax.inject.{Inject, Singleton}

@Singleton
class UsersController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def createUser: Action[JsValue] = Action(parse.json) { implicit request =>
    val userResult = request.body.validate[User]
    userResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))) // Retorno si hay un error al recibir un body, params, headers, etc.
      },
      user => {
        // Aqui se agrega la logica para agregar a BD o el proceso que se desea realizar al pegar a este endpoint.
        Ok(Json.obj("status" -> "success", "typeMethod" -> "Is Create", "data" -> Json.toJson(user))) // Respuesta que va a retornar al usuario (respuesta de la red).
      }
    )
  }

  def updateUser(id: Long): Action[JsValue] = Action(parse.json) { implicit request =>
    val userResult = request.body.validate[User]
    userResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))) // Retorno si hay un error al recibir un body, params, headers, etc.
      },
      user => {
        // Aqui se agrega la logica para editar la informacion de la BD o el proceso que se desea realizar al pegar a este endpoint.
        // Va a pasar siempre cuando la respuesta sea correcta.
        Ok(Json.obj("status" -> "success", "typeMethod" -> "Is Update", "message" -> s"User $id updated", "data" -> Json.toJson(user)))
      }
    )
  }

  def getUser(id: Long): Action[AnyContent] = Action { implicit request =>
    // Aqui se agrega la logica para recuperar la informacion de la BD mediante el id.
    val maybeUser: Option[User] = // Se deberia recuperar esto de una base de datos y sino se encuentra mandar un mensaje diferente
      if (id == 1) {
      Some(User("Jezer Cruz", 22)) // Supongamos que encontramos el usuario
    } else {
      None // Supongamos que no encontramos el usuario
    }
    maybeUser match {
      case Some(user) =>
        Ok(Json.obj("status" -> "success", "typeMethod" -> "Is get unique user", "data" -> Json.toJson(user)))
      case None =>
        BadRequest(Json.obj("status" -> "error", "typeMethod" -> "Is get unique user", "message" -> "No se encontró el usuario")) // Retorno por si no encuentra el usuario
    }
  }

  def getAllUser: Action[AnyContent] = Action { implicit request =>
    // Aqui se agrega la logica para recuperar toda la informacion de la BD.
    // Supongamos que encontramos los usuarios
    val users = List(
      User("Jezer Perez", 22),
      User("Roma Lopez", 25),
      User("Alfredo Cruz", 40)
    ) // Se deberia recuperar esto de una base de datos y sino se encuentra mandar un mensaje diferente

    Ok(Json.obj("status" -> "success", "typeMethod" -> "Is get all data", "count" -> users.length, "data" -> Json.toJson(users)))
  }

  def deleteUser(id: Long): Action[AnyContent] = Action { implicit request =>
    // Aqui se agrega la logica para eliminacion la informacion especifica del usuario de la BD.
    // Supongamos que eliminamos el usuario con éxito
    val searchUser: Option[User] =  // Se deberia recuperar esto de una base de datos
      if (id == 1) {
        Some(User("Jezer Cruz", 22)) // Supongamos que encontramos el usuario
      } else {
        None // Supongamos que no encontramos el usuario
      }
    searchUser match {
      case Some(user) =>
        Ok(Json.obj("status" -> "success", "typeMethod" -> "Is Delete unique user", "message" -> s"User with ID: $id is deleted, name: ${user.name}"))
      case None =>
        BadRequest(Json.obj("status" -> "error", "typeMethod" -> "Is Delete unique user", "message" -> "No se encontró el usuario, no existe")) // Retorno por si no encuentra el usuario
    }
  }
}
