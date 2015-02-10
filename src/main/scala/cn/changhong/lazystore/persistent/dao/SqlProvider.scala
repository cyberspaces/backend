package cn.changhong.lazystore.persistent.dao

import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode, RestException}

import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation

/**
 * Created by Administrator on 2015/2/2.
 */
object SqlProvider {
  private[dao] def exec(sql:String)={
    try{
      SlickDBPoolManager.DBPool.withSession{implicit session=>
        sql"#$sql".as(SlickResultMap).list
      }
    }catch{
      case ex => throw new RestException(RestResponseInlineCode.db_executor_error, s"${ex.getMessage}")
    }

  }
}
