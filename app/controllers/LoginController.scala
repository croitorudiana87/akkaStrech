package controllers

import javax.inject.Singleton

import com.google.inject.Inject
import models.User
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages
import play.api.libs.json._
import play.api.mvc.{Controller, _}
import play.api.{Configuration, Logger}

/**
  * Created by diana on 06-Nov-18.
  */
@Singleton
class LoginController @Inject() (val configuration: Configuration)
  extends Controller {

  case class Login(username: String, password: String)

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Login.apply)(Login.unapply)
  )

  /** Check credentials, generate token and serve it back as auth token in a Cookie */
  def login = Action(parse.json) { implicit request =>
    Logger.info("trying to log with request"+request)
    implicit val messages = Messages(request2lang, null)
    loginForm.bind(request.body).fold( // Bind JSON body to form values
      formErrors => BadRequest(Json.obj("err" -> formErrors.errorsAsJson)),
      loginData => {
        User.findByUsernameAndPassword(loginData.username, loginData.password) map { user =>
          Ok(Json.obj(
            "user" -> user.username
          ))
        } getOrElse NotFound(Json.obj("err" -> "User Not Found or Password Invalid"))
      }
    )
  }

}
