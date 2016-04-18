import backend.base.router.{LogAopAction, RestAopRouterProvider}
import backend.base.util.RestRequest
import com.twitter.finagle.http.Response
import org.specs2.mutable.Specification

trait AopService extends ForeService with LogService
object BusServiceProxyOBJ extends BusService with AopService
object BusServiceOBJ extends BusService with AopService

class AopTest  extends Specification {
  "TestClient OK" >> {
    println(BusServiceOBJ("http://www.a.com/"))

    true
  }
}

trait Service{
  def apply(str:String):String
}

class BusService extends Service {
  override def apply(str:String):String={
    println(s"request=$str")
    println("200 OK")
    "200 OK"
  }
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