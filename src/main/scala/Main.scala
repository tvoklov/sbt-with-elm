import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {
    println(Source.fromResource("test.conf").getLines().mkString("\n"))
  }

}
