package backend.base.init

import java.util.Properties
import java.util.concurrent.TimeUnit

import com.twitter.util.Duration

/**
 *  14-12-10.
 */

object GlobalConfigFactory {
//  var properties:Properties=null

  var db_driver=""
  var db_username=""
  var db_password=""
  var db_url=""
  var db_maxActive=255
  var db_maxIdle=2
  var db_maxWait=1200
  var db_thread_init_size=5
  var executor_worker_max_thread_size=4

  var global_response_timeout=Duration(10,TimeUnit.SECONDS)

  var global_log_request_access_name="logInfo"
  val global_log_request_error_name="logError"
  val global_log_request_tracker_name="logTracker"
  val global_log_request_spider_name="logSpider"
  val global_log_request_stats_error_name="statsError"

  var server_ip="localhost"
  var server_port=10001
  var server_name="lazystore_10001"


  val max_valid_request_frequency=100
  val max_valid_request_expire_seconds=10
  val exceed_spider_threshold_frequency=100
  val exceed_spider_threshold_seconds=3600
  val may_spider_sleep_seconds=10


  var db_server_ip="localhost"
  var db_server_port=3306

  var db_server_db_name="lazystore"
  var db_server_username="appdev"
  var db_server_password="appdev78"

  /*分页查询默认返回的数据条数*/
  val default_apps_count=24

  val log_user_name=""


  var redis_server_ip ="localhost"
  var redis_server_port:Int=6379

//  def apply(confPath:String): Unit ={
//    val confInputStream=this.getClass.getClassLoader.getResourceAsStream("webconf.properties")
//    val p=new Properties()//(confInputStream)
//    p.load(confInputStream)
//    println("load webconf.properties")
//    this.properties = p
//  }
}
