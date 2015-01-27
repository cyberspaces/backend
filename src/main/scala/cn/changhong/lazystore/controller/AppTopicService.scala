package cn.changhong.lazystore.controller

import cn.changhong.web.util.{ResponseContent, RestRequest}
import org.jboss.netty.handler.codec.http.HttpMethod

/**
 * Created by yangguo on 15-1-23.
 */
object AppTopicService extends AppTopicService with BaseAopService
class AppTopicService extends BaseService{
  override def apply(request: RestRequest): ResponseContent = {
    val content=request.method match{
      case HttpMethod.GET=>
      case HttpMethod.POST=>
      case HttpMethod.PUT=>
      case HttpMethod.DELETE=>
      case _=>
    }
    ResponseContent(content)
  }
}
