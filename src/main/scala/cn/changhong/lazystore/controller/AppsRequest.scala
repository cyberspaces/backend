package cn.changhong.lazystore.controller

import cn.changhong.web.init.GlobalConfigFactory
import cn.changhong.web.util.{RestResponseInlineCode, RestException, RestRequest}

/**
 * Created by yangguo on 15-1-22.
 */
case class AppsRequest(var condition:Option[String],var start:Int,var max:Int,columns:Option[String]=None)
object AppsRequest {

  private[this] val request_key_condition = "name"
  private[this] val request_key_start = "s"
  private[this] val request_key_max = "m"
  private[this] val request_key_c = "c"

  def apply(request: RestRequest): AppsRequest = {
    var temp = request.urlParams.all.get(request_key_start)
    val start =
      if (temp != null && temp.size() > 0) {
        try {
          temp.get(0).toInt
        } catch {
          case ex => throw new RestException(RestResponseInlineCode.invalid_request_parameters, s"传入参数s=${temp.get(0)}不为数值类型")
        }
      } else Integer.MAX_VALUE
    temp = request.urlParams.all.get(request_key_max)
    val max =
      if (temp != null && temp.size() > 0)
        try {
          temp.get(0).toInt
        } catch {
          case ex => throw new RestException(RestResponseInlineCode.invalid_request_parameters, s"传入参数m=${temp.get(0)}不为数值类型")
        }
      else GlobalConfigFactory.default_apps_count
    temp = request.urlParams.all.get(request_key_condition)
    val condition =
      if (temp != null && temp.size() > 0) Some(temp.get(0))
      else None
    val columns = {
      temp = request.urlParams.all.get(request_key_c)
      if (temp != null && temp.size() > 0) Some(temp.get(0))
      else None
    }
    AppsRequest(condition, start, max, columns)
  }
}