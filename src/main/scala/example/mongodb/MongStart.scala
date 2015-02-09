package example.mongodb

import java.util.Date

import cn.changhong.lazystore.persistent.T.Tables.LazyappRow
import cn.changhong.lazystore.persistent.dao.Appsdao
import cn.changhong.web.persistent.SlickDBPoolManager
import com.mongodb.{DBObject, BasicDBObject, MongoClient}
import scala.collection.JavaConverters._

import scala.collection.mutable.Map
/**
 * Created by yangguo on 15-2-9.
 */
object MongoStart {
  type FullApp=DBObject
  type apks=java.util.List[DBObject]
  type award=DBObject
  type categories=java.util.List[DBObject]
  type developer=DBObject
  type icons=DBObject
  type latestapk=DBObject
  type negComments=java.util.List[DBObject]
  type posComments=java.util.List[DBObject]
  type screenshots=DBObject
  type stat=DBObject
  type _extra = DBObject
  type tags=java.util.List[DBObject]

  type TransferBigApp=(apks,award,categories,developer,icons,latestapk,negComments,posComments,screenshots,stat,tags)


  val map:Map[String,Int]=Map()
  def main(args:Array[String]): Unit ={
    ()
    val client=new MongoClient("localhost:27017")
    val db=client.getDB("appstore")
    val collection=db.getCollection("wandoujia")
    var downloadCount:Int=134142938
    (0 to 1).foreach {index=>
      val json = collection.find(new BasicDBObject(),new BasicDBObject()).sort(new BasicDBObject("downloadCount", -1)).skip(index*10).limit(10)
      println(index+"->"+map.size)
      while (json.hasNext) {
        val kv = json.next()
        kv.keySet().asScala foreach { key =>
          map.get(key) match {
            case Some(v) =>
            case None => map += (key -> 1)
          }
        }
      }
    }
    println(map.keySet.fold("")(_+","+_))
    val json=collection.find().sort(new BasicDBObject("downloadCount",-1)).limit(1)
    Iterator continually(json.hasNext())  takeWhile(_ == true) foreach{i=>
      val lastapk=json.next().get("latestApk").asInstanceOf[DBObject]
      val downlaodUrls=lastapk.get("downloadUrls").asInstanceOf[java.util.List[DBObject]]
      println(downlaodUrls.size())
//      println(lastapk.keySet().asScala.fold("")(_+","+_))
    }
  }
  def getCollection(app:DBObject)= {
    val apks = app.get("apks").asInstanceOf[apks]
    val award = app.get("award").asInstanceOf[award]
    val categories = app.get("categories").asInstanceOf[categories]
    val developer = app.get("developer").asInstanceOf[developer]
    val icons = app.get("icons").asInstanceOf[icons]
    val lastestapk = app.get("latestApk").asInstanceOf[latestapk]
    val negComments = app.get("negComments").asInstanceOf[negComments]
    val posComments = app.get("posComments").asInstanceOf[posComments]
    val screenshots = app.get("screenshots").asInstanceOf[screenshots]
    val stat = app.get("stat").asInstanceOf[stat]
    val tags = app.get("tags").asInstanceOf[tags]




    val id = new Date().getTime
    val last_apppkg_id = new Date().getTime
    val appdev_id = new Date().getTime
    val packagename=lastestapk.get("packageName").toString
    val title=app.get("title").toString
    val icon=app.get("icons").toString
    val decs=app.get("snippet").toString
    val creation=new Date().getTime
    val apptype="wandoujia.android.app"
    val status="release"
    val updateddate=new Date().getTime
    val lazyapprow=LazyappRow(id,last_apppkg_id,appdev_id,packagename,title,icon,decs,creation,apptype,status)
    Appsdao.insertApps(Seq(lazyapprow))


  }
  def createLazyAppRow() ={

  }

}
