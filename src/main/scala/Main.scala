import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {
    println(Source.fromResource("elm/elm.js").getLines().mkString("\n"))
  }

}
