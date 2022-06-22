import sbt.Keys._
import sbt.nio.Keys.watchTriggers
import sbt.{Compile, Def, _}

object ElmCompile extends AutoPlugin {

  object autoImport {
    val elmMainFile = settingKey[String]("Elm entry point location")
    val elmSrcDirectory = settingKey[File]("Elm source files location")
  }

  import autoImport._

  val compileElm = taskKey[Seq[File]]("Compiles elm code")

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    elmSrcDirectory := (Compile / sourceDirectory).value / "elm",
    compileElm := {
      import scala.sys.process._

      val s: TaskStreams = streams.value
      val shell: Seq[String] =
        if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c")
        else Seq("bash", "-c")

      val elmIn = elmSrcDirectory.value / elmMainFile.value
      val elmOut = (Compile / resourceManaged).value / "/elm/elm.js"

      if (elmIn.exists()) {
        val make: Seq[String] =
          shell :+ ("elm make " + elmIn + " --output " + elmOut)

        if ((make !) == 0) {
          s.log.success("fronted build done")
          Seq(elmOut)
        } else {
          throw new IllegalStateException("frontend build failed")
        }
      } else {
        s.log.info("no elm files defined, skipping frontend compilation")
        Seq.empty
      }
    },
    compileElm / watchTriggers += elmSrcDirectory.value.toGlob,
    Compile / resourceGenerators += compileElm,
    Compile / packageBin / mappings += {
      (Compile / resourceManaged).value / "elm" -> "elm"
    }
  )

}
