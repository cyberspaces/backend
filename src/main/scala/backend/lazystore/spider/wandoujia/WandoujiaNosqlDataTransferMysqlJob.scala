package backend.lazystore.spider.wandoujia

import java.util.Date
import java.util.concurrent.atomic.{AtomicInteger, AtomicLong}

import backend.lazystore.persistent.T.Tables.{Appdev, AppdevRow, Apppkg, ApppkgRow, Apptags, ApptagsRow, Lazyapp, LazyappRow}
import backend.lazystore.persistent.dao.SqlProvider
import backend.lazystore.spider.demo.ErrorObtLog
import backend.lazystore.GlobalConfig
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.ExecutorProvider
import com.mongodb.{BasicDBObject, DBObject, MongoClient}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scala.collection.mutable.Map
import scala.slick.driver.MySQLDriver.simple._
import scala.util.parsing.json.JSON

/**
 *  15-2-9.
 */
object WandoujiaNosqlDataTransferMysqlJob {
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

  val error=LoggerFactory.getLogger(GlobalConfig.global_log_request_error_name)
  val map:Map[String,Int]=Map()

  val speityIndex=new AtomicInteger(1)
  val topsortIndex=new AtomicInteger(1)
  val hotsortIndex=new AtomicInteger(1)
  val otherIndex=new AtomicInteger(1)
  val generatorID=new AtomicLong(new Date().getTime)
  val defaultPkgVersion=100
  val defaultStar=5
  val defaultStatus="release"
  val defaultType="wandoujia.android.app"
  val defaultLazyAdmin_Id=1423548742269L

