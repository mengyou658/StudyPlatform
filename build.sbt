name := "StudyPlatform"

version := "1.0"

lazy val `studyplatform` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws
  //    ,"com.typesafe.slick" % "slick_2.11" % "3.0.0-RC1"
  , "com.typesafe.slick" %% "slick" % "2.1.0"
  , "org.slf4j" % "slf4j-nop" % "1.6.4"
  , "mysql" % "mysql-connector-java" % "5.1.34"
  , "joda-time" % "joda-time" % "2.7"
  , "org.joda" % "joda-convert" % "1.7"
  , "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0"
  , "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  , "org.json4s" %% "json4s-native" % "3.2.11"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  