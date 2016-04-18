
import com.twitter.finagle.Filter
import com.twitter.util.Future
import org.specs2.mutable.Specification

sealed trait Event

trait Message extends Event{
  def console():Unit
}
class ConsoleMessage(msg:String) extends Message{
  override def console(): Unit = println(s"Console Message [$msg]")
}
class TerminalMessage(msg:String) extends Message{
  override def console(): Unit = println(s"Terminal Message [$msg]")
  def childE():Unit=println("dsadsa")
}
class ServiceConvariantDemo[-Req <: Message,+Rep >: Message] extends com.twitter.finagle.Service[Req,Rep] {
  override def apply(request: Req): Future[Rep] = Future.value{
    request
  }
}
class ServiceConvariantDemoSpecs  extends Specification {
  def apply()=new ServiceConvariantDemo[ConsoleMessage,Event]
  "ServiceConvariantDemoSpecs OK" >> {
//    ServiceConvariantDemo()(new ConsoleMessage("Console")).foreach{case e if e.isInstanceOf[Message]=>e.asInstanceOf[Message].console() }
    val a:Array[TerminalMessage]=Array(new TerminalMessage("message"))
    val b:Array[Message]=a.asInstanceOf[Array[Message]]//.asInstanceOf[Array[Message]]
    val m=b(0)
    m.console()
    a(0).childE()

    true
  }
}

//abstract class Filter[-ReqIn, +RepOut, +ReqOut, -RepIn]
//abstract class Filter[ReqIn, ReqOut,RepIn,RepOut] extends ((ReqIn, finagle.Service[ReqOut, RepIn]) => Future[RepOut]){}
//abstract class SimpleServerConvariantDemo[ReqIn,RepOut,ReqOut,RepIn] extends Filter[ReqIn,RepOut,ReqOut,RepIn]{
//   def apply(request: ReqIn, service: finagle.Service[ReqOut, RepIn]): Future[RepOut] = ??? //service(request)
//}
//class ConsumerSimpleFilter[ReqIn,ReqOut,RepIn,RepOut] extends ((ReqIn,com.twitter.finagle.Service[ReqOut,RepIn])=>Future[RepOut]){
//  override def apply(request: ReqIn, service: finagle.Service[ReqOut, RepIn]): Future[RepOut] = ???
////  def andThen[ReqIn1,RepOut1](filter:ConsumerSimpleFilter[])
////  def andThen[ReqIn1,RepOut1](service:finagle.Service[])
//}
//trait L[+A,-B,C >: Message <:Event]{
//  def +(b:B)= println(b)
//  def ex={
//    val a:Array[TerminalMessage]=Array(new TerminalMessage("message"))
//    val b:Array[Message]=a.asInstanceOf[Array[Message]]
//    b(0).console()
//  }
//}