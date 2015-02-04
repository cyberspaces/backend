package cn.changhong.lazystore.controller

import cn.changhong.lazystore.service.ClientDeviceService
import cn.changhong.web.router.RestAction
import cn.changhong.web.util.{RestResponseInlineCode, RestException, ResponseContent, RestRequest}
import org.jboss.netty.handler.codec.http.HttpMethod

/**
 * Created by yangguo on 15-2-4.
 */

/**
 * 设备相关
 */
object DevicePutAction extends RestAction[RestRequest,ResponseContent] {
  override def apply(request: RestRequest): ResponseContent = {
    request.method match {
      case HttpMethod.PUT => ClientDeviceService.AddClientDeviceService(request)
      case _ => throw new RestException(RestResponseInlineCode.no_such_method, "no such method find!")
    }
  }
}