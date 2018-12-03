package stream

import spray.json.DefaultJsonProtocol

/**
  * Created by diana on 08-Nov-18.
  */
case class Author(name:String)
case class HashTag(name: String){
  require(name.startsWith("#"), "name must start with hash tag")
}
case class Tweet (author: Author, body:String){
  def hashTags:Set[HashTag]={
    body.split(" ").collect{case t if t startsWith("#") => HashTag(t)}.toSet
  }
}
object TweetJsonSupport extends DefaultJsonProtocol {
  implicit val authorFormat = jsonFormat1(Author)
  implicit val tweetFormat = jsonFormat2(Tweet)
}
