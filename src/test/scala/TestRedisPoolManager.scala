import backend.base.persistent.RedisPoolManager
import org.specs2.mutable.Specification

class TestRedisPoolManager   extends Specification {
  "TestRedisPoolManager OK" >> {
    val max_valid_request_frequency=10
    val max_valid_request_expire_seconds=10
    RedisPoolManager.redisCommand{implicit client=>
      val key="h_type_uid_cid"
      val count=client.incr(key)
      if(count>max_valid_request_frequency) true
      else {
        if(count == 1) client.expire(key,max_valid_request_expire_seconds)
        false
      }
      val helloKey=client.get("hello")
      println("helloKey="+helloKey+","+(helloKey==null))
      println(client.set("testkey1","value1"))
      println(client.set("testkey1","value1"))
    }
    true
  }
}