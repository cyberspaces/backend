package backend.base.persistent.script

import scala.slick.codegen.SourceCodeGenerator

/**
 *  14-12-10.
 */
object SlickAutoGen {
  def main(args:Array[String]): Unit ={
    SourceCodeGenerator.main(Array(
      "scala.slick.driver.MySQLDriver",
      "com.mysql.jdbc.Driver",
      "jdbc:mysql://localhost:3306/lazystore",
      "src/main/scala/",
      "example.persistent.Tables",
      "appdev",
      "appdev78"
    ))
  }
}
