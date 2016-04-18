import java.net.InetSocketAddress
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

import backend.base.init.GlobalConfigFactory
import backend.base.util.Parser
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, Response, RichHttp}
import com.twitter.util.{Duration, Future}
import org.jboss.netty.handler.codec.http.QueryStringDecoder
import com.twitter.finagle.Service
import org.specs2.mutable.Specification
/**
 * Created  on 15-2-11.
 */
class TestDecoder extends Specification {
  "test OK" >> {
    val decodeUtil = new QueryStringDecoder("http://localhost/lazystore/v1/category?params=GO%E6%A1%8C%E9%9D%A2")
    println(decodeUtil.getPath +"\r\n"+ decodeUtil.getParameters.get("params").get(0))

    //  ServerBuilder()
    //    .codec(RichHttp[Request](Http()))
    //    .readTimeout(Duration(5, TimeUnit.SECONDS))
    //    .bindTo(new InetSocketAddress(GlobalConfigFactory.server_port))
    //    .name(GlobalConfigFactory.server_name)
    //    .build(new com.twitter.finagle.Service[Request, Response] {
    //    override def apply(request: Request): Future[Response] = {
    //      val decoder = new QueryStringDecoder(request.uri)
    //      println(decoder.getPath + "," + decoder.getParameters.get("tag").get(0))
    //      Future.value(Response())
    //    }
    //  })

    true
  }
}
