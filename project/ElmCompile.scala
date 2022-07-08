import sbt.Keys._
import sbt.nio.Keys.watchTriggers
import sbt.{ Compile, Def, _ }

object ElmCompile extends AutoPlugin {

  object autoImport {
    val elmMainFile     = settingKey[String]("Elm entry point location")
    val elmSrcDirectory = settingKey[File]("Elm source files location")
    val elmOutputFile   = settingKey[String]("Elm output file name")

    val compileElm = taskKey[Seq[File]]("Compiles elm code")
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    // defaults
    elmMainFile := "Main.elm",
    elmSrcDirectory := (Compile / sourceDirectory).value / "elm",
    elmOutputFile := "/elm/elm.js",

    // elm compilation
    compileElm := {
      import scala.sys.process._

      val s: TaskStreams = streams.value
      val shell: Seq[String] =
        if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c")
        else Seq("bash", "-c")

      val elmIn  = elmSrcDirectory.value / elmMainFile.value
      val elmOut = (Compile / resourceManaged).value / elmOutputFile.value

      if (elmIn.exists()) {
        val make: Seq[String] =
          shell :+ ("elm make " + elmIn + " --output " + elmOut)

        if ((make !) == 0) {
          s.log.success("fronted build done")
          Seq(elmOut)
        } else
          throw new IllegalStateException("frontend build failed")
      } else {
        s.log.info("no elm files defined, skipping frontend compilation")
        Seq.empty
      }
    },

    // integration into the compile pipeline
    compileElm / watchTriggers += elmSrcDirectory.value.toGlob / "*",
    Compile / resourceGenerators += compileElm,
    Compile / packageBin / mappings += {
      (Compile / resourceManaged).value / "elm" -> "elm"
    }
  )

}
