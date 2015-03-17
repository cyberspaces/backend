import java.io.ByteArrayOutputStream
import java.net.URL
import java.util
import java.util.{UUID, Date}

import cn.changhong.lazystore.persistent.T.Tables._
import cn.changhong.lazystore.persistent.dao.{DeviceApps, Appsdao, ClientDeviceDao, DeviceAppsStat}
import cn.changhong.lazystore.service.AppsRequest
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{Parser, ExecutorProvider}
import com.twitter.util.{Await, Future}


import sun.net.www.protocol.http.HttpURLConnection

/**
 * Created by yangguo on 15-2-4.
 */
case class AppInfo(app:String,tags:String)
object TestParser {
  def main(args:Array[String]): Unit = {
    //    val stats=(1 to 10).map{index=>
    //      UAppstatsRow("packagename"+index,new Date().getTime,index,index,index,index,index)
    //    }.toArray
    //    val deviceStats=DeviceAppsStat(1,stats ,new Date().getTime.toString)
    //    val jsonString=Parser.ObjectToJsonString(deviceStats)
    //    println(jsonString)
    //    val obj=Parser.DeviceAppsStatsParser(jsonString)
    //    println(obj.deviceId+","+obj.statsType)
    //    obj.stats.foreach{s=>println(s.battery+","+s.deviceId)}
    //    val res=ClientDeviceDao.addClientDeviceCopStats(jsonString)
    //    println(res)


    //    val url = new URL("http://10.9.52.22:10000")
    //      val conn = url.openConnection().asInstanceOf[HttpURLConnection]
    //      conn.setConnectTimeout(5000)
    //      conn.setReadTimeout(5000)
    //          conn.setRequestMethod("GET")
    //
    //    (1 to 2).foreach {index=>
    //      if (conn.getResponseCode == 200) {
    //        val inStream = conn.getInputStream
    //        val byteBuffer = new ByteArrayOutputStream
    //        val buffer = new Array[Byte](1024)
    //        Iterator.continually(inStream.read(buffer, 0, 1024)).takeWhile(_ != -1) foreach (byteBuffer.write(buffer, 0, _))
    //        println(new String(byteBuffer.toByteArray))
    //      }
    //      println(conn.getResponseCode)
    //      Thread.sleep(4000)
    //    }
    //  }
//     testGetAppInfo("2")
//    val jsonStr="{\"star\":5,\"apppkgId\":123456789,\"comment\":\"Very Good\",\"deviceId\":123456789,\"liked\":1,\"id\":-1,\"commentdate\":-1}"
//    val comment=Parser.UAppcommentsParser(jsonStr)
//    println(comment.apppkgId)
//      case class UAppsRow(var id: Long, packagename: String, title: String, versioncode: String, var uDeviceId: Long, var updatetime: Long)

    val obj=DeviceApps(System.currentTimeMillis(),Array(UAppsRow(-1,"PACJAGENAME","TITLE","VERSIONCODE",System.currentTimeMillis(),System.currentTimeMillis())))
    println(Parser.ObjectToJsonString(obj))
  }
  def testGetAppInfo(appId:String): Unit ={
    val res=Seq( ExecutorProvider.futurePool {
      Appsdao.getAppTags(appId)
    },ExecutorProvider.futurePool{
      Appsdao.getAppInfo(appId,AppsRequest(None,0,0))
    })
    val res1=Future.collect(res).map{seq=>
      val list=seq.map(Parser.ObjectToJsonString(_))
      println("JobOk")
      AppInfo(list(1),list(0))
    }
    println(Parser.ObjectToJsonString(Await.result(res1)))
//    println("task submit ok!")
//    res1.onSuccess(appInfo=>println(Parser.ObjectToJsonString(appInfo)))onFailure{case ex:Throwable=>ex.printStackTrace()}
  }
  def concurrentTask(): Unit ={
    val futurePool=ExecutorProvider.futurePool
    //并发获取
    val start=new Date().getTime
    val f1=futurePool {
        Thread.sleep(10000)
        1
    }
    val f2=futurePool{
      futurePool {
        Thread.sleep(10000)
        2
      } onSuccess{rep=>rep+rep} onFailure{case ex:Throwable=> -1}

    }
    val f3 = f2.flatMap(t=>t)

    Future.collect(Seq(f1,f3)).map(list=>list.sum) onSuccess{res=>
      println(s"result=$res,timed=${new Date().getTime-start}")
    }

  }

}
