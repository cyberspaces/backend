package cn.changhong.lazystore.controller

import cn.changhong.lazystore.persistent.dao.UserCommentDao
import cn.changhong.web.util.{Parser, ResponseContent, RestRequest}

/**
 * Created by yangguo on 15-1-22.
 */
object UserCommentService {
  object AddAppCommentService extends AddAppCommentService with BaseAopService
  private[this] class AddAppCommentService extends BaseService{
    override def apply(request: RestRequest): ResponseContent = {
      val comment=Parser.AppCommentParser(Parser.ChannelBufferToString(request.underlying.getContent))
      val content=UserCommentDao.createNewComment(comment)
      ResponseContent(content)
    }
  }
 object GetAppStarService extends GetAppStarService with BaseAopService
 private[this] class GetAppStarService extends BaseService{
   override def apply(request: RestRequest): ResponseContent = {
     val content=UserCommentDao.getAppCommentStatsStar(AppsRequest(request).condition.get)
     ResponseContent(content)
   }
 }
  object GetAppCommentService extends GetAppCommentService with BaseAopService
 private[this] class GetAppCommentService extends BaseService{
   override def apply(request: RestRequest): ResponseContent = {
     val content=UserCommentDao.getAppComment(AppsRequest(request))
     ResponseContent(content)
   }
 }
}
