package actors

/**
  * Created by diana on 05-Nov-18.
  */

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.event.LoggingReceive
import play.api.Logger

class ChatUserActor(room: ActorRef, out: ActorRef) extends Actor with ActorLogging {

  override def preStart() = {
    log.info("Ask to join the room " +room)
    room ! JoinChatRoom
  }

  def receive = LoggingReceive {
    case ChatMessage(name, text) if sender == room =>
      val result: String = name + ":" + text
      Logger.info("Received the message "+result)
      out ! result
    case (text: String) =>
      Logger.info("Received a string "+text)
      room ! ChatMessage(text.split(":")(0), text.split(":")(1))//the message from the actor itself
    case other =>
      log.error("issue - not expected: " + other)
  }
}

object ChatUserActor {

  def props(system: ActorSystem)(out: ActorRef) = {
    Logger.info("Creating a ChatUserActor with out "+out)
    Props(new ChatUserActor(ChatRoomActor(system), out)) //out is the Play Framework actor used to send the response to UI
  }
}
