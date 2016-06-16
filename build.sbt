
organization := "com.gu"
description := "Bard - Telling the story of Facebook page performance."
scalaVersion := "2.11.8"
name := "bard"
scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked", "-target:jvm-1.8", "-Xfatal-warnings")
scalacOptions in doc in Compile := Nil

lazy val bard = (project in file(".")).enablePlugins(PlayScala, RiffRaffArtifact)

resolvers += "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq(
  ws,
  "com.restfb" % "restfb" % "1.24.0",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "com.gu" %% "play-googleauth" % "0.5.0",
  "com.gu" %% "configuration-magic-play2-4" % "1.2.0",
  "com.github.ben-manes.caffeine" % "caffeine" % "1.3.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test"
)

routesGenerator := InjectedRoutesGenerator
riffRaffPackageName := "bard"
riffRaffPackageType := (packageZipTarball in Universal).value
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")


initialize := {
  val _ = initialize.value
  assert(sys.props("java.specification.version") == "1.8",
    "Java 8 is required for this project.")
}
