import java.net.InetSocketAddress

import cn.changhong.web.util.Parser.{StringToChannelBuffer, ObjectToJsonStringToChannelBuffer}
import com.twitter.finagle.{Service, SimpleFilter, Http}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.util.{Await, Future}
import org.jboss.netty.handler.codec.http._

//import java.net.InetSocketAddress
//import java.util.Date
//
//import cn.changhong.web.router.AuthAopAction
//import cn.changhong.web.util.{NoAuthException, Parser, RestRequest}
//import com.twitter.finagle.builder.ServerBuilder
//import com.twitter.finagle.http.{Request, RichHttp, Response, Http}
//import com.twitter.finagle.redis.util.{StringToChannelBuffer, RedisCluster}
//import com.twitter.finagle.{RedisClient, Service}
//import com.twitter.util.{Await, Future}
//import org.jboss.netty.handler.codec.http._
//import org.slf4j.LoggerFactory
//import redis.clients.jedis.{JedisPool, JedisPoolConfig, Jedis}
//
//
///**
// * Created by yangguo on 14-12-8.
// */
//trait RestAopAction{
//  def aopAction(request:RestRequest): HttpResponse
//}
//trait LogAopService extends RestAopAction{
//  val log=LoggerFactory.getLogger(getClass.getName)
//  abstract override def aopAction(request:RestRequest):HttpResponse={
//    println("start log....")
//    val res=super.aopAction(request)
//    request.logBean.et=new Date().getTime-request.logBean.st
//    log.info(createLog(request))
//    res
//  }
//  def createLog(request:RestRequest): String ={
//    Parser.ObjectToJsonString(request.logBean)
//  }
//}
//trait AuthRequestRouterProvider extends Service[RestRequest,HttpResponse] with RestAopAction{
//  override def apply(request: RestRequest): Future[HttpResponse] = {
//    Future.value{
//      aopAction(request)
//    }
//  }
//
//}
//
//trait AuthAopAction extends RestAopAction{
//  abstract override def aopAction(request:RestRequest):HttpResponse={
//    println("start auth....")
//    throw new NoAuthException(request.logBean.path,"No Permission")
//    null
//  }
//}
//class TestAction extends AuthRequestRouterProvider with RestAopAction{
//  override def aopAction(request: RestRequest): HttpResponse = {
//    println("start bus...")
//    val  res=new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
//    request.logBean.et=new Date().getTime
//    println("end bus...")
//    res
//  }
//}
//object TestAction extends TestAction with AuthAopAction with LogAopService
//import com.twitter.finagle.redis.Client
//
//object Test{
//  import scala.collection.JavaConverters._
//  def main(args:Array[String]): Unit ={
//    val service=new Service[Request, Response] {
//        override def apply(request: Request): Future[Response] = {
//          request.headers().names().asScala.foreach(key =>
//            println(key + "->" + request.headers().get(key))
//          )
//          request.getParamNames().asScala.foreach{key=>
//            println(key + "->" + request.getParam(key))
//          }
//          println(request.getHeader("Remote_Addr"))
//          println(request.getHeader("HTTP_X_FORWARDED_FOR"))
//          println(request.remoteHost+","+request.remotePort)
//          Future.value(Response())
//        }
//      }
//    ServerBuilder().codec(RichHttp[Request](Http()))
//    .bindTo(new InetSocketAddress(10000))
//    .name("server")
//    .build (service)
//    (1 to 100000) foreach{p=>
//      Thread.sleep(10)
//     val client= com.twitter.finagle.Http.newService("10.9.52.99:10001")
//      client.apply(new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/dsadsads")) onSuccess(s=>
//        println("successed"+p)) onFailure(e=>println("failed"+p))
//    }
//  }
//  def action: Unit ={
////    val testAction=new TestAction with LogAopService
////    val testAction1=new TestAction with AuthAopAction with LogAopService //需要log 需要Auth
////    TestAction(RestRequest(new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/auth")))
//  }
//  def Jedis_Test(): Unit ={
////    val jedis=new Jedis("localhost",6379)
////    println(jedis.set("redis","myredis"))
////    println(jedis.set("redis","value","XX","PX",1000))
////    println(jedis.get("redis"))
//    val config=new JedisPoolConfig()
//    config.setMaxTotal(50)
//    config.setMaxIdle(5)
//    config.setMaxWaitMillis(1000*10)
//    val pool=new JedisPool(config,"localhost",6379)
////    pool.getResource.set()
//  }
//  def Finagle_Redis(): Unit ={
//   RedisCluster.start(1)
//   val client=Client(RedisCluster.hostAddresses())
//    client.set(StringToChannelBuffer("foo"),StringToChannelBuffer("bar"))
//    val getResult=Await.result(client.get(StringToChannelBuffer("foo")))
//    getResult match{
//      case Some(n)=>println("Got Result:"+new String(n.array))
//    }
//  }
//}
object Test {
  def main(args:Array[String]): Unit ={
    client
  }
  def client: Unit ={
    val client=Http.newService("www.chphone.cn:8081")
    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/")
    request.headers().set("Client_Id","client_id")
    val response=client(request)
    response onSuccess(re=>println(new String(re.getContent.array()))) onFailure(ex=>ex.printStackTrace())
    Thread.sleep(1000)
    Await.ready(response)
  }

  def server: Unit ={
    val service=ExceptionFilterService andThen new Service[HttpRequest,HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = {
        val response=new org.jboss.netty.handler.codec.http.DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
        response.setContent(StringToChannelBuffer("proxy"))
        Future.value(response)
      }
    }
    val serviceProxy = ExceptionFilterService andThen Http.newService("localhost:" + 10011)
    println("....")
    startServer(10011,service)
    startServer(10012,serviceProxy)
    Thread.sleep(100000)

  }
  def startServer(port:Int,service:Service[HttpRequest,HttpResponse]): Unit ={
    new Thread(new Runnable {
      override def run(): Unit = {
        ServerBuilder()
          .codec(com.twitter.finagle.http.Http())
          .name("proxy")
          .bindTo(new InetSocketAddress(port))
          .build(service)
      }
    }).start()
  }
}
object ExceptionFilterService extends SimpleFilter[HttpRequest,HttpResponse]{
  override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = service(request) handle { case ex =>
    println(ex.getLocalizedMessage+"sdsadsadsads")
    new org.jboss.netty.handler.codec.http.DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
  }
}

