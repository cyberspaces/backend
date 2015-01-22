package cn.changhong.lazystore.persistent.dao

import cn.changhong.lazystore.controller.AppsRequest
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.util.{RestResponseInlineCode, RestException}

import scala.slick.jdbc.StaticQuery.interpolation
/**
 * Created by yangguo on 15-1-22.
 */
object CategoryDao {
  private[this] val t_category_name="app_type_name"
  private[this] val c_category_parent_id="parent_id"
  private[this] val c_category_type_name="name"
  def getCategorys(request:AppsRequest)={
    SlickDBPoolManager.DBPool.withTransaction { implicit session =>
      val columns = request.columns match {
        case Some(s) => s
        case None => "*"
      }
      request.condition match {
        case Some(s) => sql"select #$columns from #$t_category_name where #$c_category_parent_id in (select #$c_category_parent_id from #$t_category_name where #$c_category_type_name = #${request.condition.get}})".as(SlickResultMap).list
        case None=>throw new RestException(RestResponseInlineCode.invalid_request_parameters,"请输入类别")
      }
    }
  }
}
