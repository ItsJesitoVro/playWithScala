package models

import play.api.libs.json._

case class User(name: String, age: Int)

object User{
  implicit val userFormat = Json.format[User]
}
