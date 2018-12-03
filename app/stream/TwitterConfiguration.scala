package stream

import com.typesafe.config.ConfigFactory

/**
  * Created by diana on 08-Nov-18.
  */
object TwitterConfiguration {
  val config = ConfigFactory.load.getConfig("Twitter")

  val apiKey = config.getString("apiKey")
  val apiSecret = config.getString("apiSecret")
  val accessToken = config.getString("accessToken")
  val accessTokenSecret = config.getString("accessTokenSecret")
}
