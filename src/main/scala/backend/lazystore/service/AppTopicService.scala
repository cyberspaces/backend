package backend.lazystore.service

import backend.lazystore.persistent.dao.AppTopicDao
import backend.base.util._
import org.jboss.netty.handler.codec.http.HttpMethod

/**
 *  15-1-23.
 */
object AppTopicService extends AppTopicService with BaseAopService
class AppTopicService extends BaseService{
  override def apply(request: RestRequest): ResponseContent = {
    val content=request.method match{
      case HttpMethod.GET=>AppTopicDao.getAppTopics(request)
      case HttpMethod.POST=>null
      case HttpMethod.PUT=>AppTopicDao.addAppTopic(request)
      case HttpMethod.DELETE=>null
      case _=>throw new RestException(RestRespCode.no_such_method,"未找到此服务")
    }
    ResponseContent(content)
  }
}
