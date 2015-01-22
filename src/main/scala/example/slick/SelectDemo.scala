package example.slick

import cn.changhong.lazystore.controller.AppsRequest
import cn.changhong.lazystore.persistent.dao.{SlickResultString, SlickResultMap}
import cn.changhong.web.persistent.SlickDBPoolManager
import cn.changhong.web.persistent.Tables.Tables._
import cn.changhong.web.util.Parser
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.PositionedResult

/**
 * Created by yangguo on 15-1-21.
 */
object SelectDemo extends App{
  import scala.slick.jdbc.{StaticQuery=>Q}
  import Q.interpolation
 def Sql={
   val columns="id,username,password"
   val list=sql1
   val t_name="user"
   SlickDBPoolManager.DBPool.withSession { implicit session =>

     sql"""select #$columns from #$t_name where id in (#${list.mkString(",")})""".as(SlickResultMap).list

   }
 }
  def sql1={
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      sql"""select id from user""".as(SlickResultString).list
    }
  }
  def createQuery() ={
    val res1 = SlickDBPoolManager.DBPool.withTransaction { implicit session =>
      val res = for {
        u <- User
        if u.username inSet List("guo1", "guo2", "guo3")
        if u.password inSet List("passwd1", "passwd2")
      } yield u.utype
      res.update("utype")
    }
  }
  def createIndexs(request:AppsRequest)={
    SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      val res=for{
        u<-User if u.id > request.start.toLong
      }yield u.id
      val indexs=res.take(request.max).list
      val res1=for{
        u<-User if u.id inSet indexs
      }yield (u.username,u.email,u.bind)
      res1.list.map(t=>s"username=${t._1},email=${t._2},bind=${t._3}")
    }
  }
  def createApps(indexs:List[Long])={
    SlickDBPoolManager.DBPool.withTransaction{implicit  session=>
    }
  }
  val res=Sql
  println(res.length)
  println(Parser.ObjectToJsonString(Sql))
  println(Parser.ObjectToJsonString(sql1))
  insert(null)
  def insert(user:UserRow)={
    val id=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      User returning User.map(_.id) insert(UserRow(-100,"user1","p1","e1","pwss1","status","type","bind","prottype"))
    }
    if(id>0) println(id)
    println(id)
  }
//  println(Parser.ObjectToJsonString(createQuery()))
//  println(Parser.ObjectToJsonString(createIndexs(AppsRequest(None,20,20))))
}
