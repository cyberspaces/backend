package backend.lazystore.spider.demo

import java.io._
import java.net.{HttpURLConnection, URL}
import java.util.concurrent.{FutureTask, Callable, Future, Executors}

import backend.base.init.GlobalConfigFactory
import org.jsoup.Jsoup

import scala.collection.JavaConverters._

import scala.util.parsing.json.JSON

/**
 *  14-11-19.
 */
case class sAppTag(name:String,url:String)
case class Classified(parent:AppTag,childs:List[AppTag])
object SpliderAllClassifed {
  //获取所有分类
  private def createClassifedDoc = {
    val cookies="bdshare_firstime=1439774769510; _gat=1; _ga=GA1.2.99794882.1439774769; Hm_lvt_c680f6745efe87a8fabe78e376c4b5f9=1439774769; Hm_lpvt_c680f6745efe87a8fabe78e376c4b5f9=1439780121".split(";").map{kv=>
      val _kv=kv.trim.split("=")
      if(_kv.size==2) {
        (_kv(0) -> _kv(1))
      }else ("default"->"default")
    }.toMap
    println(cookies)
    //    val cookies=Map("bdshare_firstime"->"1439774769510", "_ga"->"GA1.2.99794882.1439774769", "Hm_lvt_c680f6745efe87a8fabe78e376c4b5f9"->"1439774769","Hm_lpvt_c680f6745efe87a8fabe78e376c4b5f9"->"1439778819")
    val con=Jsoup.connect("http://www.wandoujia.com/tag/app")
    con.timeout(10000)
    con.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36")
    con.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    con.header("Accept-Encoding","gzip, deflate, sdch")
    con.header("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6,en-US;q=0.4,en-GB;q=0.2,zh-TW;q=0.2")
    con.header("Cache-Control","max-age=0")
    con.cookies(cookies.asJava)
    con.get()
  }

  def apply() = {
    var classifieds: List[Classified] = List()
    val doc = createClassifedDoc
    val allClassifieds = doc.select("li.parent-cate")
    val allIt = allClassifieds.iterator()
    while (allIt.hasNext) {
      val classified = allIt.next()
      val allChild = classified.select("a[href]")
      val cIt = allChild.iterator()
      allChild.toArray()
      var first = true
      var childs: List[AppTag] = List()
      var parent: AppTag = null
      while (cIt.hasNext) {
        val child = cIt.next()
        val app = AppTag(child.attr("title"), child.attr("href"))
        if (first) {
          first = false
          parent = app
        } else {
          childs = app :: childs
        }
      }
      val item = Classified(parent, childs)
      classifieds = item :: classifieds
    }
    classifieds
  }
}


class SpliterTask(tag:AppTag,parentTag:String)extends Callable[Int]{
  override def call(): Int = {
    try {
      run
      1
    } catch {
      case e  : Throwable =>
        e.printStackTrace()
        Util.log(e.getLocalizedMessage+"\n")
        -1
    }
  }

  def run(): Unit = {
    val tagPath=Util.createDir(parentTag)
    val byteArray = new ByteArrayOutputStream()
    val buffer: Array[Byte] = new Array[Byte](1024)
    try{
      val count=20
      var start=0
      while(start>=0){
        val url = "http://apps.wandoujia.com/api/v1/apps?tag="+tag.name+"&max=" + count + "&start=" + start
        start += count
        UrlLog(url)
        val conn = new URL(url).openConnection().asInstanceOf[HttpURLConnection]
        conn.setConnectTimeout(5000)
        conn.getResponseCode match {
          case 200 =>
            val instream = conn.getInputStream
            try {
              byteArray.reset()
              Iterator continually (instream.read(buffer)) takeWhile (_ > 0) foreach (byteArray.write(buffer, 0, _))
              if (byteArray.size() < 1000) {
                start = -1
                ErrorLog(url)
              } else {
                val json=new String(byteArray.toByteArray)
                MongoStart.insertDataToCollection(json)
              }
            }catch {
              case ex:Exception=>ErrorLog(ex.getLocalizedMessage)
            }finally {
              Util.closeStream(instream)
            }
          case e => {
            start = -1
            ErrorLog(url)
          }
        }
      }
    }finally {
//      Util.closeStream(outStream)
    }
  }
}
object Util{
  val executor=Executors.newFixedThreadPool(8)
  val basePath=""
  val errorStream=new FileWriter(basePath+"error.log")

  def log(content:String){
    errorStream.write(content+"\n")
    errorStream.flush()
  }
  def createDir(tag:String): String ={
    val path=basePath+tag
    val file=new File(path)
    if(!file.exists()) file.mkdirs()
    path
  }
  def closeStream(stream:Closeable): Unit ={
    try{stream.close()}catch {case e : Throwable=>}
  }
  def doTask[T](task:Callable[T]):Future[T]={
    val future=new FutureTask[T](task)
    executor.submit(future)
    future
  }
}




