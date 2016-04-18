package backend

import backend.lazystore.persistent.dao.SqlProvider

/**
 *  15/11/19.
 */
object DownloadAdd {
  final val stars=List(0,1,-1,2)
  final val addMinStep=3
  final val addMaxStep:List[Int]=(1 to 5).map{n=>n*(System.nanoTime()%5).toInt}.toList
  final val step=50

  def toInt(str:Any,defaultValue:Int=0):Int={
    try{str.toString.toInt}catch{case ex:Throwable=>defaultValue}
  }
  def convertToUpdateSql(map:Map[String,Any]):String={
    val id=map.getOrElse("id","-1").toString
    var installedCount=toInt(map.getOrElse("t_installedcount","0"))
    var downloadCount=toInt(map.getOrElse("t_downloadcount","0"))
    var totalstar=toInt(map.getOrElse("totalstar","1"))
    installedCount+=addMaxStep((System.nanoTime()%5).toInt)
    downloadCount+=addMaxStep((System.nanoTime()%5).toInt)
    totalstar+=stars(downloadCount%4)
    if(downloadCount>10000000) totalstar=Math.max(totalstar,4)
    if(totalstar>5) totalstar=5
    else if(totalstar<=2) totalstar=3
    s"update lazyapp set t_installedcount=$installedCount,t_downloadcount=$downloadCount,totalstar=$totalstar where id=${SqlProvider.transferRequestParams(id)};"
  }
  def updateHandler(start:Int,limit:Int)={
    val sql=s"select t_downloadcount,t_installedcount,totalstar,id from lazyapp limit ${start},$limit"
    val result=SqlProvider.exec(sql)
    if(result!=null&&result.size>0){
      val sqls=result.map{map=>convertToUpdateSql(map)}
      SqlProvider.exec(sqls)
    }
  }

  def getTotalCount():Int={
    SqlProvider.exec1("select count(1) from lazyapp") match {
      case item::list=>item
      case _=> 85848
    }
  }
  def main(args:Array[String]): Unit ={
    val totalCount=getTotalCount()
    val totalNum=totalCount/step
    (0 to (totalNum-1)).foreach{index=>
      updateHandler(index*step,step)
    }
    updateHandler(totalNum*step,totalCount%step)
  }
}
