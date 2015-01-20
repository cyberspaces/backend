package cn.changhong.lazystore.controller

import cn.changhong.web.init.GlobalConfigFactory
import cn.changhong.web.util.{RestResponseInlineCode, RestException, Parser, RestRequest}
import com.twitter.finagle.http.Response
import org.slf4j.LoggerFactory

/**
 * Created by yangguo on 15-1-19.
 */
trait BaseService{
  def apply(request:RestRequest):Response
}
trait LogService extends BaseService{
  val log=LoggerFactory.getLogger(GlobalConfigFactory.log_user_name)
  abstract override def apply(request:RestRequest):Response={
    log.info(createLog(request))
    super.apply(request)
  }
  def createLog(request:RestRequest): String ={
    Parser.ObjectToJsonString(request.logBean)
  }
}
trait TempAuthCheckService extends BaseService{
  abstract override def apply(request:RestRequest):Response={
    if(!checkTempAuth(request)) throw new RestException(RestResponseInlineCode.permission_need,"无权访问")
    super.apply(request)
  }
  def checkTempAuth(request:RestRequest):Boolean={
    true
  }
}
trait BaseAopService extends TempAuthCheckService with LogService