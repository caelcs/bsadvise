/*
 * Module:        Bsadvise-build
 * Class:         BsadviseBuild.scala
 * Last modified: 2012-10-15 20:46:51 EDT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import sbt._
import Keys._

object BsadviseBuild extends Build {

  import Dependencies._
  import BuildSettings._

  val testDeps = Seq(scalatest)
  val coreDeps = Seq(casbah, json4sNative, json4sMongo, jodaTime, slf4jApi, slf4jSimple) ++ testDeps

  lazy val salat = Project(
    id = "bsadvise",
    base = file("."),
    settings = buildSettings ++ Seq(
      publishArtifact in (Compile, packageBin) := false, 
      publishArtifact in (Compile, packageDoc) := false, 
      publishArtifact in (Compile, packageSrc) := false 
    ),
    aggregate = Seq(bsacore)
  ) dependsOn(bsacore)

  lazy val bsacore = Project(
    id = "bsacore",
    base = file("bsa-core"),
    settings = buildSettings ++ Seq(libraryDependencies ++= coreDeps)
  )
}

object BuildSettings {

  import Repos._

  val buildOrganization = "ar.com.caeldev"
  val buildVersion = "0.1.0.-SNAPSHOT"
  val buildScalaVersion = "2.10.0"

  val buildSettings = Defaults.defaultSettings ++ Format.settings ++ Publish.settings ++ net.virtualvoid.sbt.graph.Plugin.graphSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    shellPrompt := ShellPrompt.buildShellPrompt,
    parallelExecution in Test := false,
    testFrameworks += TestFrameworks.Specs2,
    resolvers ++= Seq(typeSafeRepo, typeSafeSnapsRepo, oss, ossSnaps),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    crossScalaVersions := Seq("2.9.1", "2.9.2", "2.10.0")
  )
}

object Format {

  import com.typesafe.sbt.SbtScalariform._

  lazy val settings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := formattingPreferences
  )

  lazy val formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences().
      setPreference(AlignParameters, true).
      setPreference(AlignSingleLineCaseStatements, true).
      setPreference(CompactControlReadability, true).
      setPreference(CompactStringConcatenation, true).
      setPreference(DoubleIndentClassDeclaration, true).
      setPreference(FormatXml, true).
      setPreference(IndentLocalDefs, true).
      setPreference(IndentPackageBlocks, true).
      setPreference(IndentSpaces, 2).
      setPreference(MultilineScaladocCommentsStartOnFirstLine, true).
      setPreference(PreserveSpaceBeforeArguments, false).
      setPreference(PreserveDanglingCloseParenthesis, false).
      setPreference(RewriteArrowSymbols, false).
      setPreference(SpaceBeforeColon, false).
      setPreference(SpaceInsideBrackets, false).
      setPreference(SpacesWithinPatternBinders, true)
  }
}

object Publish {
  lazy val settings = Seq(
    publishMavenStyle := true,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/caelwinner/bsadvise")),
    pomExtra := (
      <scm>
        <url>git://github.com/caelwinner/bsadvise.git</url>
        <connection>scm:git://github.com/caelwinner/bsadvise.git</connection>
      </scm>
      <developers>
        <developer>
          <id>caelwinner</id>
          <name>Adolfo Custidiano</name>
          <url>http://github.com/caelwinner</url>
        </developer>
      </developers>)
  )
}

object Dependencies {

  val salat = "com.novus" %% "salat" % "1.9.2-SNAPSHOT"
  val scalatest = "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"
  val jodaTime = "joda-time" % "joda-time" % "2.2"
  val config = "com.typesafe" % "config" % "1.0.0"
  val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.5"
  val slf4jSimple = "org.slf4j" % "slf4j-simple" % "1.7.5"
  val casbah = "org.mongodb" %% "casbah" % "2.6.1"
  val json4sNative = "org.json4s" %% "json4s-native" % "3.2.4"
  val json4sMongo = "org.json4s" %% "json4s-mongo" % "3.2.4"
}

object Repos {
  val typeSafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  val typeSafeSnapsRepo = "Typesafe Snaps Repo" at "http://repo.typesafe.com/typesafe/snapshots/"
  val oss = "OSS Sonatype" at "http://oss.sonatype.org/content/repositories/releases/"
  val ossSnaps = "OSS Sonatype Snaps" at "http://oss.sonatype.org/content/repositories/snapshots/"
}

// Shell prompt which show the current project, git branch and build version
object ShellPrompt {
  object devnull extends ProcessLogger {
    def info (s: => String) {}
    def error (s: => String) { }
    def buffer[T] (f: => T): T = f
  }
  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
  )

  val buildShellPrompt = {
    (state: State) => {
      val currProject = Project.extract (state).currentProject.id
      "%s:%s:%s> ".format (
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}