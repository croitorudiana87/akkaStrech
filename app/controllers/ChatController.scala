package controllers

/**
  * Created by diana on 05-Nov-18.
  */

import javax.inject._

import actors.{ChatBotAdminActor, ChatUserActor}
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.Logger
import play.api.libs.streams._
import play.api.mvc._

@Singleton
class ChatController @Inject()(implicit val system: ActorSystem, materializer: Materializer)
  extends Controller {

  ChatBotAdminActor(system)

  def chat_display = Action { request =>
    Ok(views.html.chat_index()(Flash(Map())))
  }

  def ws = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => {
      Logger.info("Into chat controller! " + out)
      ChatUserActor.props(system)(out)
    }
    )
  }

}
