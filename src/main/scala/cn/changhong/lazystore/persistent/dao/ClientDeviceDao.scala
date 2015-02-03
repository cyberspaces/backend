package cn.changhong.lazystore.persistent.dao

import java.util.Date

import cn.changhong.lazystore.util.KeyGenerator
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode, RestException, Parser, RestRequest}
import scala.slick.driver.MySQLDriver.simple._
import cn.changhong.lazystore.persistent.T.Tables._

/**
 * Created by yangguo on 15-1-23.
 */
object ClientDeviceDao {
  def addClientDevice(request:RestRequest) ={
    val device=Parser.UDeviceParser(Parser.ChannelBufferToString(request.underlying.getContent))
    device.registerdate=new Date().getTime
    device.id= -1
    device.uuid=KeyGenerator.createUUID
    device.isbind=0
    try {
      SlickDBPoolManager.DBPool.withTransaction { implicit session =>
        (UDevice returning UDevice.map(_.id)).insert(device)
      }
    }catch{
      case ex:Exception=>throw new RestException(RestResponseInlineCode.db_executor_error,s"db executor error,${ex.getMessage}")
    }
  }
  def addClientDeviceCopStats(request:RestRequest)={
    val stats=Parser.UAppStatsParser(Parser.ChannelBufferToString(request.underlying.getContent))
    stats.statsdate=new Date().getTime
    try{
      SlickDBPoolManager.DBPool.withTransaction{implicit session=>
        (UAppstats returning UAppstats.map(_.deviceId)).insert(stats)
      }
    }catch{
      case ex:Exception=>throw new RestException(RestResponseInlineCode.db_executor_error,s"db executor error,${ex.getMessage}")
    }
  }
}
