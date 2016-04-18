package backend.lazystore.persistent.dao

import backend.lazystore.persistent.T.Tables.{Lazyadmin,LazyadminRow}
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.{RestRespCode, RestException}
import scala.slick.driver.MySQLDriver.simple._


/**
 *  15-4-13.
 */
object AdminAcountDao {
  def insert(admin:LazyadminRow) ={
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      try {
        (Lazyadmin returning Lazyadmin.map(_.id)).insert(admin)
      }catch{
        case ex:Exception=>throw new RestException(RestRespCode.db_executor_error,s"db executor error,${ex.getMessage}")
      }
    }
  }
  def selectAdmin(name:String,passwd:String)={
    val sql=s"select name,role,id,email from lazyadmin where name='${SqlProvider.transferRequestParams(name)}' and passwd='${SqlProvider.transferRequestParams(passwd)}'"
    SqlProvider.exec(sql)
  }
  def selectAll()={
    val sql=s"select name,role,id,mail,status from lazyadmin"
    SqlProvider.exec(sql)
  }
}
