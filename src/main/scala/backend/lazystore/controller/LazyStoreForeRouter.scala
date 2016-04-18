package backend.lazystore.controller

import backend.lazystore.util.{Util, LazyStoreForeRouterType, LazyStoreRequestType}
import backend.base.persistent.{RedisDataHandler, RedisPoolManager}
import backend.base.router.RestAction
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future

import backend.base.util._
import org.jboss.netty.handler.codec.http.HttpMethod

/**
  * 判断是否存在爬虫->异常捕捉->设置日志->设置超时->前端路由器
  *
  * 前端路由器
  *
  */
object LazyStoreForeRouter extends Service[Request,Response]{
 val futurePool=ExecutorProvider.futurePool
  override def apply(request: Request): Future[Response] = {
    futurePool {
      val restRequest = RestRequest(request)
      val routers = RouterController.filterRouter(restRequest)
      //  [{"_1":{"_1":{"name":"GET"},"_2":"^/lazystore/v1/apps\\.(\\w+)"},"_2":{}}]
      val startTime=System.currentTimeMillis()
      val content = if (routers.isEmpty) {
        throw new RestException(RestRespCode.no_such_method, s"[${request.getUri()}] No Such Method Find!")
      } else {
        var _content:String=null

        println("restRequest.regex: " + Parser.ObjectToJsonString(restRequest.regex) )

        if(restRequest.regex.matches(RouterController.r_apps_page_search_url) && restRequest.method.equals(HttpMethod.GET)){

          println("restRequest.regex: matches " + Parser.ObjectToJsonString(RouterController.r_apps_page_search_url) )
          println("restRequest.underlying.getUri: " + Parser.ObjectToJsonString(restRequest.underlying.getUri) )

          _content=RedisDataHandler.getValue(restRequest.underlying.getUri)

          println("_content: \r\n" + Parser.ObjectToJsonString(_content) )
        }
        if(_content==null) {
          val router = routers.last
          restRequest.regex = router._1._2
          _content=Parser.ObjectToJsonString(router._2(restRequest))

          if(restRequest.regex.matches(RouterController.r_apps_page_search_url) && restRequest.method.equals(HttpMethod.GET)) {
            RedisDataHandler.setValue(restRequest.underlying.getUri, _content)
          }
        }
        _content
      }
      val response = Response()
      val expired=System.currentTimeMillis()-startTime
      response.headers().set("expired",expired)
      response.headers().set("server","backend")
      response.setContent(Parser.StringToChannelBuffer(content))
      response
    }
  }
}