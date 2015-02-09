package cn.changhong.lazystore.controller

import cn.changhong.lazystore.service._
import cn.changhong.lazystore.util.LazyStoreRequestType
import cn.changhong.web.router.RestAction
import cn.changhong.web.util.{RestResponseInlineCode, RestException, ResponseContent, RestRequest}

/**
 * Created by yangguo on 15-2-4.
 */

object AppGetAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = AppGetService(request)
}
object AppsQueryAction extends RestAction[RestRequest,ResponseContent]{

  override def apply(request: RestRequest): ResponseContent = {
   val requestType=request.path.split("\\.").last
   requestType match{
      case LazyStoreRequestType.speity=>SpeityAppsService(request)
      case LazyStoreRequestType.top_total=>TotalTopAppsService(request)
      case LazyStoreRequestType.top_hot=>HotTopApppsService(request)
      case LazyStoreRequestType.tag_speity=>TagSpeityAppsService(request)
      case LazyStoreRequestType.tag_top=>TagTopAppsService(request)
      case LazyStoreRequestType.tag_new=>TagNewAppsService(request)
      case LazyStoreRequestType.similar=>AppSimilarService(request)
      case LazyStoreRequestType.search=>SearchAppService(request)
      case s=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,s"Invalid Apps Type [$s]")
    }
  }
}