name := "bsa-core"

version := "0.1"

scalaVersion := "2.10.1"

resolvers ++= Seq(
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % "0.9",
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "joda-time" % "joda-time" % "2.2"
)

