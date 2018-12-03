name := "play-websocket-scala"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"
//JsEngineKeys.engineType := JsEngineKeys.EngineType.Node


// scalaz-bintray resolver needed for specs2 library
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies += ws

libraryDependencies += "org.webjars" % "flot" % "0.8.3"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.6"
libraryDependencies += cache
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += "com.typesafe.play" %% "anorm" % "2.4.0"
libraryDependencies +="com.typesafe.akka" %% "akka-stream" % akkaVersion
libraryDependencies +="org.twitter4j" % "twitter4j-stream" % "4.0.3"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.5"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.5"

lazy val akkaVersion = "2.4.18"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
