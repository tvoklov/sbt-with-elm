import sbt.Keys._
import sbt.{Compile, Def, _}

object ElmCompile extends AutoPlugin {

  object autoImport {
    val elmMainFile = settingKey[String]("Elm entry point location")
  }

  import autoImport._

  val compileElm = taskKey[Seq[File]]("Compiles elm code")

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    compileElm := {
      import scala.sys.process._

      val s: TaskStreams = streams.value
      val shell: Seq[String] =
        if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c")
        else Seq("bash", "-c")

      val elmIn =
        (Compile / sourceDirectory).value / "elm" / (ThisProject / elmMainFile).value
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
    Compile / resourceGenerators += compileElm,
    Compile / packageBin / mappings += {
      (Compile / resourceManaged).value / "elm" -> "elm"
    }
  )

}
