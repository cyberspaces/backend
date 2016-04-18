import java.net.URLEncoder
import java.util.UUID

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import org.specs2.mutable.Specification

/**
 * Created  on 15-2-3.
 */
class TestClient  extends Specification {
  "TestClient OK" >> {
    val client=ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .dest("127.0.0.1:8082")
      .hostConnectionLimit(1)
      .name("client")
      .build()
    val request=Request()
    request.headers().add("Client_Id",UUID.randomUUID().toString)
    request.setUri("/lazystore/v1/category?params="+URLEncoder.encode("便捷生活","utf8"))
    client(request) onSuccess{response=>
      println(new String(response.content.array()))
      //System.exit(0)
    }onFailure{ex=>
      ex.printStackTrace()
    }
    true
  }
}