  def main(args:Array[String]): Unit ={
    val client=new MongoClient("localhost:27017")

    var downloadCount:Int=134142938
    (0 to args(0).toInt) foreach { index =>
      val db = client.getDB("appstore")
      val collection = db.getCollection("wandoujia")
      ExecutorProvider.futurePool {
        val json = collection.find().sort(new BasicDBObject("downloadCount", -1)).skip(index * 20).limit(20)
        Iterator continually (json.hasNext()) takeWhile (_ == true) foreach { i =>
          println(index)
          val app = json.next()
          doTransactionInsertAppJob(app)
        }
      }
    }
  }
  def doTransactionInsertAppJob(app:DBObject)={//转化mongodb中的数据到mysql
    val lazyapp=createLazyApp(app)
    val apppkg=createAppPkg(app,lazyapp.id,lazyapp.lastApppkgId)
    val apptags=createAppTags(app,lazyapp.id,lazyapp.title)
    val developer=createDeveloperInfo(app,lazyapp.appdevId)
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      try{
        Lazyapp.insert(lazyapp)
        Apppkg.insert(apppkg)
        Apptags.insertAll(apptags:_*)

        Appdev.insert(developer)
      }catch{
        case ex:Throwable=>
          ErrorLog(s"title=${lazyapp.title},appid=${lazyapp.id},apppkgid=${lazyapp.lastApppkgId},packagename=${lazyapp.packagename}"+ex.getLocalizedMessage)
          session.rollback()
      }
    }
  }
  def getCurrentAPPInfo(packageName:String,title:String)={
    val selectSql=s"select versioncode,lazyapp_id,devcode from apppkg where id = (select last_apppkg_id from lazyapp where packagename='${SqlProvider.transferRequestParams(packageName)}') order by id desc limit 1;"
    val resultMap=SqlProvider.exec(selectSql)
    resultMap match{
      case item::list=>item
      case i=>Map(""->"")
    }
  }
  private def isNeedUpdate(oldCode:String,newCode:Int):Boolean={
    var result=true
    try{
      val _code=oldCode.toInt
      result={
        if(_code == 100) true
        else oldCode.toInt < newCode
      }
    }catch {
      case ex:Throwable=>
    }
    result
  }
  private def isNeedUpdate(oldCode:String,newCode:String):Boolean={
    def decoder(code:String):Array[Int]=code.split("\\.").map(c=>try{c.toInt}catch{case ex:Throwable=>0})
    val oldCodes=decoder(oldCode)
    val newCodes=decoder(newCode)
    var isNeedUpdate=false
    var index=0
    while(index<newCodes.size){
      if(index<oldCode.size&&newCodes(index)>oldCodes(index)){
        isNeedUpdate=true
      }
      index+=1
    }
    isNeedUpdate
  }

  def createLazyApp(app:DBObject)= {//创建lazyapp数据结构
    val id = generatorID.getAndIncrement
    val last_apppkg_id = generatorID.getAndIncrement
    val appdev_id = generatorID.getAndIncrement
    val latestApk=try{
      app.get("apks").asInstanceOf[apks].get(0)
    }catch{
      case ex:Throwable=>
        ex.printStackTrace()
        null
    }
    val packagename=toString(latestApk,("packageName"))//.toString
    val title=toString(app,("title"))//.toString
    ErrorLog(title)
    val icon=toString(app,("icons"))//toString
    val decs=toString(app,("description"))//.toString description
    val creation=new Date().getTime
    val apptype=defaultType
    val status=defaultStatus
    val updateddate=new Date().getTime

    def toInt(columnName:String):Option[Int] ={
      try {
        Some(app.get(columnName).toString.toInt)
      }catch {
        case ex:Throwable=>None
      }
    }
    val t_downloadcount=toInt("downloadCount")
    val t_commentcount=toInt("commentsCount")
    val t_installedcount=toInt("installedCount")
    val lazyapprow=LazyappRow(id,last_apppkg_id,appdev_id,packagename,title,icon,decs,creation,apptype,status,new Date().getTime,t_downloadcount,t_installedcount,t_commentcount,Some(speityIndex.getAndIncrement),Some(topsortIndex.getAndIncrement),Some(hotsortIndex.getAndIncrement),Some(otherIndex.getAndIncrement),Some(defaultStar))
    lazyapprow
  }

  def toString(app:DBObject,columnName:String)={
    try{
      app.get(columnName).toString
    }catch{
      case ex:Throwable=>
        ""
    }
  }
  def toInt(app:DBObject,columnName:String)={
    try{
      app.get(columnName).toString.toInt
    }catch{case ex:Throwable=>
      ex.printStackTrace()
        -1423548
    }
  }
  def toLong(app:DBObject,columnName:String)={
    try{
      app.get(columnName).toString.toLong
    }catch{case ex:Throwable=>System.currentTimeMillis()}
  }


  def createAppPkg(app:DBObject,lazyAppId:Long,appPkgId:Long= -1)= {
    val latestApk = try{
      app.get("apks").asInstanceOf[apks].get(0)
    }catch{
      case ex:Throwable=>
      ex.printStackTrace()
      null
    }
    val id = appPkgId
    val lazyapp_id = lazyAppId

    val downloadurls = toString(latestApk, "downloadUrls") //latestApk.get("downloadUrls").toString
    val pkgsize = toInt(latestApk, "bytes") //latestApk.get("bytes").toString.toDouble
    val creation = new Date().getTime
    val md5 = toString(latestApk, "md5") //latestApk.get("md5").toString
    val source = "wandoujia"

    val devcode = try{toInt(latestApk,"versionCode")}catch {case ex:Throwable=>defaultPkgVersion}
    val versioncode = toString(latestApk, "versionName") //latestApk.get("versionName").toString
    val changelog = toString(app, "changelog") //app.get("changelog").toString
    val screenshots = toString(app, "screenshots") //app.get("screenshots").toString

    val maxsdkversion = toInt(latestApk, "maxSdkVersion") //latestApk.get("maxSdkVersion").toString.toInt
    val minsdkversion = toInt(latestApk, ("minSdkVersion")) //.toString.toInt
    val targetsdkversion = toInt(latestApk, ("targetSdkVersion")) //.toString.toInt
    val dangerouspermissions = toString(latestApk, ("dangerousPermissions")) //.toString
    val permissionlevel = toString(latestApk, ("permissionLevel")) //.toString
    val permissionstatement = toString(latestApk, ("permissionGroups")) //.toString
    val securitystatus = toString(latestApk, ("securityStatus")) //.toString
    val securitydetail = toString(latestApk, ("securityDetail")) //.toString

    val publishdate = new Date().getTime
    val lazyadmin_id = defaultLazyAdmin_Id
    val pkgstatus = "release"
    val t_downloadcount = toInt(app, "downloadCount")
    val t_commentcount = toInt(app, "commentsCount")
    val t_installedcount = toInt(app, "installedCount")
    val dislikescount = toInt(app, "dislikesCount")
    val likescount = toInt(app, "likesCount")
    val adminmsg = toString(app, ("editorComment")) //.toString
    val apppkg = ApppkgRow(id, lazyapp_id, downloadurls, pkgsize, creation, md5, source, devcode, versioncode, Some(changelog), Some(screenshots), maxsdkversion, minsdkversion, targetsdkversion, Some(dangerouspermissions), Some(permissionlevel), Some(permissionstatement), Some(securitydetail), Some(securitystatus), Some(publishdate), Some(adminmsg), lazyadmin_id, Some(pkgstatus), Some(t_downloadcount), Some(t_installedcount), dislikescount = Some(dislikescount), Some(likescount), Some(t_commentcount))
    apppkg
  }

  def updateAPPVersion(app:DBObject)= {
    val latestApk = try{
      app.get("apks").asInstanceOf[apks].get(0)
    }catch{
      case ex:Throwable=>
        ex.printStackTrace()
        null
    }
    val packagename=toString(latestApk,("packageName"))//.toString
    val title=toString(app,"title")
    val exitsAppVersionInfos=getCurrentAPPInfo(packagename,title)
    exitsAppVersionInfos.get("devcode") match {
      case Some(item) => {
        val lazyAppId = exitsAppVersionInfos.getOrElse("lazyapp_id", "-1")
        val id = generatorID.getAndIncrement
        val lazyapp_id = try {
          lazyAppId.toString.toLong
        } catch {
          case ex: Throwable => -1
        }
        val devcode = try{
          toInt(latestApk,"versionCode")
        }catch{case ex:Throwable=>
          ex.printStackTrace()
          defaultPkgVersion
        }
        val versioncode = toString(latestApk, "versionName") //latestApk.get("versionName").toString
        println(s"current Version ${item},wandoujia server version ${versioncode}")
        if (lazyapp_id != -1 && isNeedUpdate(item.toString,devcode)){//!versioncode.trim().equals(item.toString.trim)) {
          val downloadurls = toString(latestApk, "downloadUrls") //latestApk.get("downloadUrls").toString
          val pkgsize = toInt(latestApk, "bytes") //latestApk.get("bytes").toString.toDouble
          val creation = toLong(latestApk, "creation")
          val md5 = toString(latestApk, "md5") //latestApk.get("md5").toString
          val source = "wandoujia"

          val changelog = toString(app, "changelog") //app.get("changelog").toString
          val screenshots = toString(app, "screenshots") //app.get("screenshots").toString

          val maxsdkversion = toInt(latestApk, "maxSdkVersion") //latestApk.get("maxSdkVersion").toString.toInt
          val minsdkversion = toInt(latestApk, ("minSdkVersion")) //.toString.toInt
          val targetsdkversion = toInt(latestApk, ("targetSdkVersion")) //.toString.toInt
          val dangerouspermissions = toString(latestApk, ("dangerousPermissions")) //.toString
          val permissionlevel = toString(latestApk, ("permissionLevel")) //.toString
          val permissionstatement = toString(latestApk, ("permissionGroups")) //.toString
          val securitystatus = toString(latestApk, ("securityStatus")) //.toString
          val securitydetail = toString(latestApk, ("securityDetail")) //.toString

          val icon=toString(app,"icons")
          val publishdate = creation
          val lazyadmin_id = defaultLazyAdmin_Id
          val pkgstatus = "release"

          val t_downloadcount = toInt(app, "downloadCount")
          val t_commentcount = toInt(app, "commentsCount")
          val t_installedcount = toInt(app, "installedCount")
          val dislikescount = toInt(app, "dislikesCount")
          val likescount = toInt(app, "likesCount")
          val adminmsg = toString(app, ("editorComment")) //.toString
          val apppkg = ApppkgRow(id, lazyapp_id, downloadurls, pkgsize, creation, md5, source, devcode, versioncode, Some(changelog), Some(screenshots), maxsdkversion, minsdkversion, targetsdkversion, Some(dangerouspermissions), Some(permissionlevel), Some(permissionstatement), Some(securitydetail), Some(securitystatus), Some(publishdate), Some(adminmsg), lazyadmin_id, Some(pkgstatus), Some(t_downloadcount), Some(t_installedcount), dislikescount = Some(dislikescount), Some(likescount), Some(t_commentcount))
          SlickDBPoolManager.DBPool.withTransaction { implicit session =>
            try {
              Apppkg.insert(apppkg)
            } catch {
              case ex: Throwable => ex.printStackTrace()
            }
          }

          val desc=toString(app,("description"))//.toString description

          val updateSql = s"update lazyapp set last_apppkg_id='${id}',updateddate='$creation',title='${title}',icon='$icon',described='$desc' where id='${lazyapp_id}' and status='release'"
          try {
            SqlProvider.exec1(updateSql)
          }catch{
            case ex:Throwable=>ErrorObtLog("error:"+updateSql+","+ex.printStackTrace())
          }
        }
      }
      case None =>doTransactionInsertAppJob(app)
    }
  }

  def createAppTags(app:DBObject,lazyAppId:Long,title:String="")={
    val lazyapp_id=lazyAppId
    val tags=app.get("tags").asInstanceOf[tags]
    var defaultWeith=2020
    val tagrows=tags.asScala.map{tag=>
      val tagName=toString(tag,("tag"))//.toString
      val weight=defaultWeith-20
      defaultWeith-=20
      ApptagsRow(lazyapp_id,title,tagName,weight)

    }
//      AppTagDao.insertAppTags(tagrows)
    tagrows
  }

  def createDeveloperInfo(app:DBObject,appdevId:Long)= {
    val id = appdevId
    val appdev = app.get("developer").asInstanceOf[developer]
    val company = Some(toString(appdev, "name"))
    val passwd = Some("")
    val email = Some(toString(appdev, "email"))
    val website = Some(toString(appdev, "website") match {
      case "" => toString(appdev, "urls")
      case s => s
    })
    val weibo = Some(toString(appdev, "weibo"))
    val weixi = Some("")
    val intro = Some(toString(appdev, "intro"))
    val verified = "No"
    val dev = AppdevRow(id, company, passwd, email, website, weibo, weixi, intro, Some(verified))
    dev
  }

}

