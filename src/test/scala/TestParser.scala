import java.util.{UUID, Date}

import cn.changhong.lazystore.persistent.T.Tables._
import cn.changhong.lazystore.persistent.dao.{ClientDeviceDao, DeviceAppsStat}
import cn.changhong.web.persistent.SlickDBPoolManager

import cn.changhong.web.util.{RestResponseInlineCode, RestException, RestRequest, Parser}
/**
 * Created by yangguo on 15-2-4.
 */
object TestParser {
  def main(args:Array[String]): Unit ={
    val stats=(1 to 10).map{index=>
      UAppstatsRow("packagename"+index,new Date().getTime,index,index,index,index,index)
    }.toArray
    val deviceStats=DeviceAppsStat(1,stats ,new Date().getTime.toString)
    val jsonString=Parser.ObjectToJsonString(deviceStats)
    println(jsonString)
    val obj=Parser.DeviceAppsStatsParser(jsonString)
    println(obj.deviceId+","+obj.statsType)
    obj.stats.foreach{s=>println(s.battery+","+s.deviceId)}
    val res=ClientDeviceDao.addClientDeviceCopStats(jsonString)
    println(res)
  }

}
