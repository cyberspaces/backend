import cn.changhong.web.controller.auth.AccountUtil
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode, ResponseContent, RestRequest, Parser}
import net.liftweb.json.DefaultFormats

import scala.collection.mutable
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST._
import scala.slick.driver.MySQLDriver.simple._
import cn.changhong.web.persistent.Tables.Tables._
/**
 * Created by yangguo on 14-12-11.
 */
object test extends App {
  implicit  val formats=DefaultFormats
  case class Topic(topicId:Long,desc:String,t:String,imgs:String,index:Int)
  //  val map:mutable.Map[String,String]=mutable.Map()
  //  val v=map.getOrElse("s",new util.ArrayList[String]())
  //  v match{
  //    case s=>println(s"$s")
  //  }
  val responseContent=ResponseContent(RestResponseInlineCode.succeed,Topic(10L,"专题1","推荐专题","图片集合",10))
  val map:mutable.Map[String,String]=mutable.Map()
  map+=("start"->"value")
//  transferObjToString()
  transferObjToString(responseContent)
  def transferObjToString(obj: Any): String = {
    println(Parser.ObjectToJsonString(obj))
    ""
  }
  val user=SlickDBPoolManager.DBPool.withTransaction { implicit session =>
    def createCondition(u: User) = {
      //      val condition=Seq("username","guo.yang").map(str=>u.username === str)

      var condition: Column[Boolean] = null
      Seq("username", "guo.yang").foreach { b =>
        if (condition == null) condition = u.username === b
        else condition = condition || u.username === b
      }
      condition
    }
    def createLikeCondition(u: User) = {
      u.username like "%yang%"
    }
    val res = for {
      c <- User if createLikeCondition(c)
    } yield (c.utype, c.password)
    println(res.update("like_type", "like_passwd"))
    res.list.foreach {
      println(_)
    }
    transferObjToString(res.list)

    //    val res=User.filter(createCondition).update()
    //    transferObjToString(res)
  }
  val uid=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
    (1 to 100).foreach{index=>(User returning User.map(_.id)).insert(UserRow(-100,"guo"+index,"phone"+index,"email.yang"+index,"passwd"+index,AccountUtil.account_isbind_yes,"","",""))}
  }



}
//object Test {
//  def main(args:String): Unit ={
//    val service=ExceptionFilterService andThen Http.newService("www.baidu.com:80")
//    ServerBuilder()
//    .codec(com.twitter.finagle.http.Http())
//    .name("proxy")
//    .bindTo(new InetSocketAddress(10000))
//    .build(service)
//  }
//}
//object ExceptionFilterService extends SimpleFilter[HttpRequest,HttpResponse]{
//  override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = service(request) handle { case ex =>
//    new org.jboss.netty.handler.codec.http.DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
//  }
//}

