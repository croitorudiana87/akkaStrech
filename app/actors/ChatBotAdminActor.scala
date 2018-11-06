package actors

/**
  * Created by diana on 05-Nov-18.
  */

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.event.LoggingReceive
import play.api.Logger

import scala.concurrent.duration._

class ChatBotAdminActor(system: ActorSystem) extends Actor with ActorLogging {

  import play.api.libs.concurrent.Execution.Implicits.defaultContext

  val room: ActorRef = ChatRoomActor(system)

  val cancellable = system.scheduler.schedule(0 seconds, 10 seconds, self, Tick)

  override def preStart() = {
    room ! JoinChatRoom
  }

  def receive = LoggingReceive {
    case ChatMessage(name, text) => Unit
    case (text: String) =>
      log.info(s"Sending to the room following message: $text")
      room ! ChatMessage(text.split(":")(0), text.split(":")(1))
    case Tick =>
      val response: String = "AdminBot:" + ActorHelper.get(GetStats, room)
      sender ! response
    case other =>
      log.error("issue - not expected: " + other)
  }
}

object ChatBotAdminActor {
  var bot: ActorRef = null

  def apply(system: ActorSystem) = {
    this.synchronized {
      Logger.info("Creating the ChatBotAdminActor")
      if (bot == null) bot = system.actorOf(Props(new ChatBotAdminActor(system)))
      bot
    }
  }
}
