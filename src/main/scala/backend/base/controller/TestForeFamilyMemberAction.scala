import backend.base.persistent.SlickDBPoolManager
import backend.base.persistent.Tables.Tables._
import backend.base.util.Parser.{FamilyMemberParser, ObjectToJsonString}
import org.specs2.mutable.Specification

/**
  * Created by Administrator on 2016/3/18.
  */

class TestForeFamilyMemberAction  extends Specification {
  "Test OK" >> {

    //
    val user=UserRow(1,"username","phone","email","password","status","uType","bind","proto")
    //    val uid=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
    //      (User returning User.map(_.id)).insert(user)
    //    }
    ////   val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/")
    ////    request.setUri("/username?fid=1&role" +
    //    val t_request=Request()
    //    t_request.setUri("/username?f_id=1&f_role=121")
    //    t_request.headers().set("Client_Id","")
    //    val request=RestRequest(t_request)
    ////    println(request.getUri)
    //    val f_id:Long=request.urlParams.getParam[Long]("f_id") match{
    //      case s:Seq[Long]=>s(0)
    //    }
    //    val f_role:String=request.urlParams.getParam[String]("f_role") match{
    //      case s:Seq[String]=>s(0)
    //    }
    ////    val decoder=new QueryStringDecoder(request.underlying.getUri)
    //    println(f_id+","+f_role)
    //    val res=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
    //      FamilyMember.filter{fm=>fm.role === f_role && fm.userId === 1L}.firstOption
    //    }
    //    println(ObjectToJsonString(res)+">>>")
    ////    val fid=decoder.getParameters.get("fid").get(0).toLong
    //    val res1=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
    //      FamilyMember.filter{fm=>fm.userId === f_id}.list
    //    }
    //   println(ObjectToJsonString(res1))
    val fm=FamilyMemberRow(user.id,Some("male"),Some(27),Some(170),Some(60.5),"bb")
    val json=ObjectToJsonString(fm)
    val fmt=FamilyMemberParser(json)
    fmt.created=new java.util.Date().getTime
    println(fmt.created)
    //    val res=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
    //      FamilyMember.insert(fmt)
    //
    //    }
    //    println(res)
    val ress=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
      //FamilyMember.filter{ fms=> fms.userId===fmt.userId && fms.role===fmt.role }.map(x=>(x.age,x.height,x.sex,x.weight)).update((fmt.age,fmt.height,fmt.sex,fmt.weight))
    }
    println(ress)









    //    val map = Map("userId" -> 1, "created" -> 34324324,"role"->13)
    //    val json=ObjectToJsonString(map)
    //    val family=FamilyMemberParser(json)
    //
    //    val res=SlickDBPoolManager.DBPool.withTransaction{implicit session=>
    //      FamilyMember.insert(family)
    //    }
    //    println(res+","+1)

    true
  }
}
//object Test {
//
//  import net.liftweb.json.DefaultFormats
//  import net.liftweb.json._
//  import net.liftweb.json.Extraction._
//  import net.liftweb.json.Implicits._
//  implicit val format=DefaultFormats
//  var maps:Map[Int,BigInt]=Map()
//  var step:Int=0
//  def main(args: Array[String]): Unit = {
//    //      case class FamilyMemberRow(userId: Long, sex: Option[String] = None, age: Option[Int] = None, created: Long, height: Option[Int] = None, weight: Option[Int] = None)
//    val (value,step)=new Fn()(args(0).toInt)
//
//    println(value+","+step)
//    val map = Map("userId" -> 213, "created" -> 34324324)
//    val json = ObjectToJsonString(map)
//    val familyM=FamilyMemberRow(213,None,None,34324324,None,None)
//    val jsonStr="{\"userId\":213,\"created\":34324324}"
////    println(ObjectToJsonString(familyM))
//    println(json)
////    val familym=parse(ObjectToJsonString(familyM)).extract[FamilyMemberRow]
////    val familym = FamilyMemberParser(json)
//    val familym=FamilyMemberParser(json)
//    println(familym.height + "," + familym.created + "," + familym.age + ","+familym.userId)
//
//  }
//
//}
//class Fn{
//  private[this] var map:Map[Int,BigDecimal]=Map()
//  private[this] var step:Int=0
//  def apply(index:Int):(BigDecimal,Int)={
//    step=0
//    (run(index),step)
//  }
//  private[this] def run(index:Int):BigDecimal={
//    step+=1
//    index match {
//      case 1 | 2 => 1
//      case index if index > 2 =>
//        map.get(index) match {
//          case Some(v) => v
//          case None => {
//            val t = run(index - 1) + run(index - 2)
//            map += (index -> t)
//            t
//          }
//        }
//      case index if index < 0 => throw new IllegalArgumentException("输入参数错误")
//    }
//  }
//
//}