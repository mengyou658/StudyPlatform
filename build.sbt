name := "StudyPlatform"

version := "1.0"

lazy val `studyplatform` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"


//libraryDependencies ++= Seq(
//  "org.webjars" %% "webjars-play" % "2.3.0",
//  "org.webjars" % "requirejs" % "2.1.17"
//)


libraryDependencies ++= Seq( jdbc , anorm , cache , ws
  //    ,"com.typesafe.slick" % "slick_2.11" % "3.0.0-RC1"
  , "com.typesafe.slick" %% "slick" % "2.1.0"
  , "mysql" % "mysql-connector-java" % "5.1.34"
  , "joda-time" % "joda-time" % "2.7"
  , "org.joda" % "joda-convert" % "1.7"
  , "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0"
  , "ws.securesocial" %% "securesocial" % "3.0-M3"
  , "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  , "org.json4s" %% "json4s-native" % "3.2.11"
  , "org.webjars" %% "webjars-play" % "2.3.0-2"
  , "org.webjars" % "bootstrap" % "3.3.4"
  , "org.webjars" % "angularjs" % "1.3.15"
  , "org.webjars" % "angular-ui-bootstrap" % "0.13.0"
  , "org.webjars" % "angular-ui-router" % "0.2.14"
  , "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.1"
  , "org.webjars.bower" % "jquery" % "2.1.3"
  , "org.webjars" % "requirejs" % "2.1.17"
  , "org.webjars" % "requirejs-domready" % "2.0.1-2"
  , "org.webjars.bower" % "angular-route" % "1.3.15"
  , "io.really" %% "jwt-scala" % "1.2.2"
  , "org.webjars" % "satellizer" % "0.10.1"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

dependencyOverrides += "org.scala-lang" % "scala-library" % scalaVersion.value

scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  //"-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code",
  "-language:reflectiveCalls"
)