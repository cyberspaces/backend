package cn.changhong.lazystore.persistent.dao

import cn.changhong.lazystore.controller.AppsService.AppsRequest
import cn.changhong.lazystore.persistent.Tables.Tables._
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode, RestException}

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by yangguo on 15-1-21.
 */
object Appsdao {
  private[this] val t_app_info = "app_info"
  private[this] val c_app_id = "app_ver_id"
  private[this] val t_top_hot = "app_top_hot"
  private[this] val t_top_sale = "app_total_sale"
  private[this] val t_speity_sort = "app_recommend_sort"
  private[this] val c_tag_name="app_label"
  private[this] val c_app_update_time="app_update"
  //  private[this] val t_app

  import scala.slick.jdbc.{StaticQuery => Q}
  import Q.interpolation

  def searchSpeityApps(request: AppsRequest) = defaultSearchApps(request, t_speity_sort)

  def searchTopSaleApps(request: AppsRequest) = defaultSearchApps(request, t_top_sale)

  def searchTopHotApps(request: AppsRequest) = defaultSearchApps(request, t_top_hot)

  private[this] def defaultSearchApps(request: AppsRequest, sort_table_name: String) = {
    val indexs = getSortIndexs(sort_table_name, request.start, request.max,request.condition)
    searchApps(request.columns match {
      case Some(s) => s
      case None => "*"
    }, indexs)
  }

  def getSortIndexs(tablename: String, start: Int, max: Int,tag:Option[String]=None) = {
    SlickDBPoolManager.DBPool.withTransaction { implicit session =>
      try {
        tag match {
          case Some(s) => sql"""select #$c_app_id from #$tablename where (#$c_tag_name like '%#$s%') and #$c_app_id>#$start limit #$max""".as(SlickResultString).list
          case None =>sql"""select #$c_app_id from #$tablename where #$c_app_id>#$start limit #$max""".as(SlickResultString).list
        }
      } catch {
        case ex => throw new RestException(RestResponseInlineCode.db_executor_error, ex.getMessage)
      }
    }
  }

  @Deprecated("used getSortIndexs")
  private[this] def getSpeityAppIndexs(request: AppsRequest) = {
    SlickDBPoolManager.DBPool.withTransaction { implicit session =>
      try {
        val res = for {
          recommend <- AppRecommendSort if recommend.recommendId > request.start
        } yield recommend.appVerId
        res.take(request.max).list
      } catch {
        case ex => throw new RestException(RestResponseInlineCode.db_executor_error, ex.getMessage)
      }
    }
  }

  private[this] def searchApps(columns: String, appIds: List[String]) = {
    SlickDBPoolManager.DBPool.withTransaction { implicit session =>
      try {
        sql"select #$columns from #$t_app_info where id in (#${appIds.mkString(",")})".as(SlickResultMap).list
      } catch {
        case ex => throw new RestException(RestResponseInlineCode.db_executor_error, ex.getMessage)
      }
    }

  }
  def newAddApps(request:AppsRequest)= {
    val columns=request.columns match{
      case Some(s)=>s
      case None=>"*"
    }
    SlickDBPoolManager.DBPool.withTransaction { implicit session =>
      try {
        sql"select #$columns from #$t_app_info where (#$c_tag_name like '%#${request.condition.get}%') and #$c_app_id>#${request.start} order by #$c_app_update_time desc limit #${request.max}".as(SlickResultMap).list
      } catch {
        case ex => throw new RestException(RestResponseInlineCode.db_executor_error, ex.getMessage)
      }
    }
  }
  def searchSimilarApps(request:AppsRequest)={
    //获取app类型
    val tags=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
     sql"select #$c_tag_name from #$t_app_info where #$c_app_id = '#${request.condition.get}'".as(SlickResultString).list
    }
    tags match{
      case s::list=>
      case _=>
    }

  }
}
