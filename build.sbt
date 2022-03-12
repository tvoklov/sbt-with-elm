import scala.language.postfixOps
import scala.sys.process._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "sbt-elm"
  )

val elmMainFile = "Main.elm"

// elm compilation magic
lazy val compileElm = taskKey[Seq[File]]("Compiles elm code")
compileElm := {
  val s: TaskStreams = streams.value
  val shell: Seq[String] =
    if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c")
    else Seq("bash", "-c")

  val elmIn = (Compile / sourceDirectory).value / "elm" / elmMainFile
  val elmOut = (Compile / resourceManaged).value / "/elm/elm.js"

  val make: Seq[String] = shell :+ ("elm make " + elmIn + " --output " + elmOut)

  if ((make !) == 0) {
    s.log.success("fronted build done")
    Seq(elmOut)
  } else {
    throw new IllegalStateException("frontend build failed")
  }
}

Compile / resourceGenerators += compileElm
Compile / packageBin / mappings += {
  (Compile / resourceManaged).value / "elm" -> "elm"
}
