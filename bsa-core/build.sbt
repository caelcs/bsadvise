name := "bsa-core"

version := "0.1.0"

scalaVersion := "2.10.0"

resolvers ++= Seq(
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "SonarType repository" at "https://oss.sonatype.org/content/repositories/releases",
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "2.6.1",
  "org.json4s" %% "json4s-native" % "3.2.4",
  "org.json4s" %% "json4s-mongo" % "3.2.4",
  "com.novus" %% "salat" % "1.9.2-SNAPSHOT",
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "joda-time" % "joda-time" % "2.2",
  "com.typesafe" % "config" % "1.0.0",
  "org.slf4j" % "slf4j-simple" % "1.7.5"
)

