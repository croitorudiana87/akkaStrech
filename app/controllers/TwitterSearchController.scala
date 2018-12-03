package controllers

import javax.inject.{Inject, Singleton}

import actors.TwitterActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc.{Action, Controller, Flash, WebSocket}

/**
  * Created by diana on 08-Nov-18.
  */
@Singleton
class TwitterSearchController @Inject()(implicit val system: ActorSystem, materializer: Materializer) extends Controller {
  def twitter_display = Action { request =>
    Ok(views.html.twitterMessageSearch()(Flash(Map())))
  }

  def ws = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => {
      TwitterActor.props(system)(out)
    }
    )
  }
}
