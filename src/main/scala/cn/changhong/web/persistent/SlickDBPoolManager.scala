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
    ds.setUsername("yangguo")
    ds.setPassword("123456")
    ds.setMaxActive(20)
    ds.setMaxIdle(10)
    ds.setInitialSize(5)
    ds.setTestOnBorrow(true)
    ds.setUrl("jdbc:mysql://localhost:3306/crazycat")
    ds.setMaxWait(1)
    Database.forDataSource(ds)
  }

}
