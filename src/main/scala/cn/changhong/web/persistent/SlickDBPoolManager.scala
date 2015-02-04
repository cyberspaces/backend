package cn.changhong.web.persistent

import org.apache.commons.dbcp.BasicDataSource
import scala.slick.driver.MySQLDriver.simple._
/**
 * Created by yangguo on 14-12-10.
 */

object SlickDBPoolManager {
  val DBPool={
    val ds=new BasicDataSource
    ds.setDriverClassName("com.mysql.jdbc.Driver")
    ds.setUsername("appdev")
    ds.setPassword("appdev")
    ds.setMaxActive(20)
    ds.setMaxIdle(10)
    ds.setInitialSize(5)
    ds.setTestOnBorrow(true)
    ds.setUrl("jdbc:mysql://10.9.52.31:13306/lazystore")
    ds.setMaxWait(1)
    Database.forDataSource(ds)
  }

}
