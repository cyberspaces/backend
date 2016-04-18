package backend.lazystore.persistent.dao

import java.util.Date
import java.util.concurrent.atomic.AtomicLong

import backend.lazystore.util.KeyGenerator
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.{RestRespCode, RestException, Parser, RestRequest}
import scala.slick.driver.MySQLDriver.simple._
import backend.lazystore.persistent.T.Tables._

/**
 *  15-1-23.
 */
case class DeviceAppsStat(deviceId:Long,stats:Array[UAppstatsRow],statsType:String)
case class DeviceApps(uDeviceId:Long,uApps:Array[UAppsRow])

object ClientDeviceDao {
  val generator=new AtomicLong(System.currentTimeMillis())
  def addClientDevice(request: RestRequest) = {
    val device = Parser.UDeviceParser(Parser.ChannelBufferToString(request.underlying.getContent))
    device.registerdate = new Date().getTime
    device.id = generator.addAndGet(1)
    device.uuid = KeyGenerator.createUUID
    device.name="小武"+device.phone
    device.isbind = 0
    val phoneNumber={
      if(device.phone==null||device.phone.trim.length<8||device.phone=="12345678910") System.currentTimeMillis()
      else device.phone
    }
    val id= try{
        SqlProvider.exec2(s"select id from u_device where phone='${SqlProvider.transferRequestParams(phoneNumber+"")}'") match {
          case item :: list => item
          case s => ""
        }
    }catch{
      case ex:Throwable=> ""
    }
    if(id.trim=="") {
      try {
        SlickDBPoolManager.DBPool.withTransaction { implicit session =>
          (UDevice returning UDevice.map(_.id)).insert(device)
        }
      } catch {
        case ex: Exception => throw new RestException(RestRespCode.db_executor_error, s"db executor error,${ex.getMessage}")
      }
    }else id
  }
  //上传客服端设备信息
  def addClientDeviceApps(uapps:Array[UAppsRow])={
    try{
      var errors:List[(String,String)]=List()
      var successed:List[(String,Long)]=List()
      SlickDBPoolManager.DBPool.withTransaction{implicit session=>
        uapps.foreach{app=>
          try{
            successed=(app.title->(UApps returning UApps.map(_.id)).insert(app))::successed
          } catch{
            case ex:Throwable=> errors=(app.title->ex.getMessage)::errors
          }
        }
      }
      Map("success"->successed,"errors"->errors)
    }catch {
      case ex: Exception => throw new RestException(RestRespCode.db_executor_error, s"db executor error,${ex.getMessage}")
    }
  }
  def getClientDeviceVersions(uapps:List[String])={
    val condition=uapps.map{values=>"'"+SqlProvider.transferRequestParams(values)+"'"}.reduce(_+","+_)
    println("condition->"+condition)
    val sql=s"select title,versioncode,lazyapp_id,packagename,last_apppkg_id,devcode from v_lazyapp_apppkg where packagename in (${condition})"
    SqlProvider.exec(sql)
  }
  def addClientDeviceCopStats(request: RestRequest) = {
    val deviceStats = Parser.DeviceAppsStatsParser(Parser.ChannelBufferToString(request.underlying.getContent))
    val date = new Date().getTime()
    val stats = deviceStats.stats.map { deviceStat =>
      deviceStat.statsdate = date
      deviceStat.deviceId = deviceStats.deviceId
      deviceStat
    }
    try {
      SlickDBPoolManager.DBPool.withTransaction { implicit session =>
        UAppstats.insertAll(stats: _*) //.insert(stats)
      } match {
        case Some(s) => s
        case _ => 0
      }
    } catch {
      case ex: Exception => throw new RestException(RestRespCode.db_executor_error, s"db executor error,${ex.getMessage}")
    }
  }
  def addClientDeviceCopStats(request: String) = {
    val deviceStats = Parser.DeviceAppsStatsParser(request) //Parser.ChannelBufferToString(request.underlying.getContent))
    val date = new Date().getTime()
    val stats = deviceStats.stats.map { deviceStat =>
      deviceStat.statsdate = date
      deviceStat.deviceId = deviceStats.deviceId
      deviceStat
    }
    try {
      val res = SlickDBPoolManager.DBPool.withTransaction { implicit session =>
        UAppstats.insertAll(stats: _*) //.insert(stats)
      }
      res match {
        case Some(s) => s
        case _ => 0
      }
    } catch {
      case ex: Exception => throw new RestException(RestRespCode.db_executor_error, s"db executor error,${ex.getMessage}")
    }
  }

}
