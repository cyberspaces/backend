package cn.changhong.web.util

/**
 * Created by yangguo on 14-12-9.
 */
case class RestResponseContent(code:Int,jsonObj:AnyRef)
case class ResponseContent(code:Int,jsonObj:AnyRef)
object ResponseContent{
  def apply(jsonObj:AnyRef):ResponseContent={
    ResponseContent(RestResponseInlineCode.succeed,jsonObj)
  }
}

