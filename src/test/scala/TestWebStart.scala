import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import backend.base.router._
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import com.twitter.util.Duration
import org.specs2.mutable.Specification

/**
 * Created  on 14-12-8.
 */
class TestWebStart  extends Specification {
  "Test OK" >> {

    val service = AccessLogFilterService andThen ExceptionFilterService andThen SpiderActionInspectorFilterService andThen  TimeoutFilterService andThen ForeRouter
    ServerBuilder()
      .codec(RichHttp[Request](Http()))
      .readTimeout(Duration(5,TimeUnit.SECONDS))
      .bindTo(new InetSocketAddress("localhost",8080))
      .name("testserver")
      .build(service)

    true must_===(true)
  }
}
