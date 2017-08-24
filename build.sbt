organization := "com.typesafe"

name := "jstranspiler"

libraryDependencies ++= Seq(
  "org.webjars" % "mkdirp" % "0.3.5",
  "org.webjars" % "when-node" % "3.2.2"
)

mappings in (Compile, packageBin) ++= Seq(
  (file("src") / "main.js").getPath,
  "package.json" 
  ) map(f => baseDirectory.value / f -> (file("META-INF") / "resources" / "webjars" / name.value / version.value / f).getPath)

crossPaths := false

// Publish settings
homepage := Some(url("https://github.com/typesafehub/jstranspiler"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))
pomExtra := {
  <scm>
    <url>git@github.com:typesafehub/jstranspiler.git</url>
    <connection>scm:git:git@github.com:typesafehub/jstranspiler.git</connection>
  </scm>
  <developers>
    <developer>
      <id>playframework</id>
      <name>Play Framework Team</name>
      <url>https://github.com/playframework</url>
    </developer>
  </developers>
}
pomIncludeRepository := { _ => false }

// Sonatype settings

xerial.sbt.Sonatype.SonatypeKeys.sonatypeProfileName := "com.typesafe"

// Release settings

releaseCrossBuild := false
releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseTagName := (version in ThisBuild).value

import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  releaseStepCommand("sonatypeRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
