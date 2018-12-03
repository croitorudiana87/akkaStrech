package stream

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Twitter, TwitterFactory}

import scala.collection.JavaConverters._

/**
  * Created by diana on 08-Nov-18.
  */
object TwitterClient {

  def getInstance: Twitter = {

    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(TwitterConfiguration.apiKey)
      .setOAuthConsumerSecret(TwitterConfiguration.apiSecret)
      .setOAuthAccessToken(TwitterConfiguration.accessToken)
      .setOAuthAccessTokenSecret(TwitterConfiguration.accessTokenSecret)

    val tf = new TwitterFactory(cb.build())
    tf.getInstance()
  }

  def retrieveTweets(term: String) = {
    val query = new Query(term)
    query.setCount(100)
    getInstance.search(query).getTweets.asScala.toSet
  }
}
