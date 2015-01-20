import cn.changhong.web.router.{RestAopRouterProvider, LogAopAction}
import cn.changhong.web.util.RestRequest
import com.twitter.finagle.http.Response

/**
 * Created by yangguo on 15-1-19.
 */
object AopTest {
  def main(args:Array[String]): Unit ={
    TestBusService("hello world")
  }
}
trait Service{
  def apply(str:String):String
}
trait ForeService extends Service{
  abstract override def apply(str:String):String={
    println("ForeService Start...")
    val res=super.apply(str)
    println("ForeService End...")
    res
  }
}
trait LogService extends Service{
  abstract override  def apply(str:String):String={
    println("LogService Start...")
    val res=super.apply(str)
    println("LogService End...")
    res
  }
}
object BusService extends BusService with ForeService with LogService
class BusService extends Service {
  override def apply(str:String):String={
    println(s"request=$str")
    ""
  }
}
trait AopService extends ForeService with LogService
object TestBusService extends BusService with AopService
