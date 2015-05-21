logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

//resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.9")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0")
