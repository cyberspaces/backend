package backend.lazystore.service

/**
 *  15-7-8.
 */
package Promote {

import backend.lazystore.persistent.dao.SqlProvider
import backend.base.router.RestAction
import backend.base.util.{ResponseContent, RestRequest}
  import scala.collection.JavaConverters._

object DailyPromoteAction extends RestAction[RestRequest, ResponseContent] {
  override def apply(request: RestRequest): ResponseContent = DailyPromote(request)
}

private[this] object DailyPromote extends DailyPromote with BaseAopService

private[this] class DailyPromote extends BaseService {
  override def apply(request: RestRequest): ResponseContent = {
    val mainPos = request.urlParams.getSingleParam("p1","default")
    var where=s"mainposition='${SqlProvider.transferRequestParams(mainPos)}'"
    if(mainPos=="分类-推荐应用列表"){
      where+=s" and subposition='${SqlProvider.transferRequestParams(request.urlParams.getSingleParam("p2","default"))}'"
    }
    val columns = SqlProvider.transferRequestParams(request.urlParams.getSingleParam("columns","action,launchtime,expired"))
    val currentTime = System.currentTimeMillis()
    val sql = s"select ${columns} from prometedapps where $where and launchtime<= $currentTime and expired >= $currentTime and status='发布'"
    val content = SqlProvider.exec(sql)
    ResponseContent(content)
  }
}

}
