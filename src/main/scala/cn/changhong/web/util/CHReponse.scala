package cn.changhong.web.util

/**
 * Created by yangguo on 14-12-9.
 */
case class RestResponseContent(code:Int,jsonObj:Any)
case class ResponseContent(code:Int,jsonObj:Any)
object ResponseContent{
  def apply(jsonObj:Any)={
    ResponseContent(RestResponseInlineCode.succeed,jsonObj)
  }
}

