package backend.lazystore.controller


import backend.lazystore.service.Promote.DailyPromoteAction
import backend.lazystore.service.Stats.{UserEventAction, StatsErrorApkDownloadInfoAction, StatsDownloadInfoAction}
import backend.base.router.RestAction
import backend.base.util.{ResponseContent, RestRequest}
import com.twitter.finagle.http.Request
import org.jboss.netty.handler.codec.http.HttpMethod

import scala.collection.mutable.Map

/**
 *  15-2-3.
 */
object RouterController {

  val r_start_url="^/lazystore/v1"

  val r_apps_page_search_url=s"$r_start_url/apps\\.(\\w+)"

  val r_topic_url=s"$r_start_url/topic"

  val r_categories_url=s"$r_start_url/category"

  val r_comment_url=s"$r_start_url/comment"

  val r_comment_star_url=s"$r_start_url/comment\\.star"

  val r_device_url=s"$r_start_url/device"

  val r_stats_url=s"$r_start_url/stats"

  val r_app_info_url=s"$r_start_url/apps/(\\w+)"

  val r_user_apps_url=s"$r_start_url/u/apps"

  val r_app_version=s"$r_start_url/app/(\\w+)/version"

  val r_app_versions=s"$r_start_url/app/versions"

  val r_app_stats_download=s"$r_start_url/stats/download"

  val r_app_user_event=s"$r_start_url/stats/event"

  val r_app_stats_error_info=s"$r_start_url/stats/e/info"

  val r_app_prometed_app_g=s"$r_start_url/promote/g"

  val routers: Map[(HttpMethod, String),RestAction[RestRequest,ResponseContent] ] = Map()

  //获取Apps
  routers+=((HttpMethod.GET->r_apps_page_search_url)->AppsQueryAction)

  //获取专题
  routers+=((HttpMethod.GET->r_topic_url)->TopicGetAction)

  //创建专题
  routers+=((HttpMethod.PUT->r_topic_url)->TopicPutAction)

  //获取app分类列表
  routers+=((HttpMethod.GET->r_categories_url)->CategoryGetAction)

  //获取app相关的用户评论
  routers+=((HttpMethod.GET->r_comment_url)->AppCommentGetAction)

  //获取用户对app的评论星级
  routers+=((HttpMethod.GET->r_comment_star_url)->AppCommentStarGetAction)

  //添加App评论
  routers+=((HttpMethod.PUT->r_comment_url)->AppCommentPutAction)

  //创建设备信息
  routers+=((HttpMethod.PUT->r_device_url)->DevicePutAction)

  //上传设备端app的统计信息
  routers+=((HttpMethod.PUT->r_stats_url)->StatsPutAction)

  //获取App详细信息
  routers+=((HttpMethod.GET->r_app_info_url)->AppGetAction)

  //上传设备端所有app
  routers+=((HttpMethod.PUT->r_user_apps_url)->DeviceAppsPutAction)

  //获取设备端最新版本信息
  routers+=((HttpMethod.POST->r_app_versions)->DeviceAppVersionsAction)

  //上传下载统计
  routers+=((HttpMethod.PUT->r_app_stats_download)->StatsDownloadInfoAction)

  //上传异常类信息
  routers+=((HttpMethod.PUT->r_app_stats_error_info)->StatsErrorApkDownloadInfoAction)

  //获取app端推广位置推广列表
  routers+=((HttpMethod.GET->r_app_prometed_app_g)->DailyPromoteAction)


  //手机用户事件信息收集
  routers+=((HttpMethod.POST->r_app_user_event)->UserEventAction)

  def filterRouter(request:RestRequest)={
    routers.filter{router=>
      if(request.method == router._1._1 && request.path.matches(router._1._2)) true
      else false
    }
  }
}
