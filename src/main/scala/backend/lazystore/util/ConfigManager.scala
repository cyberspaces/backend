package backend.lazystore.util

import java.util.Properties
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._

/**
 *  16/2/22.
 */
class ConfigManager(loadDefault:Boolean,resource:String="lazystore.properties"){
  def this()=this(true)

  private val map=new ConcurrentHashMap[String,String]()

  if(loadDefault){
    val inStream=this.getClass.getClassLoader.getResourceAsStream(resource)
    try{
      if(inStream!=null) addProperties(inStream)
      else{ throw new RuntimeException("Load default configuration file error!")}
    }finally {
      if(inStream!=null) inStream.close()
    }
  }

  private def addProperties(stream:java.io.InputStream): Unit = {
    val props = new Properties()
    props.load(stream)
    props.keySet().asScala.foreach { key =>
      if (props.containsKey(key)) {
        map.put(key.toString, props.get(key).toString)
      }
    }
  }
  def load(resourceFile:java.io.File):ConfigManager={
    val inStream=new java.io.FileInputStream(resourceFile)
    try {
      addProperties(inStream)
    }finally {
      if(inStream!=null) inStream.close()
    }
    this
  }
  def set(key:String,value:String):ConfigManager={
    map.put(key,value)
    this
  }
  def get(key:String,defaultValue:String="default"):String={
    if(map.containsKey(key)) map.get(key)
    else defaultValue
  }
  def console()={
    map.asScala.foreach(println)
  }

}
object ConfigManager {
  private val config:ConfigManager= new ConfigManager()
  def apply(key:String):String={
    config.get(key)
  }
  def load(path:String):ConfigManager={
    val file=new java.io.File(path)
    if(file.isFile){
      config.load(file)
    }
    config
  }
  def print()={
    config.console()
  }
}
