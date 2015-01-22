package cn.changhong.lazystore.persistent.dao

import scala.slick.jdbc.{PositionedResult, GetResult}

/**
 * Created by yangguo on 15-1-21.
 */
object SlickResultString extends GetResult[String]{
  override def apply(pr: PositionedResult): String = {
    pr.rs.getObject(1).toString
  }
}
object SlickResultInt extends GetResult[Int]{
  override def apply(pr: PositionedResult): Int = {
    pr.rs.getObject(1).toString.toInt
  }
}
object SlickResultMap extends GetResult[Map[String,Any]]{
  override def apply(pr: PositionedResult): Map[String, Any] = {
    val cvalues=pr.rs
    val cnames=cvalues.getMetaData
    val res = (1 to pr.numColumns).map{ i=> cnames.getColumnName(i) -> cvalues.getObject(i) }.toMap
    res
  }
}
