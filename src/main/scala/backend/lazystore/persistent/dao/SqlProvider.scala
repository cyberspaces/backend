package backend.lazystore.persistent.dao

import backend.lazystore.GlobalConfig
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.{RestException, RestRespCode}
import org.apache.commons.lang.StringEscapeUtils

import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation

/**
 * Created by Administrator on 2015/2/2.
 */
object SqlProvider {

  def exec(sql:String)={
    try{
      val start=System.currentTimeMillis()
      println(GlobalConfig.server_name +" <--sql-- "+ sql)
      val  res=SlickDBPoolManager.DBPool.withSession{implicit session=>
        sql"#$sql".as(SlickResultMap).list
      }
      println("Used Timed:"+(System.currentTimeMillis()-start))
      res
    }catch{
      case ex:Throwable =>
        ex.printStackTrace()
        throw new RestException(RestRespCode.db_executor_error, s"${ex.getMessage}")
    }
  }

  def createKeyValues(map:Map[String,String])={
    val keys=map.map{kv=>transferRequestParams(kv._1)}.reduce(_+","+_)
    val values=map.map{kv=>"'"+transferRequestParams(kv._2)+"'"}.reduce(_+","+_)
    (keys,values)
  }

  def exec1(sql:String)={
    try{
      println(sql)
      SlickDBPoolManager.DBPool.withSession{implicit session=>
        sql"#$sql".as(SlickResultInt).list
      }
    }catch{
      case ex:Throwable => throw new RestException(RestRespCode.db_executor_error, s"${ex.getMessage}")
    }
  }
  def exec(sqls:List[String])={
    SlickDBPoolManager.DBPool.withSession{implicit session=>

      sqls.foreach{sql=>
        println(sql)
        sql"#$sql".as(SlickResultString).list
      }
    }
  }
  def exec2(sql:String)={
    try{
      println(sql)
      SlickDBPoolManager.DBPool.withSession{implicit session=>
        sql"#$sql".as(SlickResultString).list
      }
    }catch{
      case ex:Throwable => throw new RestException(RestRespCode.db_executor_error, s"${ex.getMessage}")
    }
  }

  private final val unsafeSQLWord="!@|#$%^&*()\"\'\\[]{}+."
  def transferRequestParams(params:String)= {
//    params.map { ch =>
//      if (unsafeSQLWord.contains(ch)) "\\" + ch
//      else ch
//    }.mkString

    StringEscapeUtils.escapeSql(params)
  }
}
