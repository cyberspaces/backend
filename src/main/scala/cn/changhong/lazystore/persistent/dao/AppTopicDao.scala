package cn.changhong.lazystore.persistent.dao

import java.util.Date

import cn.changhong.lazystore.controller.AppsRequest
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode, RestException, RestRequest, Parser}

import cn.changhong.lazystore.persistent.T.Tables._

import scala.slick.driver.MySQLDriver.simple._

import scala.slick.jdbc.StaticQuery.interpolation

import SqlProvider._
/**
 * Created by yangguo on 15-1-23.
 */
object AppTopicDao {
  private[this] val c_lazytopic_position="topicposition"
  private[this] val T_LAZYTOPIC="lazytopic"
  private[this] val c_lazytopic_location="location"
  def addAppTopic(request:RestRequest)={
    val bean = Parser.LazyTopicParser(Parser.ChannelBufferToString(request.underlying.getContent))
    bean.creation = new Date().getTime
    try {
      SlickDBPoolManager.DBPool.withTransaction { implicit session =>
        (Lazytopic returning Lazytopic.map(_.location)).insert(bean)
      }
    }catch{
      case ex:Exception=>throw new RestException(RestResponseInlineCode.db_executor_error,s"db executor error,${ex.getMessage}")
    }
  }
  def getAppTopics(request:AppsRequest)={
    val where=request.condition match{
      case Some(s)=>s"$c_lazytopic_position='$s'"
      case None=>"1=1"
    }
    val columns=request.columns match{
      case Some(s)=>s
      case None=>"*"
    }
    val sql=s"select $columns from $T_LAZYTOPIC where $where order by $c_lazytopic_location"
    exec(sql)
  }
  def updateAppTopics()={}
}
