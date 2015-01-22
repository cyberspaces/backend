package cn.changhong.lazystore.persistent.dao

import cn.changhong.lazystore.controller.AppsRequest
import cn.changhong.lazystore.persistent.Tables.Tables.AppCommRow
import cn.changhong.lazystore.persistent.Tables.Tables.AppComm
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode,  RestException}

import scala.collection.mutable
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.driver.MySQLDriver.simple._
/**
 * Created by yangguo on 15-1-22.
 */
object UserCommentDao {
  private[this] val t_comment="app_comm"
  private[this] val t_device="app_device"
  private[this] val c_comment_device_id="device_id"
  private[this] val c_device_id="device_id"
  private[this] val c_comment_app_id="app_id"
  private[this] val c_comment_id="comment_id"
  private[this] val c_comment_app_star="app_star"
  def createNewComment(comment:AppCommRow)={
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      try {
        (AppComm returning AppComm.map(_.commId)).insert(comment)
      }catch{
        case ex=>throw new RestException(RestResponseInlineCode.db_insert_error,s"插入数据失败,ex=${ex.getMessage}")
      }
    }
  }
  def getAppCommentStatsStar(appid:String)={
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      val sql=s"select $c_comment_app_star from $t_comment where $c_comment_app_id=$appid"
      val seq=mutable.Seq(0,0,0,0,0)
      sql"#$sql".as(SlickResultInt).list.foreach{s=>
        try{
          seq(s)+=1
        }
        catch{
          case ex=>
        }
      }
      seq.mkString(",")
    }
  }
  def getAppComment(request:AppsRequest)={
    val columns=request.columns match{
      case Some(s)=>s
      case None=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"请输入合法的可选列")
    }
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      val sql=s"select $columns from $t_comment t1,$t_device t2 where t1.$c_comment_app_id=${request.condition.get} and t1.${c_comment_id}<${request.start} and t1.$c_comment_device_id = t2.$c_device_id limit ${request.max}"
      sql"#$sql".as(SlickResultMap).list
    }
  }

}
