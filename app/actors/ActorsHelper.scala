package actors

/**
  * Created by diana on 05-Nov-18.
  */

object ActorHelper {

  import akka.actor.ActorRef
  import akka.pattern.ask
  import akka.util.Timeout
  import play.api.libs.concurrent.Execution.Implicits.defaultContext

  import scala.concurrent.Await
  import scala.concurrent.duration._

  def get(msg:Any,actor:ActorRef):String = {
    implicit val timeout = Timeout(5 seconds)
    val result = (actor ? msg).mapTo[String].map { result => result.toString }
    Await.result(result, 5.seconds)
  }

}
