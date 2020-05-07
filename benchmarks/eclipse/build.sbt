lazy val renaissanceCore = RootProject(uri("../../renaissance-core"))

lazy val eclipseCompiler = (project in file("."))
  .settings(
    name := "jdt-compiler",
    version := (version in renaissanceCore).value,
    organization := (organization in renaissanceCore).value,
    scalafmtConfig := Some(file(".scalafmt.conf")),
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      "commons-io" % "commons-io" % "2.6",
      "org.eclipse.jdt.core.compiler" % "ecj" % "4.6.1"
    )
  )
  .dependsOn(
    renaissanceCore
  )
