package backend.lazystore

import java.net.InetSocketAddress
import java.util.UUID
import java.util.concurrent.TimeUnit

import backend.lazystore.controller.LazyStoreForeRouter
import backend.lazystore.util.ConfigManager
import backend.base.init.GlobalConfigFactory
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
    GlobalConfigFactory.server_ip = ConfigManager("lazystore.server.host")
    GlobalConfigFactory.server_port = ConfigManager("lazystore.server.port").toInt//args(1).toInt
    GlobalConfigFactory.server_name = ConfigManager("lazystore.server.name")
    GlobalConfigFactory.redis_server_ip = ConfigManager("lazystore.cache.redis.host")
    GlobalConfigFactory.redis_server_port = ConfigManager("lazystore.cache.redis.port").toInt
    GlobalConfigFactory.db_server_ip = ConfigManager("lazystore.database.mysql.host")
    GlobalConfigFactory.db_server_port = ConfigManager("lazystore.database.mysql.port").toInt
    GlobalConfigFactory.db_server_username=ConfigManager("lazystore.database.mysql.username")
    GlobalConfigFactory.db_server_password=ConfigManager("lazystore.database.mysql.password")
    GlobalConfigFactory.db_server_db_name=ConfigManager("lazystore.database.mysql.db.name")
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
      .bindTo(new InetSocketAddress(GlobalConfigFactory.server_port))
      .name(GlobalConfigFactory.server_name)
      .build(service)
  }
}