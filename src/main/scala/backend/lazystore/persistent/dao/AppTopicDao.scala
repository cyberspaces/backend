package backend.lazystore.persistent.dao

import java.util.Date
import backend.lazystore.service.AppsRequest
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.{RestRespCode, RestException, RestRequest, Parser}

import backend.lazystore.persistent.T.Tables._

import scala.slick.driver.MySQLDriver.simple._

import scala.slick.jdbc.StaticQuery.interpolation

import SqlProvider._
/**
 *  15-1-23.
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
      case ex:Exception=>throw new RestException(RestRespCode.db_executor_error,s"db executor error,${ex.getMessage}")
    }
  }
  def getAppTopics(request:RestRequest)={
      val params = request.urlParams.all
      val position = {
        val list = params.get("position")
        if (list!=null && list.size() >= 1) list.get(0)
        else "首页top1"
      }
      val columns = {
        val list = params.get("columns")
        if (list !=null && list.size() >= 1) list.get(0)
        else "url,img,action"
      }
      val _p_time = SqlProvider.transferRequestParams({
        val list = params.get("p_t")
        if (list!=null && list.size() >= 1) list.get(0)
        else System.currentTimeMillis().toString
      })
      val p_time=SqlProvider.transferRequestParams(_p_time)
      val sql = s"select ${SqlProvider.transferRequestParams(columns)} from $T_LAZYTOPIC where lazytopicposition_name like '${SqlProvider.transferRequestParams(position)}%' and (launchtime <= $p_time and expired>=$p_time) and topicstatus='发布'"
      exec(sql)
  }
  def updateAppTopics()={}
}
