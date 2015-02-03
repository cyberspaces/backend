package cn.changhong.lazystore.controller

import cn.changhong.lazystore.util.{Util, LazyStoreForeRouterType, LazyStoreRequestType}
import cn.changhong.web.router.RestAction
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future

import cn.changhong.web.util._
import org.jboss.netty.handler.codec.http.HttpMethod

/**
 * Created by yangguo on 15-1-19.
 */
object LazyStoreForeRouter extends Service[Request,Response]{
 val futurePool=ExecutorProvider.futurePool
  override def apply(request: Request): Future[Response] = {
    val restRequest=RestRequest(request)
    futurePool{
      if(restRequest.path!=null || restRequest.path.length < 2) throw new RestException(RestResponseInlineCode.no_such_method,"no such method find!")
      val content=restRequest.path(2) match{
        case LazyStoreForeRouterType.apps=>ForeAppsAction(restRequest)
        case LazyStoreForeRouterType.topics=>ForeTopicsAction(restRequest)
        case LazyStoreForeRouterType.categorys=>ForeCategorysAction(restRequest)
        case LazyStoreForeRouterType.comment=>ForeCommentAction(restRequest)

        case LazyStoreForeRouterType.device=>ForeDeviceAction(restRequest)
        case _=>throw new RestException(RestResponseInlineCode.no_such_method,"no such method find!")
      }
      val response=Response()
      response.setContent(Parser.ObjectToJsonStringToChannelBuffer(content))
      response
    }
  }
}
object ForeAppsAction extends RestAction[RestRequest,ResponseContent]{
  import AppsService._

  override def apply(request: RestRequest): ResponseContent = {
   val requestType=request.urlParams.getParam[String](Util.request_key_app_type) match{
      case s::Nil=>s
      case _=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"Needs Apps Search Type")
    }
   requestType match{
      case LazyStoreRequestType.speity=>SpeityAppsService(request)
      case LazyStoreRequestType.topic=>AppTopicService(request)//??
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
object ForeCategorysAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = CategoryService.GetCategoryService(request)
}

/**
 * 统计信息
 */
object ForeStatsAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = {
    request.method match{
      case HttpMethod.PUT=>ClientDeviceService.AddClientDeviceCopStats(request)
      case _=>throw new RestException(RestResponseInlineCode.no_such_method,"no such method find!")
    }
  }
}

/**
 * 评论相关
 */
object ForeCommentAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = {
    val requestType=try {
      request.urlParams.getParam[String](Util.request_key_app_type) match {
        case s :: Nil => Some(s)
        case _ => throw new RestException(RestResponseInlineCode.invalid_request_parameters, "Needs Comment")
      }
    } catch{case _=>None}
    requestType match {
      case None =>
        if (request.method.equals(HttpMethod.PUT)) {
          UserCommentService.AddAppCommentService(request)
        } else throw new RestException(RestResponseInlineCode.no_such_method, "此方法不存在")
      case Some(s) =>
        s match {
          case LazyStoreRequestType.star => UserCommentService.GetAppStarService(request)
          case LazyStoreRequestType.comment => UserCommentService.GetAppCommentService(request)
          case t => throw new RestException(RestResponseInlineCode.invalid_request_parameters, s"type=$t 错误")
        }
    }
  }
}

/**
 * 设备相关
 */
object ForeDeviceAction extends RestAction[RestRequest,ResponseContent] {
  override def apply(request: RestRequest): ResponseContent = {
    request.method match {
      case HttpMethod.PUT => ClientDeviceService.AddClientDeviceService(request)
      case _ => throw new RestException(RestResponseInlineCode.no_such_method, "no such method find!")
    }
  }
}

/**
 * 专题相关
 */
object ForeTopicsAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = {
    AppTopicService(request)
  }
}

