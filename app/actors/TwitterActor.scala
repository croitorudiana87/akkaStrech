package actors

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.event.LoggingReceive
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import stream.{Author, Tweet, TwitterClient}
import twitter4j.Status

/**
  * Created by diana on 08-Nov-18.
  */
class TwitterActor(out: ActorRef) extends Actor with ActorLogging {

  def receive = LoggingReceive {
    case (text: String) => createAkkaFlow(text)
    case other =>
      log.error("issue - not expected: " + other)
  }


  def createAkkaFlow(searchItem: String) = {

    implicit val flowMaterializer = ActorMaterializer()

    val source = Source(TwitterClient.retrieveTweets(searchItem))

    val normalize = Flow[Status].map { t =>
      Tweet(Author(t.getUser().getName()), t.getText())
    }
    val sink = Sink.foreach[Tweet] { tweet =>
      import spray.json._
      import stream.TweetJsonSupport._
      var tweetDeserialized: String = tweet.toJson.prettyPrint
      out ! tweetDeserialized
    }

    source.via(normalize).runWith(sink)
  }
}

object TwitterActor {

  def props(system: ActorSystem)(out: ActorRef) = {
    Props(new TwitterActor(out)) //out is the Play Framework actor used to send the response to UI
  }

}
