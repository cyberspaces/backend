package cn.changhong.lazystore.persistent.dao

import cn.changhong.lazystore.controller.AppsRequest
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode, RestException}

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by yangguo on 15-1-21.
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
  private[this] val V_LAZYAPP_APPTAGS="v_lazyapp_apptags"
  private[this] val c_lazystore_speitysort_index="speitysort"
  private[this] val c_lazystore_topsort_index="topsort"
  private[this] val c_lazystore_hotsort_index="hotsort"
  private[this] val c_apptags_appcategories_name="appcategories_name"
  private[this] val c_v_lazyapp_apptags_id="id"
  private[this] val c_t_lazyapp_id="id"

  private[this] val c_lazyapp_title="title"

  private[this] val c_apptags_weight="weight"



  def searchSpeityApps(request: AppsRequest) ={
    val columns=request.columns match {
      case Some(s) => s"$s,$c_lazystore_speitysort_index as sid"
      case None => s"*,$c_lazystore_speitysort_index as sid"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%$s%'"
      case None=> "1=1"
    }
    val sql=request.tag match{
      case Some(s)=>s"select $columns from $V_LAZYAPP_APPTAGS where $condition and $c_apptags_appcategories_name = $s and $c_lazystore_speitysort_index > ${request.start} order by $c_apptags_weight desc limit ${request.max}"
      case None=>s"select $columns from $T_LAZYAPP where $condition and $c_lazystore_speitysort_index > ${request.start} limit ${request.max}"
    }
    exec(sql)
  }

  def searchTopSaleApps(request: AppsRequest) = {
    val columns=request.columns match {
      case Some(s) => s"$s,$c_lazystore_topsort_index as sid"
      case None => s"*,$c_lazystore_topsort_index as sid"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%$s%'"
      case None=> "1=1"
    }
    val sql=request.tag match{
      case Some(s)=>s"select $columns from $V_LAZYAPP_APPTAGS where $condition and $c_apptags_appcategories_name = $s and $c_lazystore_topsort_index > ${request.start} order by $c_apptags_weight desc limit ${request.max}}"
      case None=>s"select $columns from $T_LAZYAPP where $condition and $c_lazystore_topsort_index > ${request.start} limit ${request.max}"
    }
    exec(sql)
  }

  def searchTopHotApps(request: AppsRequest) ={
    val columns=request.columns match {
      case Some(s) => s"$s,$c_lazystore_topsort_index as sid"
      case None => s"*,$c_lazystore_topsort_index as sid"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%$s%'"
      case None=> "1=1"
    }
    val sql=request.tag match{
      case Some(s)=>s"select $columns from $V_LAZYAPP_APPTAGS where $condition and $c_apptags_appcategories_name = $s and $c_lazystore_hotsort_index > ${request.start} order by $c_apptags_weight desc limit ${request.max}}"
      case None=>s"select $columns from $T_LAZYAPP where $condition and $c_lazystore_hotsort_index > ${request.start} limit ${request.max}"
    }
    exec(sql)
  }


  def newAddApps(request:AppsRequest)= {
    val columns=request.columns match{
      case Some(s)=>s"$s,$c_lazystore_speitysort_index as sid"
      case None=>s"*,$c_lazystore_speitysort_index as sid"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%$s%'"
      case None=> "1=1"
    }
    val sql=request.tag match{
      case Some(s)=>s"select $columns from $V_LAZYAPP_APPTAGS where $condition and $c_apptags_appcategories_name = $s and $c_lazystore_speitysort_index > ${request.start} order by $c_apptags_weight limit ${request.max}"
      case None=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"tag 不能为空")
    }
    exec(sql)
  }
  def searchSimilarApps(request:AppsRequest)={
    val columns=request.columns match {
      case Some(s) => s"$s,$c_lazystore_speitysort_index as sid"
      case None => s"*,$c_lazystore_speitysort_index as sid"
    }
    val condition=request.condition match{
      case Some(s)=>s"$c_lazyapp_title like '%$s%'"
      case None=> "1=1"
    }
    val sql=s"select $columns from $V_LAZYAPP_APPTAGS where $c_apptags_appcategories_name in (select $c_apptags_appcategories_name from $V_LAZYAPP_APPTAGS where $condition) and $c_lazystore_speitysort_index > ${request.start} order by $c_apptags_weight desc limit ${request.max}"
    exec(sql)
  }
  def conditionSearchApps(request:AppsRequest)={
    request.condition match{
      case Some(s)=>
        val columns=request.columns match{
          case Some(c)=>s"$c,$c_lazystore_speitysort_index as sid"
          case None=>s"*,$c_lazystore_speitysort_index as sid"
        }
        val where=s"$c_lazyapp_title like '%$s%' or $c_apptags_appcategories_name like '%$s%'"
        val sql=s"select $columns from $V_LAZYAPP_APPTAGS where $where and $c_lazystore_speitysort_index > ${request.start} limit ${request.max}"
        exec(sql)
      case None=> throw new RestException(RestResponseInlineCode.invalid_request_parameters,"请输入需要查询的App")
    }
  }
}
