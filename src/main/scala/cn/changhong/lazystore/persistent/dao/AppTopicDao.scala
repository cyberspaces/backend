package cn.changhong.lazystore.persistent.dao

import cn.changhong.lazystore.controller.AppsRequest
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestRequest, Parser}

import cn.changhong.lazystore.persistent.Tables.Tables._

import scala.slick.driver.MySQLDriver.simple._

import scala.slick.jdbc.StaticQuery.interpolation

/**
 * Created by yangguo on 15-1-23.
 */
object AppTopicDao {
  private[this] val c_app_topic_type="subject_type"
  private[this] val t_app_topic="subject_base"
  def addAppTopic(request:RestRequest)={
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      val bean=Parser.AppTopicParser(Parser.ChannelBufferToString(request.underlying.getContent))
      (AppSubjectBase returning AppSubjectBase.map(_.subjectId)).insert(bean)
    }
  }
  def getAppTopics(request:AppsRequest)={
    val where=request.condition match{
      case Some(s)=>s"$c_app_topic_type='$s'"
      case None=>"1=1"
    }
    val columns=request.columns match{
      case Some(s)=>s
      case None=>"*"
    }
    val sql=s"select $columns from $t_app_topic where $where"
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      sql"#$sql".as(SlickResultMap).list
    }
  }
  def deleteAppTopic(subjectId:String)={
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
    }
  }
  def updateAppTopics()={}
}
