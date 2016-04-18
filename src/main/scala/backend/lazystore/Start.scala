package backend.lazystore

import java.net.InetSocketAddress
import java.util.UUID
import java.util.concurrent.TimeUnit

import backend.lazystore.controller.LazyStoreForeRouter
import backend.lazystore.util.ConfigManager
import backend.base.router.{TimeoutFilterService, SpiderActionInspectorFilterService, ExceptionFilterService, AccessLogFilterService}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import com.twitter.util.Duration



import scala.concurrent.future

/**
 * Created by Administrator on 2015/2/2.
 */
object Start {
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
  def main(args:Array[String]): Unit ={
    if(args.size>0){
      ConfigManager.load(args(0))
    }
    initConfig()
    ConfigManager.print();
    val service = AccessLogFilterService andThen
      ExceptionFilterService andThen
      SpiderActionInspectorFilterService andThen
      TimeoutFilterService andThen
      LazyStoreForeRouter
    ServerBuilder()
      .codec(RichHttp[Request](Http()))
      .readTimeout(Duration(15,TimeUnit.SECONDS))
      .requestTimeout(Duration(15,TimeUnit.SECONDS))
      .bindTo(new InetSocketAddress(GlobalConfig.server_port))
      .name(GlobalConfig.server_name)
      .build(service)
  }
}