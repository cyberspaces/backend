package backend.base.persistent

import backend.lazystore.GlobalConfig
import org.apache.commons.dbcp.BasicDataSource
import scala.slick.driver.MySQLDriver.simple._

/**
 *  14-12-10.
 */
object SlickDBPoolManager {
  val DBPool={
    val ds=new BasicDataSource
    ds.setDriverClassName("com.mysql.jdbc.Driver")
    ds.setUsername(GlobalConfig.db_server_username)
    ds.setPassword(GlobalConfig.db_server_password)//正式appdev78
    ds.setMaxActive(20)
    ds.setMaxIdle(10)
    ds.setInitialSize(5)
    ds.setTestOnBorrow(false)
    ds.setTestWhileIdle(true)
    ds.setMaxWait(10000) //ms
    ds.setValidationQuery("select 1")
    ds.setTimeBetweenEvictionRunsMillis(300000) //ms
    ds.setMinEvictableIdleTimeMillis(300000) //ms
    ds.setRemoveAbandoned(true)
    ds.setRemoveAbandonedTimeout(300) //300s
    ds.setLogAbandoned(true)

    ds.setUrl(s"jdbc:mysql://${GlobalConfig.db_server_ip}:${GlobalConfig.db_server_port}/${GlobalConfig.db_server_db_name}?characterEncoding=UTF-8&autoReconnect=true")

    Database.forDataSource(ds)
  }

}
