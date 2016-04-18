import java.util.Date

import backend.lazystore.persistent.T.Tables.{Appcategories, AppcategoriesRow}
import backend.lazystore.persistent.dao.{SlickResultInt, SlickResultMap}
import backend.lazystore.spider.wandoujia.SpliderAllClassifed
import backend.base.persistent.SlickDBPoolManager
import backend.base.util.{ExecutorProvider, Parser}
import com.twitter.util.Future
import org.slf4j.LoggerFactory
import org.specs2.mutable.Specification

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.{StaticQuery => Q}
import Q.interpolation
import scala.collection.mutable.Map
/**
 * Created  on 15-2-11.
 */
class TestWandoujiaCategoriesSpecs   extends Specification {
  "ServiceConvariantDemoSpecs OK" >> {
    allWandoujiaCategories()

    true
  }
  def allWandoujiaCategories()= {
    val creation = new Date().getTime()
    def insertCategoriesRow(crow: Seq[AppcategoriesRow]) = {
      SlickDBPoolManager.DBPool.withSession { implicit session =>
        crow.foreach { row =>
          try {
            Appcategories.insert(row)
          }catch {
            case ex:Throwable=>ex.printStackTrace()
          }
        }
      }
    }
    val lazyadmin_id = 1423548742269L
    val status = "release"
    val atype = "wandoujia.type"
    val icon = "unkown"
    val prows=SpliderAllClassifed().map { cls =>
      val description = cls.childs.slice(0, 4).foldLeft("")(_ + " " + _.name)
      val crows = cls.childs.map { cl =>
        val name = cl.name
        val alias = name
        val parent = cls.parent.name
        AppcategoriesRow(-1, name, alias, atype, icon, Some(""), lazyadmin_id, creation, status, parent)
      }
      insertCategoriesRow(crows)
      AppcategoriesRow(-1, cls.parent.name, cls.parent.name, atype, icon, Some(description), lazyadmin_id, creation, status, "0")
    }
    insertCategoriesRow(prows)
  }
  def allTagSortJob()={
    val res = SlickDBPoolManager.DBPool.withSession { implicit session =>
      sql"select distinct tag from apptags".as(SlickResultMap).list
    }
    val futureTask=res.map{tags=>tags.getOrElse("tag","unkown").toString}.map{tag=>
      ExecutorProvider.futurePool{
        val res=SlickDBPoolManager.DBPool.withSession{implicit session=>
          val sql=s"select count(1) from apptags where tag='${tag}'"
          sql"#$sql".as(SlickResultInt).list
        }
        (tag->res(0))
      }
    }
    Future.collect(futureTask) onSuccess{tags=>
      val sortSeq=tags.sortBy(-_._2)
      println(Parser.ObjectToJsonString(sortSeq))
      System.exit(0)
    }onFailure{
      case ex:Throwable=>
        ex.printStackTrace()
    }
  }
}
