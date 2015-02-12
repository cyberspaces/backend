import java.net.InetSocketAddress
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

import cn.changhong.web.init.GlobalConfigFactory
import cn.changhong.web.util.Parser
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Response, Http, Request, RichHttp}
import com.twitter.util.{Future, Duration}
import org.jboss.netty.handler.codec.http.QueryStringDecoder
import com.twitter.finagle.Service
/**
 * Created by yangguo on 15-2-11.
 */
object TestDecoder extends App {
  val encodeStr = "GO%E6%A1%8C%E9%9D%A2"
  val decodeUtil = new QueryStringDecoder("http://localhost/lazystore/v1/category?params=GO%E6%A1%8C%E9%9D%A2")

  val service = new Service {
    override def apply(str: String): String = ???
  }
  println(decodeUtil.getPath + decodeUtil.getParameters.get("params").get(0))
  ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .readTimeout(Duration(5, TimeUnit.SECONDS))
    .bindTo(new InetSocketAddress(GlobalConfigFactory.server_port))
    .name(GlobalConfigFactory.server_name)
    .build(new com.twitter.finagle.Service[Request, Response] {
    override def apply(request: Request): Future[Response] = {
      val decoder = new QueryStringDecoder(request.uri)
      println(decoder.getPath + "," + decoder.getParameters.get("tag").get(0))
      Future.value(Response())
    }
  })

}
