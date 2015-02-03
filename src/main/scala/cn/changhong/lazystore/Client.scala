package cn.changhong.lazystore

import java.util.UUID

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}

/**
 * Created by yangguo on 15-2-3.
 */
object Client {
  def main(args:Array[String]): Unit ={
    val client=ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .dest("10.9.52.31:8085")
      .hostConnectionLimit(1)
      .name("client")
      .build()
    val request=Request()
    request.headers().add("Client_Id",UUID.randomUUID().toString)
    request.setUri("/lazystore/v1/12")
    client(request) onSuccess{response=>
      println(new String(response.content.array()))
    }onFailure{ex=>
      ex.printStackTrace()
    }
  }
}
