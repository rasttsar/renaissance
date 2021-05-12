lazy val renaissanceCore = RootProject(uri("../../renaissance-core"))

lazy val eclipse = (project in file("."))
  .settings(
    name := "eclipse",
    version := (version in renaissanceCore).value,
    organization := (organization in renaissanceCore).value,
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      "commons-io" % "commons-io" % "2.7",
      "org.eclipse.jdt.core.compiler" % "ecj" % "4.6.1",
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.2"
    )
  )
  .dependsOn(
    renaissanceCore
  )
