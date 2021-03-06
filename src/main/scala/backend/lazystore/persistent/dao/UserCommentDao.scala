package backend.lazystore.persistent.dao

import java.util.Date
import backend.lazystore.persistent.T.Tables.UAppcommentsRow
import backend.lazystore.persistent.T.Tables.UAppcomments
import backend.lazystore.service.AppsRequest
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.{RestRespCode,  RestException}

import scala.collection.mutable
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.driver.MySQLDriver.simple._
import SqlProvider._
/**
 *  15-1-22.
 */
object UserCommentDao {
  private[this] val t_comment="app_comm"
  private[this] val t_device="app_device"
  private[this] val c_comment_device_id="device_id"
  private[this] val c_device_id="device_id"
  private[this] val c_comment_app_id="app_id"
  private[this] val c_comment_id="comment_id"
  private[this] val c_comment_app_star="app_star"

  private[this] val T_UAPPCOMMENTS="u_appcomments"
  private[this] val c_uappcomments_star="star"
  private[this] val c_uappcomments_apppkg_id="apppkg_id"
  private[this] val c_uappcomments_commentDate="commentDate"



  def createNewComment(comment:UAppcommentsRow)={
    comment.id= -1
    comment.commentdate=new Date().getTime
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      try {
        (UAppcomments returning UAppcomments.map(_.id)).insert(comment)
      }catch{
        case ex:Throwable=>throw new RestException(RestRespCode.db_insert_error,s"插入数据失败,ex=${ex.getMessage}")
      }
    }
  }
  def getAppCommentStatsStar(appid:String)= {
    try {
      val sql = s"select $c_uappcomments_star from $T_UAPPCOMMENTS where $c_uappcomments_apppkg_id=${SqlProvider.transferRequestParams(appid)}"
      val stars = SlickDBPoolManager.DBPool.withTransaction { implicit session =>
        sql"#$sql".as(SlickResultInt).list
      }
      val seq = mutable.Seq(0, 0, 0, 0, 0)
      stars.foreach { s =>
        try {
          seq(s) += 1
        }
        catch {
          case ex:Throwable =>
        }
      }
      seq.mkString(",")
    } catch {
      case ex:Throwable => RestException(RestRespCode.db_executor_error, s"db executor error,${ex.getMessage}")
    }
  }
  def getAppComment(request:AppsRequest)={
    val columns=request.columns match{
      case Some(s)=>s"${SqlProvider.transferRequestParams(s)},$c_uappcomments_commentDate as sid"
      case None=>s"*,$c_uappcomments_commentDate as sid"
    }
    if(request.start <=0) request.start=0
    val apkid=request.condition match{
      case Some(id)=>id
      case None=>throw new RestException(RestRespCode.invalid_request_parameters,"无效的id")
    }
//    val sql=s"select $columns from $T_UAPPCOMMENTS where $c_uappcomments_apppkg_id=$apkid and $c_uappcomments_commentDate < ${request.start} order by $c_uappcomments_commentDate desc limit ${request.max}"
    val sql=s"select $columns from $T_UAPPCOMMENTS where $c_uappcomments_apppkg_id=${SqlProvider.transferRequestParams(apkid)} order by commentdate desc limit ${request.start*request.max},${request.max}"
    exec(sql)
  }

}
