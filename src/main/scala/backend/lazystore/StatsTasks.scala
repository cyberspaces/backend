package backend.lazystore

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.{LinkedBlockingQueue, TimeUnit, ThreadPoolExecutor}

import backend.lazystore.persistent.dao.{SlickResultMap, SlickResultInt, SlickResultString, SqlProvider}
import backend.lazystore.util.ConfigManager
import backend.lazystore.GlobalConfig
import backend.base.persistent.SlickDBPoolManager

import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
/**
 * Created by yangguo on 16/2/29.
 */
class StatsTasks {
  val processCoreNumber=Runtime.getRuntime.availableProcessors()
  val  executor=new ThreadPoolExecutor(processCoreNumber,(processCoreNumber*1.2).toInt,5,TimeUnit.SECONDS,new LinkedBlockingQueue[Runnable])//,new LinkedBlockingQueue[Runnable]())
  def executorDailyJob(startDate:Long,endDate:Long,time:Long)= {
    val sql=s"select app_id,country,count(1) as num from downloadstat where downloaddate>=$startDate and downloaddate<$endDate group by app_id,country"
    SlickDBPoolManager.DBPool.withSession{implicit session=>
      sql"#$sql".as(SlickResultMap).list.foreach{map=>
        val app_id=map.getOrElse("app_id","-1")
        val country=map.getOrElse("country","0")
        val count=map.getOrElse("num","0")
        val insertSQL=s"insert into tb_day_stats(time,app_id,downloadcount,country) values('$time','$app_id','$count','$country')"
        sql"#$insertSQL".as(SlickResultString).list
      }
    }
  }
  def executorMonthJob(startDate:Long,endDate:Long,time:Long)={
    val sql=s"select app_id,country,sum(downloadcount) as num from tb_day_stats where downloaddate>=$startDate and downloaddate<$endDate group by app_id,country"
    SlickDBPoolManager.DBPool.withSession{implicit session=>
      sql"#$sql".as(SlickResultMap).list.foreach{map=>
        val app_id=map.getOrElse("app_id","-1")
        val country=map.getOrElse("country","0")
        val count=map.getOrElse("num","0")
        val insertSQL=s"insert into tb_month_stats(time,app_id,downloadcount,country) values('$time','$app_id','$count','$country')"
        sql"#$insertSQL".as(SlickResultString).list
      }
    }
  }
}
object StatsTasks{
  private def initConfig()={
    GlobalConfig.server_ip = ConfigManager("lazystore.server.host")
    GlobalConfig.server_port = ConfigManager("lazystore.server.port").toInt//args(1).toInt
    GlobalConfig.server_name = ConfigManager("lazystore.server.name")
    GlobalConfig.redis_server_ip = ConfigManager("lazystore.cache.redis.host")
    GlobalConfig.redis_server_port = ConfigManager("lazystore.cache.redis.port").toInt
    GlobalConfig.db_server_ip = ConfigManager("lazystore.database.mysql.host")
    GlobalConfig.db_server_port = ConfigManager("lazystore.database.mysql.port").toInt
    GlobalConfig.db_server_username=ConfigManager("lazystore.database.mysql.username")
    GlobalConfig.db_server_password=ConfigManager("lazystore.database.mysql.password")
    GlobalConfig.db_server_db_name=ConfigManager("lazystore.database.mysql.db.name")
  }
  type Year=Int
  type Month=Int
  type Day=Int
  type SimpleDate=(Year,Month,Day)
  def getCurrentDate:SimpleDate={
    val date=Calendar.getInstance()
    (date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1,date.get(Calendar.DAY_OF_MONTH))
  }
  def simpleDateToTimestamp(simpleDateStr:String,format:String):Long={
    new SimpleDateFormat(format).parse(simpleDateStr).getTime
  }
  def main(args: Array[String]): Unit = {
    require(args.size>0,"require")
    ConfigManager.load(args(0))
    initConfig()
    val  statsType=ConfigManager("lazystore.stats.type")
    statsType match {
      case "month"=>
        val (year,month,_)=getCurrentDate
        val format="yyyy-MM"
        val startTime=simpleDateToTimestamp(year+"-"+(month-1),format)
        val endTime=simpleDateToTimestamp(year+"-"+month,format)
        val time=endTime
        new StatsTasks().executorMonthJob(startTime,endTime,time)
      case "day"=>
        val (year,month,day)=getCurrentDate
        val format="yyyy-MM-dd"
        val startTime=simpleDateToTimestamp(year+"-"+month+"-"+(day-1),format)
        val endTime=simpleDateToTimestamp(year+"-"+month+"-"+day,format)
        val time=endTime
        new StatsTasks().executorMonthJob(startTime,endTime,time)
    }
  }
}
