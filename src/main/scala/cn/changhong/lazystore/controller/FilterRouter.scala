package cn.changhong.lazystore.controller

import cn.changhong.lazystore.util.{LazyStoreForeRouterType, LazyStoreRequestType}
import cn.changhong.web.router.RestAction
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future

import cn.changhong.web.util._
/**
 * Created by yangguo on 15-1-19.
 */
object LazyStoreForeRouter extends Service[Request,Response]{
 val futurePool=ExecutorProvider.futurePool
  override def apply(request: Request): Future[Response] = {
    val restRequest=RestRequest(request)
    futurePool{
      restRequest.path(2) match{
        case LazyStoreForeRouterType.apps=>null
        case LazyStoreForeRouterType.topics=>null
        case LazyStoreForeRouterType.categorys=>null
        case LazyStoreForeRouterType.comment=>null
        case LazyStoreForeRouterType.device=>null
        case s:String=>null
        case _=>null
      }
    }
  }
}
object ForeAppsAction extends RestAction[RestRequest,Response]{


  override def apply(request: RestRequest): Response = {
   val requestType=request.urlParams.getParam[String]("type") match{
      case s::Nil=>s
      case _=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"Type类型无效")
    }
    requestType match{
      case LazyStoreRequestType.speity=>null
      case LazyStoreRequestType.topic=>null
      case LazyStoreRequestType.top_total=>null
      case LazyStoreRequestType.top_hot=>null
      case LazyStoreRequestType.tag_speity=>null
      case LazyStoreRequestType.tag_top=>null
      case LazyStoreRequestType.tag_new=>null
      case LazyStoreRequestType.similar=>null
      case LazyStoreRequestType.search=>null
      case s=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,s"type=$s 错误")
    }


  }
}
object ForeCategorysAction extends RestAction[RestRequest,Response]{
  override def apply(request: RestRequest): Response = {
    val requestType=request.urlParams.getParam[String]("type") match{
      case s::Nil=>s
      case _=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"Type类型无效")
    }
    requestType match{
      case LazyStoreRequestType.app=>null
      case LazyStoreRequestType.game=>null
      case s=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,s"type=$s 错误")
    }
  }
}
object ForeCommentAction extends RestAction[RestRequest,Response]{
  override def apply(request: RestRequest): Response = {
    val requestType=try {
      request.urlParams.getParam[String]("type") match {
        case s :: Nil => Some(s)
        case _ => throw new RestException(RestResponseInlineCode.invalid_request_parameters, "Type类型无效")
      }
    } catch{case _=>None}
    requestType match {
      case None =>null
      case Some(s) =>
        s match {
          case LazyStoreRequestType.stats =>null
          case LazyStoreRequestType.comment =>null
          case t => throw new RestException(RestResponseInlineCode.invalid_request_parameters, s"type=$t 错误")
        }
    }
  }
}
object ForeDeviceAction extends RestAction[RestRequest,Response]{
  override def apply(request: RestRequest): Response = {
    if(request.path.size==3){
     ???
    }else if(request.path.size==4&&request.path.last.equals(LazyStoreRequestType.stats)){
      ???
    }else{
       throw new RestException(RestResponseInlineCode.invalid_request_parameters,"type类型无效")

    }
  }
}
object ForeTopicsAction extends RestAction[RestRequest,Response]{
  override def apply(request: RestRequest): Response = {
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

