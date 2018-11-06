package controllers

/**
  * Created by diana on 05-Nov-18.
  */
import javax.inject._

import play.api.mvc._

/**
  * This class creates the actions and the websocket needed.
  */
@Singleton
class HomeController @Inject() extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome to Main page!")(Flash(Map("success" -> "Welcome!"))))
  }
}