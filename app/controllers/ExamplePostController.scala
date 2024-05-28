package controllers

import play.api.mvc._

import javax.inject.{Inject, Singleton}

@Singleton
class ExamplePostController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}
