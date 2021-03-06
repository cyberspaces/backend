package backend.lazystore.service

import backend.lazystore.util.Util
import backend.lazystore.GlobalConfig
import backend.base.util.{RestException, RestRequest, RestRespCode}

/**
 *  15-1-22.
 */
case class AppsRequest(var condition:Option[String],var start:Long,var max:Int,columns:Option[String]=None,tag:Option[String]=None)
object AppsRequest {
//  private[service] def urlParamsDecoder(params:String)={
//    val res=URLDecoder.decode(params,"utf-8")
//    println(params+","+res)
//    res
//  }
  def apply(request: RestRequest): AppsRequest = {
  var temp = request.urlParams.all.get(Util.request_key_start)
  val start =
    if (temp != null && temp.size() > 0 && temp.get(0).trim.size > 0) {
      try {
        temp.get(0).toLong
      } catch {
        case ex:Throwable => throw new RestException(RestRespCode.invalid_request_parameters, s"传入参数s=${temp.get(0)}不为数值类型")
      }
//    } else Long.MaxValue
    }else 0

    temp = request.urlParams.all.get(Util.request_key_max)
    val max =
      if (temp != null && temp.size() > 0&&temp.get(0).trim.size>0)
        try {
          temp.get(0).toInt
        } catch {
          case ex:Throwable => throw new RestException(RestRespCode.invalid_request_parameters, s"传入参数m=${temp.get(0)}不为数值类型")
        }
      else GlobalConfig.default_apps_count

    temp = request.urlParams.all.get(Util.request_key_params)
    val condition =
      if (temp != null && temp.size() > 0&&temp.get(0).trim.size>0) Some(temp.get(0))
      else None
    val columns = {
      temp = request.urlParams.all.get(Util.request_key_columns)
      if (temp != null && temp.size() > 0&&temp.get(0).trim.size>0) Some(temp.get(0))
      else None
    }
    val tag={
      temp=request.urlParams.all.get(Util.request_key_tag)
      if(temp !=null && temp.size()>0&&temp.get(0).size>0) Some(temp.get(0))
      else None
    }
    AppsRequest(condition, start, max, columns,tag)
  }
}