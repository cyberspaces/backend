package backend.lazystore.service

import backend.lazystore.persistent.dao.SqlProvider
import backend.base.router.RestAction
import backend.base.util.{Parser, ResponseContent, RestRequest}

/**
 *  15-7-8.
 */
package Stats {

import backend.lazystore.GlobalConfig
import com.twitter.util.Future
import org.slf4j.LoggerFactory

object UserEventAction extends RestAction[RestRequest,ResponseContent] {
  override def apply(request: RestRequest): ResponseContent = UserEventService(request)
}
object UserEventService extends UserEventService with BaseAopService{
  val event_of_login=1<<1
  val event_of_logout=1<<2
  val event_of_pay_app_download=1<<3
  val event_of_free_app_download=1<<4
}
class UserEventService extends BaseService {
  override def apply(request: RestRequest): ResponseContent = {
    val body = Parser.ChannelBufferToJsonStringToMap(request.underlying.getContent).map{kv=>(kv._1,SqlProvider.transferRequestParams(kv._2))}
    val device_id=body.getOrElse("device_id","-1")
    val eventType=body.getOrElse("type","0")
    val downloadDate=System.currentTimeMillis().toString
    val location=body.getOrElse("location","unknow")
    val app_id=body.getOrElse("app_id","-1")
    val sql=s"insert into downloadstat(device_id,type,downloaddate,location,app_id) values('$device_id','$eventType','$downloadDate','$location','$app_id')"
    val rep = SqlProvider.exec1(sql) match {
      case item :: list => item
      case n => "-1"
    }
    Future.value{
      if(eventType.equals(UserEventService.event_of_login)){

      } else if(eventType.equals(UserEventService.event_of_logout)){

      }else if(eventType.equals(UserEventService.event_of_free_app_download)){

      }else if(eventType.equals(UserEventService.event_of_pay_app_download)){

      }
    }
    ResponseContent(rep)
  }
}

object StatsDownloadInfoAction extends RestAction[RestRequest, ResponseContent] {
  override def apply(request: RestRequest): ResponseContent = StatsDownloadInfo(request)
}

object StatsDownloadInfo extends StatsDownloadInfo with BaseAopService

class StatsDownloadInfo extends BaseService {
  override def apply(request: RestRequest): ResponseContent = {
    val body = Parser.ChannelBufferToJsonStringToMap(request.underlying.getContent)
    val keys = body.map{key=>SqlProvider.transferRequestParams(key._1)}.reduce(_ + "," + _) + ",downloaddate"
    val values = body.map{value=>"'" + SqlProvider.transferRequestParams(value._2) + "'"}.reduce(_ + "," + _) + ",'" + System.currentTimeMillis() + "'"
    val sql = s"insert into downloadstat(${keys}) values(${values})"
    val rep = SqlProvider.exec1(sql) match {
      case item :: list => item
      case n => "-1"
    }
    ResponseContent(rep)
  }
}

object StatsErrorApkDownloadInfoAction extends RestAction[RestRequest, ResponseContent] {
  override def apply(request: RestRequest): ResponseContent = StatsErrorApkDownloadInfo(request)
}

object StatsErrorApkDownloadInfo extends StatsErrorApkDownloadInfo with BaseAopService

class StatsErrorApkDownloadInfo extends BaseService {
  lazy val statsLog=LoggerFactory.getLogger(GlobalConfig.global_log_request_stats_error_name)
  override def apply(request: RestRequest): ResponseContent = {
    val body = Parser.ChannelBufferToString(request.underlying.getContent)
    statsLog.info(body)
    ResponseContent("1")
  }
}

}