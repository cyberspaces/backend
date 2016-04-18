package backend.base.persistent

import java.util.UUID

import backend.lazystore.GlobalConfig
import backend.base.util.TokenUtil
import redis.clients.jedis.{JedisPool, JedisPoolConfig,Jedis}

/**
 *  14-12-10.
 */
object RedisPoolManager{
  private[this] val redisPool={
    val config=new JedisPoolConfig
    config.setMaxTotal(500)
    config.setMaxIdle(5)
    config.setMaxWaitMillis(1000*10)
    config.setTestOnBorrow(true)
    new JedisPool(config,GlobalConfig.redis_server_ip,GlobalConfig.redis_server_port,3000,"Kksebo",10)
}
  def redisCommand[T](f:Jedis=>T): T ={
    val client=redisPool.getResource
    try {
      f(client)
    }finally {
      redisPool.returnResource(client)
    }
  }
}
object RedisDataHandler{
  private lazy val expiredTime=172800
  def getValue(key:String):String={
    RedisPoolManager.redisCommand{implicit client=>
      client.get(key)
    }
  }
  def getValueBASE64EncoderKey(key:String):String={
    RedisPoolManager.redisCommand{implicit client=>
      client.get(createKey(key))
    }
  }
  def setValue(key:String,value:String)={
    RedisPoolManager.redisCommand{implicit client=>
      client.set(key,value)
      client.expire(key,expiredTime)
    }
  }
  private def createKey(key:String):String={
   new sun.misc.BASE64Encoder().encode(key.getBytes)
  }
}

