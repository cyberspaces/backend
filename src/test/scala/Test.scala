import java.net.InetSocketAddress

import cn.changhong.web.util.{RestResponseContent, DefaultHttpResponse}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.{Http, SimpleFilter, Service}
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.{HttpResponseStatus, HttpVersion, HttpResponse, HttpRequest}

/**
 * Created by yangguo on 14-12-11.
 */
object Test {
  def main(args:String): Unit ={
    val service=ExceptionFilterService andThen Http.newService("www.baidu.com:80")
    ServerBuilder()
    .codec(com.twitter.finagle.http.Http())
    .name("proxy")
    .bindTo(new InetSocketAddress(10000))
    .build(service)
  }
}
object ExceptionFilterService extends SimpleFilter[HttpRequest,HttpResponse]{
  override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = service(request) handle { case ex =>
    new org.jboss.netty.handler.codec.http.DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
  }
}

