package actors

/**
  * Created by diana on 05-Nov-18.
  */

case class ChatMessage(name: String, text: String)

case class Stats(users: Set[String])

object JoinChatRoom

object Tick

object GetStats
