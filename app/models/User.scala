package models

import play.api.libs.json._

case class User(id: Option[Long], name: String, age: Int)

object User{
  implicit val userFormat: OFormat[User] = Json.format[User]
}
