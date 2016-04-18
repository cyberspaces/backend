package backend.lazystore.spider.demo

import backend.lazystore.GlobalConfig
import org.specs2.mutable.Specification

/**
  * Created by Administrator on 2016/3/18.
  */

class WandoujiaAppsSpider  extends Specification {
  "Test OK" >> {
//    if(args.size>=4) {
//      GlobalConfigFactory.db_server_ip = args(0)
//      GlobalConfigFactory.db_server_port = args(1).toInt
//      GlobalConfigFactory.db_server_username = args(2)
//      GlobalConfigFactory.db_server_password = args(3)
//    }
    val types=SpliderAllClassifed()//获取豌豆夹应用分类
    val task=types.flatMap { c =>
        c.childs.map{t=>
          new SpliterTask(t,c.parent.name)//获取数据并存储到mongodb中
        }
      }
    val res=task.map { item =>
      Util.doTask(item)
    }

    true must_===(true)
  }

}