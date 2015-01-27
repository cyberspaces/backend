package cn.changhong.lazystore.persistent.dao

import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{Parser, RestRequest}
import cn.changhong.lazystore.persistent.Tables.Tables.{AppDevice,AppStat}
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by yangguo on 15-1-23.
 */
object ClientDeviceDao {
  def addClientDevice(request:RestRequest) ={
    val device=Parser.ClientDeviceParser(Parser.ChannelBufferToString(request.underlying.getContent))
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      (AppDevice returning AppDevice.map(_.deviceId)).insert(device)
    }
  }
  def addClientDeviceCopStats(request:RestRequest)={
    val stats=Parser.ClientDeviceCopStatsParser(Parser.ChannelBufferToString(request.underlying.getContent))
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      (AppStat returning AppStat.map(_.deviceId)).insert(stats)
    }
  }
}
