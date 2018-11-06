package actors

/**
  * Created by diana on 05-Nov-18.
  */

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}
import akka.event.LoggingReceive

class ChatRoomActor extends Actor with ActorLogging {

  var users = Set[ActorRef]()

  def receive = LoggingReceive {
    case msg: ChatMessage =>
      users foreach {
        _ ! msg
      }
    case JoinChatRoom =>
      log.info("Asked to join the room by: "+sender)
      users += sender
      context watch sender
    case GetStats =>
      val stats: String = "online users[" + users.size + "] - users[" + users.map(a => a.hashCode().toString()).mkString("|") + "]"
      log.info(stats)
      sender ! stats
    case Terminated(user) =>
      log.info("User exited!")
      users -= user
  }
}

object ChatRoomActor {
  var room: ActorRef = null

  def apply(system: ActorSystem) = {
    this.synchronized {
      if (room == null) room = system.actorOf(Props[ChatRoomActor])
      room
    }
  }

}
