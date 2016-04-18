package backend.lazystore.service

import backend.base.util.{ResponseContent, RestRequest}

/**
 *  15-5-28.
 */
object PromotedService extends PromotedService with BaseAopService
class PromotedService extends BaseService{
  override def apply(request: RestRequest): ResponseContent = ???
}
