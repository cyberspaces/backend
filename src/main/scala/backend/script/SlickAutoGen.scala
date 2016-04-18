package backend.script

import scala.slick.codegen.SourceCodeGenerator

/**
 *  15-1-20.
 */
object SlickAutoGen {
  def main(args:Array[String]): Unit ={
    SourceCodeGenerator.main(Array(
      "scala.slick.driver.MySQLDriver",
      "com.mysql.jdbc.Driver",
      "jdbc:mysql://localhost:3306/lazystore",
      "src/main/scala/",
      "example.persistent",
      "appdev",
      "appdev78"
    ))
  }
}
