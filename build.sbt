ThisBuild / version := "template"
ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .enablePlugins(ElmCompile)
  .settings(
    name := "sbt-elm",
    elmMainFile := "Main.elm"
  )
