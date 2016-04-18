import org.jsoup.Jsoup
import org.specs2.mutable.Specification

import scala.collection.JavaConverters._

/**
 * Created  on 15-8-17.
 */
class TestJsonpConnect extends Specification {
  "test" >> {
    val cookies="bdshare_firstime=1439774769510; _gat=1; _ga=GA1.2.99794882.1439774769; Hm_lvt_c680f6745efe87a8fabe78e376c4b5f9=1439774769; Hm_lpvt_c680f6745efe87a8fabe78e376c4b5f9=1439780121".split(";").map{kv=>
      val _kv=kv.trim.split("=")
      if(_kv.size==2) {
        (_kv(0) -> _kv(1))
      }else ("default"->"default")
    }.toMap
    println(cookies)
//    val cookies=Map("bdshare_firstime"->"1439774769510", "_ga"->"GA1.2.99794882.1439774769", "Hm_lvt_c680f6745efe87a8fabe78e376c4b5f9"->"1439774769","Hm_lpvt_c680f6745efe87a8fabe78e376c4b5f9"->"1439778819")
    val con=Jsoup.connect("http://www.wandoujia.com/tag/app")
    con.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36")
    con.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    con.header("Accept-Encoding","gzip, deflate, sdch")
    con.header("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6,en-US;q=0.4,en-GB;q=0.2,zh-TW;q=0.2")
    con.header("Cache-Control","max-age=0")
    con.cookies(cookies.asJava)
    con.get()
//    val client=HttpClient.New(new URL("http://www.baidu.com"))
//
//    val stream=client.getInputStream
//    val sb=new StringBuilder
//    val buffer=new Array[Byte](1024)
//    Iterator continually(stream.read(buffer,0,1024)) takeWhile(_ > 0) foreach{
//      sb.append(buffer)
//    }
//    println(sb.toString())

    true
  }

}
