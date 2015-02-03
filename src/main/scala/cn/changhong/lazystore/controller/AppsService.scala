package cn.changhong.lazystore.controller

import cn.changhong.lazystore.persistent.dao.Appsdao
import cn.changhong.web.init.GlobalConfigFactory
import cn.changhong.web.util.{ResponseContent, RestResponseInlineCode, RestException, RestRequest}
/**
   * Created by yangguo on 15-1-20.
*/
object AppsService {
  /**
   * 返回推荐首页APP
   */
  object SpeityAppsService extends SpeityAppsService with BaseAopService
  private[controller] class SpeityAppsService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val content = Appsdao.searchSpeityApps(AppsRequest(request))
      ResponseContent(content)
    }
  }

  /**
   * 查找当前主题中的APP
   */
  object TopicAppsService extends TopicAppsService with BaseAopService

  /**
   *？？？
   */
  private[controller] class TopicAppsService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      ???
    }
  }

  /**
   * 热榜
   */
  object HotTopApppsService extends HotTopApppsService with BaseAopService

  private[controller] class HotTopApppsService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val content=Appsdao.searchTopHotApps(AppsRequest(request))
      ResponseContent(content)
    }
  }

  /**
   * 总排行
   */
  object TotalTopAppsService extends TotalTopAppsService with BaseAopService

  private[controller] class TotalTopAppsService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val content=Appsdao.searchTopSaleApps(AppsRequest(request))
      ResponseContent(content)
    }
  }

  /**
   * 根据分类查找当前分类中的推荐
   */
  object TagSpeityAppsService extends TagSpeityAppsService with BaseAopService

  private[controller] class TagSpeityAppsService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val appRequest=AppsRequest(request)
      val content=appRequest.condition match{
        case Some(s)=>Appsdao.searchSpeityApps(appRequest)
        case None=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"tag is null")
      }
      ResponseContent(content)
    }
  }

  /**
   * 根据分类查找当前分类最火的APP的服务
   */
  object TagTopAppsService extends TagTopAppsService with BaseAopService

  private[controller] class TagTopAppsService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val appRequest=AppsRequest(request)
      val content=appRequest.condition match {
        case Some(s)=>Appsdao.searchTopHotApps(appRequest)
        case None=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"tag is null");
      }
      ResponseContent(content)
    }
  }

  /**
   * 根据分类返回最新添加的APP的服务
   */
  object TagNewAppsService extends TagNewAppsService with BaseAopService

  private[controller] class TagNewAppsService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val appRequest = AppsRequest(request)
      val content = Appsdao.newAddApps(appRequest)
      ResponseContent(content)
    }
  }

  /**
   * 查找与输入APP相似的APP的服务
   */
  object AppSimilarService extends AppSimilarService with BaseAopService

  private[controller] class AppSimilarService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val content=Appsdao.searchSimilarApps(AppsRequest(request))
      ResponseContent(content)
    }
  }

  /**
   * 搜索服务
   */
  object SearchAppService extends SearchAppService with BaseAopService

  private[controller] class SearchAppService extends BaseService {
    override def apply(request: RestRequest): ResponseContent = {
      val content=Appsdao.conditionSearchApps(AppsRequest(request))
      ResponseContent(content)
    }
  }

}
