package backend.lazystore.persistent.dao

import java.util.Date

import backend.lazystore.persistent.T.Tables.{Apppkg, ApppkgRow, Lazyapp, LazyappRow, VLazyappApppkg}
import backend.lazystore.service.AppsRequest
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.{RestException, RestRespCode}

import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import scala.slick.jdbc.JdbcBackend._
import Q.interpolation
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.slick.driver.MySQLDriver.simple._

/**
 *  15-1-21.
 */
object Appsdao {
  import SqlProvider._
  private[this] val t_app_info = "lazyapp"
  private[this] val t_top_hot = "app_top_hot"
  private[this] val t_top_sale = "app_total_sale"
  private[this] val t_speity_sort = "app_recommend_sort"

  private[this] val c_app_id = "app_ver_id"
  private[this] val c_tag_name="app_label"
  private[this] val c_app_name="app_name"
  private[this] val c_app_update_time="app_update"

  private[this] val T_LAZYAPP="lazyapp"
  private[this] val V_LAZYAPP_APPPKG="v_lazyapp_apppkg"
  private[this] val V_LAZYAPP_APPPKG_TAGS="v_lazyapp_apppkg_tags"
  private[this] val c_lazystore_speitysort_index="speitysort"
  private[this] val c_lazystore_topsort_index="topsort"
  private[this] val c_lazystore_hotsort_index="hotsort"
  private[this] val c_apptags_appcategories_name="tag"
  private[this] val c_v_lazyapp_apptags_id="id"
  private[this] val c_t_lazyapp_id="id"
  private[this] val c_lazyapp_updatedate="updateddate"

  private[this] val v_LAZYAPP_TAGS="v_lazyapp_tags"

  private[this] val c_lazyapp_title="title"

  private[this] val c_apptags_weight="weight"

  private[this] val T_APPTAGS="apptags"

  private[this] val c_lazyapp_id="lazyapp_id"


  def getAppInfo(appId:String,request:AppsRequest)={
    val columns=request.columns match{
      case Some(s)=>SqlProvider.transferRequestParams(s)
      case _=>"*"
    }
    val sql=s"select $columns from $V_LAZYAPP_APPPKG where $c_lazyapp_id = ${SqlProvider.transferRequestParams(appId)}"
    exec(sql)
  }

  def getAppTags(appId:String)={
    val sql=s"select tag,weight  from $T_APPTAGS where $c_lazyapp_id = ${SqlProvider.transferRequestParams(appId)} order by $c_apptags_weight desc"
    exec(sql)
  }

  /**
   * 查询推荐
    *
    * @param request
   * @return
   */
  def searchSpeityApps(request: AppsRequest) ={
    val columns=request.columns match {
      case Some(s) => SqlProvider.transferRequestParams(s)
      case None => throw new RestException(RestRespCode.invalid_request_parameters,"Need Columns Label")// s"DISTINCT $c_lazystore_speitysort_index as sid,*"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%${SqlProvider.transferRequestParams(s)}%'"
      case None=> "1=1"
    }
    val sql=request.tag match{
      /*使用分类查询*/
      case Some(s)=>s"select DISTINCT lazyapp_id,$columns from $V_LAZYAPP_APPPKG_TAGS where $condition and $c_apptags_appcategories_name = '${SqlProvider.transferRequestParams(s)}'  limit ${request.start*request.max},${request.max}"

//    case Some(s)=>s"select $columns from $V_LAZYAPP_APPPKG_TAGS where $condition and $c_apptags_appcategories_name = '$s' order by  $c_lazystore_speitysort_index desc, $c_apptags_weight desc limit ${request.max}"

      /*使用默认查询*/
      case None=>s"select $columns from $V_LAZYAPP_APPPKG where $condition limit ${request.start*request.max},${request.max}"
    }
    exec(sql)
  }

  def searchTopSaleApps(request: AppsRequest) = {
    val columns=request.columns match {
      case Some(s) => SqlProvider.transferRequestParams(s)
      case None =>  throw new RestException(RestRespCode.invalid_request_parameters,"Need Columns Label")//s"DISTINCT $c_lazystore_topsort_index as sid,*"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%${SqlProvider.transferRequestParams(s)}%'"
      case None=> "1=1"
    }
    val sql=request.tag match{//order by t_downloadcount desc
      case Some(s)=>s"select DISTINCT lazyapp_id,$columns from $V_LAZYAPP_APPPKG_TAGS where $condition and $c_apptags_appcategories_name = '${SqlProvider.transferRequestParams(s)}' order by t_downloadcount desc  limit ${request.start*request.max},${request.max}"
      case None=>s"select $columns from $V_LAZYAPP_APPPKG where $condition order by t_downloadcount desc limit ${request.start*request.max},${request.max}"
    }
//    println(sql);
    exec(sql)
  }

