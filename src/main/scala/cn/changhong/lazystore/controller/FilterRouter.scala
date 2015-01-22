package cn.changhong.lazystore.controller

import cn.changhong.lazystore.persistent.dao.CategoryDao
import cn.changhong.lazystore.util.{LazyStoreForeRouterType, LazyStoreRequestType}
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
      val content=restRequest.path(2) match{
        case LazyStoreForeRouterType.apps=>ForeAppsAction(restRequest)
        case LazyStoreForeRouterType.topics=>null
        case LazyStoreForeRouterType.categorys=>ForeCategorysAction(restRequest)
        case LazyStoreForeRouterType.comment=>null
        case LazyStoreForeRouterType.device=>null
        case s:String=>null
        case _=>null
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
   val requestType=request.urlParams.getParam[String]("type") match{
      case s::Nil=>s
      case _=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"Type类型无效")
    }
   requestType match{
      case LazyStoreRequestType.speity=>SpeityAppsService(request)
      case LazyStoreRequestType.topic=>null
      case LazyStoreRequestType.top_total=>TotalTopAppsService(request)
      case LazyStoreRequestType.top_hot=>HotTopApppsService(request)
      case LazyStoreRequestType.tag_speity=>TagSpeityAppsService(request)
      case LazyStoreRequestType.tag_top=>TagTopAppsService(request)
      case LazyStoreRequestType.tag_new=>TagNewAppsService(request)
      case LazyStoreRequestType.similar=>AppSimilarService(request)
      case LazyStoreRequestType.search=>SearchAppService(request)
      case s=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,s"type=$s 错误")
    }
  }
}
object ForeCategorysAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = CategoryService.GetCategoryService(request)
}
object ForeCommentAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = {
    val requestType=try {
      request.urlParams.getParam[String]("type") match {
        case s :: Nil => Some(s)
        case _ => throw new RestException(RestResponseInlineCode.invalid_request_parameters, "Type类型无效")
      }
    } catch{case _=>None}
    requestType match {
      case None =>
        if (request.method.equals(HttpMethod.PUT)) {
          UserCommentService.AddAppCommentService(request)
        } else throw new RestException(RestResponseInlineCode.no_such_method, "此方法不存在")
      case Some(s) =>
        s match {
          case LazyStoreRequestType.stats => UserCommentService.GetAppStarService(request)
          case LazyStoreRequestType.comment => UserCommentService.GetAppCommentService(request)
          case t => throw new RestException(RestResponseInlineCode.invalid_request_parameters, s"type=$t 错误")
        }
    }
  }
}
object ForeDeviceAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = {
    if(request.path.size==3){
     ???
    }else if(request.path.size==4&&request.path.last.equals(LazyStoreRequestType.stats)){
      ???
    }else{
       throw new RestException(RestResponseInlineCode.invalid_request_parameters,"type类型无效")

    }
  }
}
object ForeTopicsAction extends RestAction[RestRequest,ResponseContent]{
  override def apply(request: RestRequest): ResponseContent = {
    val requestType=request.urlParams.getParam[String]("type") match{
      case s::Nil=>s
      case _=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"Type类型无效")
    }
    requestType match{
      case LazyStoreRequestType.speity2=>null
      case LazyStoreRequestType.topic=>null
      case LazyStoreRequestType.tag=>null
      case LazyStoreRequestType.all=>null
      case LazyStoreRequestType.speity1=>null
      case s=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,s"type=$s 错误")
    }
  }
}