  def searchTopHotApps(request: AppsRequest) ={
    val columns=request.columns match {
      case Some(s) => SqlProvider.transferRequestParams(s)
      case None => throw new RestException(RestRespCode.invalid_request_parameters,"Need Columns Label")//s"DISTINCT $c_lazystore_topsort_index as sid,*"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%${SqlProvider.transferRequestParams(s)}%'"
      case None=> "1=1"
    }
    val sql=request.tag match{//order by installedcount desc
      case Some(s)=>s"select DISTINCT lazyapp_id,$columns from $V_LAZYAPP_APPPKG_TAGS where $condition and $c_apptags_appcategories_name = '${SqlProvider.transferRequestParams(s)}' order by t_installedcount desc limit ${request.start*request.max},${request.max}"
      case None=>s"select $columns from $V_LAZYAPP_APPPKG where $condition order by t_installedcount desc limit ${request.start*request.max},${request.max}"
    }
    exec(sql)
  }

  def searchNewApps(request:AppsRequest)= {

    try{
      val  res=SlickDBPoolManager.DBPool.withSession { implicit session =>
        // VLazyappApppkg
//        val q1 = Q.queryNA[VLazyappApppkg]("select * from v_lazyapp_apppkg  where lazyapp_id > ? order by creation desc limit 0,24 ")
//        q1.apply(10).list

        val q2 = Q.query[Int, (Long, String, String, String, Int)]("""
        select  lazyapp_id,title,icon,described,t_downloadcount from v_lazyapp_apppkg where lazyapp_id > ? order by creation desc limit 0,24
                                                                   """)
        q2.apply(10).list
        }

      res
    }catch{
      case ex:Throwable =>
        ex.printStackTrace()
        throw new RestException(RestRespCode.db_executor_error, s"${ex.getMessage}")
    }
  }

  def searchNewApps_bak(request:AppsRequest)= {
    val columns=request.columns match{
      case Some(s)=>SqlProvider.transferRequestParams(s)
      case None => throw new RestException(RestRespCode.invalid_request_parameters,"Need Columns Label")//case None=>s"DISTINCT $c_lazyapp_updatedate as sid,*"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%${SqlProvider.transferRequestParams(s)}%'"
      case None=> "1=1"
    }
    val sql=request.tag match{
      case Some(s)=>s"select DISTINCT lazyapp_id,$columns from $V_LAZYAPP_APPPKG_TAGS where $condition and $c_apptags_appcategories_name = '${SqlProvider.transferRequestParams(s)}' order by apppkg_creation desc limit ${request.start*request.max},${request.max}"
      case None=>s"select $columns from $V_LAZYAPP_APPPKG where $condition order by apppkg_creation desc limit ${request.start*request.max},${request.max}"
    }
    exec(sql)
  }

  def searchSimilarApps(request:AppsRequest)={
    val columns=request.columns match {
      case Some(s) => SqlProvider.transferRequestParams(s)
      case None => throw new RestException(RestRespCode.invalid_request_parameters,"Need Columns Label")//case None => s"*,$c_lazystore_speitysort_index as sid"
    }
    val condition=request.condition match{
      case Some(s)=>s"lazyapp_title like '${SqlProvider.transferRequestParams(s)}'"
      case None=> throw new RestException(RestRespCode.invalid_request_parameters,"Need App Name")
    }

    val withNoCondition=request.condition match{
      case Some(s) => s"title != '${SqlProvider.transferRequestParams(s)}'"
      case None=> throw new RestException(RestRespCode.invalid_request_parameters,"Need App Name")
    }
    val sql=s"select DISTINCT lazyapp_id,$columns from $V_LAZYAPP_APPPKG_TAGS where $c_apptags_appcategories_name in (select $c_apptags_appcategories_name from $T_APPTAGS where $condition) and $withNoCondition limit ${request.start*request.max},${request.max}"
    exec(sql)
  }

  def conditionSearchApps(request:AppsRequest)={
    request.condition match{
      case Some(s)=>
        val columns=request.columns match{
          case Some(c)=>SqlProvider.transferRequestParams(c)
          case None => throw new RestException(RestRespCode.invalid_request_parameters,"Need Columns Label")//case None=>s"DISTINCT $c_lazystore_speitysort_index as sid,*"
        }
        val where=s"$c_lazyapp_title like '%${SqlProvider.transferRequestParams(s)}%'"
        val sql=s"select DISTINCT lazyapp_id,$columns from $V_LAZYAPP_APPPKG where $where limit ${request.start*request.max},${request.max}"
        exec(sql)
      case None=> throw new RestException(RestRespCode.invalid_request_parameters,"请输入需要查询的App")
    }
  }

  def insertApps(lazyApps:Seq[LazyappRow]) ={
    try {
      SlickDBPoolManager.DBPool.withSession {implicit session=>
        Lazyapp.insertAll(lazyApps: _*)
      }
    }catch{
      case ex:Exception=>throw new RestException(RestRespCode.db_executor_error,s"db executor error,${ex.getMessage}")
    }
  }

  def insertAppPkgs(appPkgs:Seq[ApppkgRow])={
    try{
      SlickDBPoolManager.DBPool.withSession{implicit session=>
        Apppkg.insertAll(appPkgs:_*)
      }
    }catch{
      case ex:Exception=>throw new RestException(RestRespCode.db_executor_error,s"db executor error,${ex.getMessage}")
    }
  }

}
