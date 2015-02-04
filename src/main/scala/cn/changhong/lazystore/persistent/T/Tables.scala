package cn.changhong.lazystore.persistent.T
/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
object Tables {
  val profile: scala.slick.driver.JdbcProfile=scala.slick.driver.MySQLDriver
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  import scala.slick.collection.heterogenous._
  import scala.slick.collection.heterogenous.syntax._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = Appcategories.ddl ++ Appdev.ddl ++ Apppkg.ddl ++ Apptags.ddl ++ Lazyadmin.ddl ++ Lazyapp.ddl ++ Lazyappstats.ddl ++ Lazyhistorytopic.ddl ++ Lazytopic.ddl ++ UAppcomments.ddl ++ UApps.ddl ++ UAppstats.ddl ++ UDevice.ddl ++ VLazyappTags.ddl
  
  /** Entity class storing rows of table Appcategories
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param name Database column name DBType(VARCHAR), Length(50,true)
   *  @param alias Database column alias DBType(VARCHAR), Length(50,true)
   *  @param `type` Database column type DBType(VARCHAR), Length(20,true)
   *  @param icon Database column icon DBType(VARCHAR), Length(50,true)
   *  @param description Database column description DBType(VARCHAR), Length(150,true), Default(None)
   *  @param lazyadminId Database column lazyadmin_id DBType(BIGINT UNSIGNED)
   *  @param creation Database column creation DBType(BIGINT UNSIGNED)
   *  @param status Database column status DBType(VARCHAR), Length(20,true)
   *  @param parent Database column parent DBType(VARCHAR), Length(50,true) */
  case class AppcategoriesRow(id: Long, name: String, alias: String, `type`: String, icon: String, description: Option[String] = None, lazyadminId: Long, creation: Long, status: String, parent: String)
  /** GetResult implicit for fetching AppcategoriesRow objects using plain SQL queries */
  implicit def GetResultAppcategoriesRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]]): GR[AppcategoriesRow] = GR{
    prs => import prs._
    AppcategoriesRow.tupled((<<[Long], <<[String], <<[String], <<[String], <<[String], <<?[String], <<[Long], <<[Long], <<[String], <<[String]))
  }
  /** Table description of table AppCategories. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class Appcategories(_tableTag: Tag) extends Table[AppcategoriesRow](_tableTag, "AppCategories") {
    def * = (id, name, alias, `type`, icon, description, lazyadminId, creation, status, parent) <> (AppcategoriesRow.tupled, AppcategoriesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, name.?, alias.?, `type`.?, icon.?, description, lazyadminId.?, creation.?, status.?, parent.?).shaped.<>({r=>import r._; _1.map(_=> AppcategoriesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name DBType(VARCHAR), Length(50,true) */
    val name: Column[String] = column[String]("name", O.Length(50,varying=true))
    /** Database column alias DBType(VARCHAR), Length(50,true) */
    val alias: Column[String] = column[String]("alias", O.Length(50,varying=true))
    /** Database column type DBType(VARCHAR), Length(20,true)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Column[String] = column[String]("type", O.Length(20,varying=true))
    /** Database column icon DBType(VARCHAR), Length(50,true) */
    val icon: Column[String] = column[String]("icon", O.Length(50,varying=true))
    /** Database column description DBType(VARCHAR), Length(150,true), Default(None) */
    val description: Column[Option[String]] = column[Option[String]]("description", O.Length(150,varying=true), O.Default(None))
    /** Database column lazyadmin_id DBType(BIGINT UNSIGNED) */
    val lazyadminId: Column[Long] = column[Long]("lazyadmin_id")
    /** Database column creation DBType(BIGINT UNSIGNED) */
    val creation: Column[Long] = column[Long]("creation")
    /** Database column status DBType(VARCHAR), Length(20,true) */
    val status: Column[String] = column[String]("status", O.Length(20,varying=true))
    /** Database column parent DBType(VARCHAR), Length(50,true) */
    val parent: Column[String] = column[String]("parent", O.Length(50,varying=true))
    
    /** Uniqueness Index over (name) (database name uk_AppCategoriese_phone) */
    val index1 = index("uk_AppCategoriese_phone", name, unique=true)
  }
  /** Collection-like TableQuery object for table Appcategories */
  lazy val Appcategories = new TableQuery(tag => new Appcategories(tag))
  
  /** Entity class storing rows of table Appdev
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param company Database column company DBType(VARCHAR), Length(50,true), Default(None)
   *  @param passwd Database column passwd DBType(VARCHAR), Length(50,true), Default(None)
   *  @param email Database column email DBType(VARCHAR), Length(50,true), Default(None)
   *  @param website Database column website DBType(VARCHAR), Length(100,true), Default(None)
   *  @param weibo Database column weibo DBType(VARCHAR), Length(50,true), Default(None)
   *  @param weixi Database column weixi DBType(VARCHAR), Length(50,true), Default(None)
   *  @param intro Database column intro DBType(VARCHAR), Length(250,true), Default(None)
   *  @param verified Database column verified DBType(VARCHAR), Length(25,true), Default(None) */
  case class AppdevRow(id: Long, company: Option[String] = None, passwd: Option[String] = None, email: Option[String] = None, website: Option[String] = None, weibo: Option[String] = None, weixi: Option[String] = None, intro: Option[String] = None, verified: Option[String] = None)
  /** GetResult implicit for fetching AppdevRow objects using plain SQL queries */
  implicit def GetResultAppdevRow(implicit e0: GR[Long], e1: GR[Option[String]]): GR[AppdevRow] = GR{
    prs => import prs._
    AppdevRow.tupled((<<[Long], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table Appdev. Objects of this class serve as prototypes for rows in queries. */
  class Appdev(_tableTag: Tag) extends Table[AppdevRow](_tableTag, "Appdev") {
    def * = (id, company, passwd, email, website, weibo, weixi, intro, verified) <> (AppdevRow.tupled, AppdevRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, company, passwd, email, website, weibo, weixi, intro, verified).shaped.<>({r=>import r._; _1.map(_=> AppdevRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column company DBType(VARCHAR), Length(50,true), Default(None) */
    val company: Column[Option[String]] = column[Option[String]]("company", O.Length(50,varying=true), O.Default(None))
    /** Database column passwd DBType(VARCHAR), Length(50,true), Default(None) */
    val passwd: Column[Option[String]] = column[Option[String]]("passwd", O.Length(50,varying=true), O.Default(None))
    /** Database column email DBType(VARCHAR), Length(50,true), Default(None) */
    val email: Column[Option[String]] = column[Option[String]]("email", O.Length(50,varying=true), O.Default(None))
    /** Database column website DBType(VARCHAR), Length(100,true), Default(None) */
    val website: Column[Option[String]] = column[Option[String]]("website", O.Length(100,varying=true), O.Default(None))
    /** Database column weibo DBType(VARCHAR), Length(50,true), Default(None) */
    val weibo: Column[Option[String]] = column[Option[String]]("weibo", O.Length(50,varying=true), O.Default(None))
    /** Database column weixi DBType(VARCHAR), Length(50,true), Default(None) */
    val weixi: Column[Option[String]] = column[Option[String]]("weixi", O.Length(50,varying=true), O.Default(None))
    /** Database column intro DBType(VARCHAR), Length(250,true), Default(None) */
    val intro: Column[Option[String]] = column[Option[String]]("intro", O.Length(250,varying=true), O.Default(None))
    /** Database column verified DBType(VARCHAR), Length(25,true), Default(None) */
    val verified: Column[Option[String]] = column[Option[String]]("verified", O.Length(25,varying=true), O.Default(None))
    
    /** Uniqueness Index over (company) (database name uk_Appdev_company) */
    val index1 = index("uk_Appdev_company", company, unique=true)
    /** Uniqueness Index over (email) (database name uk_Appdev_email) */
    val index2 = index("uk_Appdev_email", email, unique=true)
    /** Uniqueness Index over (intro) (database name uk_Appdev_intro) */
    val index3 = index("uk_Appdev_intro", intro, unique=true)
    /** Uniqueness Index over (website) (database name uk_Appdev_website) */
    val index4 = index("uk_Appdev_website", website, unique=true)
    /** Uniqueness Index over (weibo) (database name uk_Appdev_weibo) */
    val index5 = index("uk_Appdev_weibo", weibo, unique=true)
    /** Uniqueness Index over (weixi) (database name uk_Appdev_weixi) */
    val index6 = index("uk_Appdev_weixi", weixi, unique=true)
  }
  /** Collection-like TableQuery object for table Appdev */
  lazy val Appdev = new TableQuery(tag => new Appdev(tag))
  
  /** Row type of table Apppkg */
  type ApppkgRow = HCons[Long,HCons[Long,HCons[String,HCons[Double,HCons[Long,HCons[String,HCons[String,HCons[Int,HCons[String,HCons[Option[String],HCons[Option[String],HCons[Int,HCons[Int,HCons[Int,HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[Long],HCons[Option[String],HCons[Long,HCons[Option[String],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HCons[Option[Int],HNil]]]]]]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for ApppkgRow providing default values if available in the database schema. */
  def ApppkgRow(id: Long, lazyappId: Long, downloadurls: String, pkgsize: Double, creation: Long, md5: String, source: String, devcode: Int, versioncode: String, changelogs: Option[String] = None, screenshots: Option[String] = None, maxsdkversion: Int, minsdkversion: Int, targetsdkversion: Int, dangerouspermissions: Option[String] = None, permissionlevel: Option[String] = None, permissionstatement: Option[String] = None, securitydetail: Option[String] = None, securitystatus: Option[String] = None, publishdate: Option[Long] = None, adminmsg: Option[String] = None, lazyadminId: Long, pkgstatus: Option[String] = None, downloadcount: Option[Int] = None, installedcount: Option[Int] = None, dislikescount: Option[Int] = None, likescount: Option[Int] = None, commentcount: Option[Int] = None): ApppkgRow = {
    id :: lazyappId :: downloadurls :: pkgsize :: creation :: md5 :: source :: devcode :: versioncode :: changelogs :: screenshots :: maxsdkversion :: minsdkversion :: targetsdkversion :: dangerouspermissions :: permissionlevel :: permissionstatement :: securitydetail :: securitystatus :: publishdate :: adminmsg :: lazyadminId :: pkgstatus :: downloadcount :: installedcount :: dislikescount :: likescount :: commentcount :: HNil
  }
  /** GetResult implicit for fetching ApppkgRow objects using plain SQL queries */
  implicit def GetResultApppkgRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Double], e3: GR[Int], e4: GR[Option[String]], e5: GR[Option[Long]], e6: GR[Option[Int]]): GR[ApppkgRow] = GR{
    prs => import prs._
    <<[Long] :: <<[Long] :: <<[String] :: <<[Double] :: <<[Long] :: <<[String] :: <<[String] :: <<[Int] :: <<[String] :: <<?[String] :: <<?[String] :: <<[Int] :: <<[Int] :: <<[Int] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[Long] :: <<?[String] :: <<[Long] :: <<?[String] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: <<?[Int] :: HNil
  }
  /** Table description of table Apppkg. Objects of this class serve as prototypes for rows in queries. */
  class Apppkg(_tableTag: Tag) extends Table[ApppkgRow](_tableTag, "Apppkg") {
    def * = id :: lazyappId :: downloadurls :: pkgsize :: creation :: md5 :: source :: devcode :: versioncode :: changelogs :: screenshots :: maxsdkversion :: minsdkversion :: targetsdkversion :: dangerouspermissions :: permissionlevel :: permissionstatement :: securitydetail :: securitystatus :: publishdate :: adminmsg :: lazyadminId :: pkgstatus :: downloadcount :: installedcount :: dislikescount :: likescount :: commentcount :: HNil
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column lazyapp_id DBType(BIGINT UNSIGNED) */
    val lazyappId: Column[Long] = column[Long]("lazyapp_id")
    /** Database column downloadurls DBType(VARCHAR), Length(200,true) */
    val downloadurls: Column[String] = column[String]("downloadurls", O.Length(200,varying=true))
    /** Database column pkgsize DBType(DOUBLE) */
    val pkgsize: Column[Double] = column[Double]("pkgsize")
    /** Database column creation DBType(BIGINT) */
    val creation: Column[Long] = column[Long]("creation")
    /** Database column md5 DBType(VARCHAR), Length(50,true) */
    val md5: Column[String] = column[String]("md5", O.Length(50,varying=true))
    /** Database column source DBType(VARCHAR), Length(100,true) */
    val source: Column[String] = column[String]("source", O.Length(100,varying=true))
    /** Database column devcode DBType(INT) */
    val devcode: Column[Int] = column[Int]("devcode")
    /** Database column versioncode DBType(VARCHAR), Length(25,true) */
    val versioncode: Column[String] = column[String]("versioncode", O.Length(25,varying=true))
    /** Database column changelogs DBType(VARCHAR), Length(250,true), Default(None) */
    val changelogs: Column[Option[String]] = column[Option[String]]("changelogs", O.Length(250,varying=true), O.Default(None))
    /** Database column screenshots DBType(VARCHAR), Length(400,true), Default(None) */
    val screenshots: Column[Option[String]] = column[Option[String]]("screenshots", O.Length(400,varying=true), O.Default(None))
    /** Database column maxsdkversion DBType(INT) */
    val maxsdkversion: Column[Int] = column[Int]("maxsdkversion")
    /** Database column minsdkversion DBType(INT) */
    val minsdkversion: Column[Int] = column[Int]("minsdkversion")
    /** Database column targetsdkversion DBType(INT) */
    val targetsdkversion: Column[Int] = column[Int]("targetsdkversion")
    /** Database column dangerousPermissions DBType(VARCHAR), Length(250,true), Default(None) */
    val dangerouspermissions: Column[Option[String]] = column[Option[String]]("dangerousPermissions", O.Length(250,varying=true), O.Default(None))
    /** Database column permissionLevel DBType(VARCHAR), Length(20,true), Default(None) */
    val permissionlevel: Column[Option[String]] = column[Option[String]]("permissionLevel", O.Length(20,varying=true), O.Default(None))
    /** Database column permissionStatement DBType(VARCHAR), Length(20,true), Default(None) */
    val permissionstatement: Column[Option[String]] = column[Option[String]]("permissionStatement", O.Length(20,varying=true), O.Default(None))
    /** Database column securityDetail DBType(VARCHAR), Length(200,true), Default(None) */
    val securitydetail: Column[Option[String]] = column[Option[String]]("securityDetail", O.Length(200,varying=true), O.Default(None))
    /** Database column securitystatus DBType(VARCHAR), Length(25,true), Default(None) */
    val securitystatus: Column[Option[String]] = column[Option[String]]("securitystatus", O.Length(25,varying=true), O.Default(None))
    /** Database column publishDate DBType(BIGINT), Default(None) */
    val publishdate: Column[Option[Long]] = column[Option[Long]]("publishDate", O.Default(None))
    /** Database column adminmsg DBType(VARCHAR), Length(250,true), Default(None) */
    val adminmsg: Column[Option[String]] = column[Option[String]]("adminmsg", O.Length(250,varying=true), O.Default(None))
    /** Database column lazyadmin_id DBType(BIGINT) */
    val lazyadminId: Column[Long] = column[Long]("lazyadmin_id")
    /** Database column pkgstatus DBType(VARCHAR), Length(25,true), Default(None) */
    val pkgstatus: Column[Option[String]] = column[Option[String]]("pkgstatus", O.Length(25,varying=true), O.Default(None))
    /** Database column downloadcount DBType(INT), Default(None) */
    val downloadcount: Column[Option[Int]] = column[Option[Int]]("downloadcount", O.Default(None))
    /** Database column installedcount DBType(INT), Default(None) */
    val installedcount: Column[Option[Int]] = column[Option[Int]]("installedcount", O.Default(None))
    /** Database column dislikesCount DBType(INT), Default(None) */
    val dislikescount: Column[Option[Int]] = column[Option[Int]]("dislikesCount", O.Default(None))
    /** Database column likesCount DBType(INT), Default(None) */
    val likescount: Column[Option[Int]] = column[Option[Int]]("likesCount", O.Default(None))
    /** Database column commentCount DBType(INT), Default(None) */
    val commentcount: Column[Option[Int]] = column[Option[Int]]("commentCount", O.Default(None))
    
    /** Foreign key referencing Lazyapp (database name fk_lazyapp_id) */
    lazy val lazyappFk = foreignKey("fk_lazyapp_id", lazyappId :: HNil, Lazyapp)(r => r.id :: HNil, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Apppkg */
  lazy val Apppkg = new TableQuery(tag => new Apppkg(tag))
  
  /** Entity class storing rows of table Apptags
   *  @param apppkgId Database column apppkg_id DBType(BIGINT UNSIGNED)
   *  @param appcategoriesName Database column appcategories_name DBType(VARCHAR), Length(25,true)
   *  @param weigth Database column weigth DBType(INT) */
  case class ApptagsRow(apppkgId: Long, appcategoriesName: String, weigth: Int)
  /** GetResult implicit for fetching ApptagsRow objects using plain SQL queries */
  implicit def GetResultApptagsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Int]): GR[ApptagsRow] = GR{
    prs => import prs._
    ApptagsRow.tupled((<<[Long], <<[String], <<[Int]))
  }
  /** Table description of table Apptags. Objects of this class serve as prototypes for rows in queries. */
  class Apptags(_tableTag: Tag) extends Table[ApptagsRow](_tableTag, "Apptags") {
    def * = (apppkgId, appcategoriesName, weigth) <> (ApptagsRow.tupled, ApptagsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (apppkgId.?, appcategoriesName.?, weigth.?).shaped.<>({r=>import r._; _1.map(_=> ApptagsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column apppkg_id DBType(BIGINT UNSIGNED) */
    val apppkgId: Column[Long] = column[Long]("apppkg_id")
    /** Database column appcategories_name DBType(VARCHAR), Length(25,true) */
    val appcategoriesName: Column[String] = column[String]("appcategories_name", O.Length(25,varying=true))
    /** Database column weigth DBType(INT) */
    val weigth: Column[Int] = column[Int]("weigth")
    
    /** Foreign key referencing Lazyapp (database name fk_apppkg_id) */
    lazy val lazyappFk = foreignKey("fk_apppkg_id", apppkgId, Lazyapp)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Apptags */
  lazy val Apptags = new TableQuery(tag => new Apptags(tag))
  
  /** Entity class storing rows of table Lazyadmin
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param role Database column role DBType(VARCHAR), Length(20,true)
   *  @param name Database column name DBType(VARCHAR), Length(20,true)
   *  @param passwd Database column passwd DBType(VARCHAR), Length(50,true)
   *  @param email Database column email DBType(VARCHAR), Length(50,true) */
  case class LazyadminRow(id: Long, role: String, name: String, passwd: String, email: String)
  /** GetResult implicit for fetching LazyadminRow objects using plain SQL queries */
  implicit def GetResultLazyadminRow(implicit e0: GR[Long], e1: GR[String]): GR[LazyadminRow] = GR{
    prs => import prs._
    LazyadminRow.tupled((<<[Long], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table LazyAdmin. Objects of this class serve as prototypes for rows in queries. */
  class Lazyadmin(_tableTag: Tag) extends Table[LazyadminRow](_tableTag, "LazyAdmin") {
    def * = (id, role, name, passwd, email) <> (LazyadminRow.tupled, LazyadminRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, role.?, name.?, passwd.?, email.?).shaped.<>({r=>import r._; _1.map(_=> LazyadminRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column role DBType(VARCHAR), Length(20,true) */
    val role: Column[String] = column[String]("role", O.Length(20,varying=true))
    /** Database column name DBType(VARCHAR), Length(20,true) */
    val name: Column[String] = column[String]("name", O.Length(20,varying=true))
    /** Database column passwd DBType(VARCHAR), Length(50,true) */
    val passwd: Column[String] = column[String]("passwd", O.Length(50,varying=true))
    /** Database column email DBType(VARCHAR), Length(50,true) */
    val email: Column[String] = column[String]("email", O.Length(50,varying=true))
  }
  /** Collection-like TableQuery object for table Lazyadmin */
  lazy val Lazyadmin = new TableQuery(tag => new Lazyadmin(tag))
  
  /** Entity class storing rows of table Lazyapp
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param lastApppkgId Database column last_apppkg_id DBType(BIGINT UNSIGNED)
   *  @param appdevId Database column appdev_id DBType(BIGINT UNSIGNED)
   *  @param packagename Database column packagename DBType(VARCHAR), Length(100,true)
   *  @param title Database column title DBType(VARCHAR), Length(30,true)
   *  @param icon Database column icon DBType(VARCHAR), Length(100,true)
   *  @param desc Database column desc DBType(VARCHAR), Length(250,true)
   *  @param creation Database column creation DBType(BIGINT)
   *  @param apptype Database column apptype DBType(VARCHAR), Length(25,true)
   *  @param status Database column status DBType(VARCHAR), Length(25,true)
   *  @param downloadcount Database column downloadcount DBType(INT), Default(None)
   *  @param installedcount Database column installedcount DBType(INT), Default(None)
   *  @param commentcount Database column commentcount DBType(INT), Default(None)
   *  @param speitysort Database column speitysort DBType(INT), Default(None)
   *  @param topsort Database column topsort DBType(INT), Default(None)
   *  @param hotsort Database column hotsort DBType(INT), Default(None)
   *  @param othersort Database column othersort DBType(INT), Default(None)
   *  @param updateddate Database column updatedDate DBType(BIGINT) */
  case class LazyappRow(id: Long, lastApppkgId: Long, appdevId: Long, packagename: String, title: String, icon: String, desc: String, creation: Long, apptype: String, status: String, downloadcount: Option[Int] = None, installedcount: Option[Int] = None, commentcount: Option[Int] = None, speitysort: Option[Int] = None, topsort: Option[Int] = None, hotsort: Option[Int] = None, othersort: Option[Int] = None, updateddate: Long)
  /** GetResult implicit for fetching LazyappRow objects using plain SQL queries */
  implicit def GetResultLazyappRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[Int]]): GR[LazyappRow] = GR{
    prs => import prs._
    LazyappRow.tupled((<<[Long], <<[Long], <<[Long], <<[String], <<[String], <<[String], <<[String], <<[Long], <<[String], <<[String], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<[Long]))
  }
  /** Table description of table Lazyapp. Objects of this class serve as prototypes for rows in queries. */
  class Lazyapp(_tableTag: Tag) extends Table[LazyappRow](_tableTag, "Lazyapp") {
    def * = (id, lastApppkgId, appdevId, packagename, title, icon, desc, creation, apptype, status, downloadcount, installedcount, commentcount, speitysort, topsort, hotsort, othersort, updateddate) <> (LazyappRow.tupled, LazyappRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, lastApppkgId.?, appdevId.?, packagename.?, title.?, icon.?, desc.?, creation.?, apptype.?, status.?, downloadcount, installedcount, commentcount, speitysort, topsort, hotsort, othersort, updateddate.?).shaped.<>({r=>import r._; _1.map(_=> LazyappRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11, _12, _13, _14, _15, _16, _17, _18.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column last_apppkg_id DBType(BIGINT UNSIGNED) */
    val lastApppkgId: Column[Long] = column[Long]("last_apppkg_id")
    /** Database column appdev_id DBType(BIGINT UNSIGNED) */
    val appdevId: Column[Long] = column[Long]("appdev_id")
    /** Database column packagename DBType(VARCHAR), Length(100,true) */
    val packagename: Column[String] = column[String]("packagename", O.Length(100,varying=true))
    /** Database column title DBType(VARCHAR), Length(30,true) */
    val title: Column[String] = column[String]("title", O.Length(30,varying=true))
    /** Database column icon DBType(VARCHAR), Length(100,true) */
    val icon: Column[String] = column[String]("icon", O.Length(100,varying=true))
    /** Database column desc DBType(VARCHAR), Length(250,true) */
    val desc: Column[String] = column[String]("desc", O.Length(250,varying=true))
    /** Database column creation DBType(BIGINT) */
    val creation: Column[Long] = column[Long]("creation")
    /** Database column apptype DBType(VARCHAR), Length(25,true) */
    val apptype: Column[String] = column[String]("apptype", O.Length(25,varying=true))
    /** Database column status DBType(VARCHAR), Length(25,true) */
    val status: Column[String] = column[String]("status", O.Length(25,varying=true))
    /** Database column downloadcount DBType(INT), Default(None) */
    val downloadcount: Column[Option[Int]] = column[Option[Int]]("downloadcount", O.Default(None))
    /** Database column installedcount DBType(INT), Default(None) */
    val installedcount: Column[Option[Int]] = column[Option[Int]]("installedcount", O.Default(None))
    /** Database column commentcount DBType(INT), Default(None) */
    val commentcount: Column[Option[Int]] = column[Option[Int]]("commentcount", O.Default(None))
    /** Database column speitysort DBType(INT), Default(None) */
    val speitysort: Column[Option[Int]] = column[Option[Int]]("speitysort", O.Default(None))
    /** Database column topsort DBType(INT), Default(None) */
    val topsort: Column[Option[Int]] = column[Option[Int]]("topsort", O.Default(None))
    /** Database column hotsort DBType(INT), Default(None) */
    val hotsort: Column[Option[Int]] = column[Option[Int]]("hotsort", O.Default(None))
    /** Database column othersort DBType(INT), Default(None) */
    val othersort: Column[Option[Int]] = column[Option[Int]]("othersort", O.Default(None))
    /** Database column updatedDate DBType(BIGINT) */
    val updateddate: Column[Long] = column[Long]("updatedDate")
    
    /** Foreign key referencing Appdev (database name fk_appdev_id) */
    lazy val appdevFk = foreignKey("fk_appdev_id", appdevId, Appdev)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Apppkg (database name fk_last_apppkg_id) */
    lazy val apppkgFk = foreignKey("fk_last_apppkg_id", lastApppkgId, Apppkg)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    
    /** Uniqueness Index over (packagename) (database name uk_Lazyapp_packagename) */
    val index1 = index("uk_Lazyapp_packagename", packagename, unique=true)
    /** Uniqueness Index over (title) (database name uk_Lazyapp_title) */
    val index2 = index("uk_Lazyapp_title", title, unique=true)
  }
  /** Collection-like TableQuery object for table Lazyapp */
  lazy val Lazyapp = new TableQuery(tag => new Lazyapp(tag))
  
  /** Entity class storing rows of table Lazyappstats
   *  @param packagename Database column packagename DBType(VARCHAR), PrimaryKey, Length(100,true)
   *  @param statsdate Database column statsDate DBType(BIGINT UNSIGNED)
   *  @param cpu Database column cpu DBType(DOUBLE)
   *  @param mem Database column mem DBType(DOUBLE)
   *  @param traffic Database column traffic DBType(DOUBLE)
   *  @param battery Database column battery DBType(DOUBLE)
   *  @param frequency Database column frequency DBType(INT)
   *  @param statstype Database column statstype DBType(VARCHAR), Length(10,true) */
  case class LazyappstatsRow(packagename: String, statsdate: Long, cpu: Double, mem: Double, traffic: Double, battery: Double, frequency: Int, statstype: String)
  /** GetResult implicit for fetching LazyappstatsRow objects using plain SQL queries */
  implicit def GetResultLazyappstatsRow(implicit e0: GR[String], e1: GR[Long], e2: GR[Double], e3: GR[Int]): GR[LazyappstatsRow] = GR{
    prs => import prs._
    LazyappstatsRow.tupled((<<[String], <<[Long], <<[Double], <<[Double], <<[Double], <<[Double], <<[Int], <<[String]))
  }
  /** Table description of table LazyAppStats. Objects of this class serve as prototypes for rows in queries. */
  class Lazyappstats(_tableTag: Tag) extends Table[LazyappstatsRow](_tableTag, "LazyAppStats") {
    def * = (packagename, statsdate, cpu, mem, traffic, battery, frequency, statstype) <> (LazyappstatsRow.tupled, LazyappstatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (packagename.?, statsdate.?, cpu.?, mem.?, traffic.?, battery.?, frequency.?, statstype.?).shaped.<>({r=>import r._; _1.map(_=> LazyappstatsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column packagename DBType(VARCHAR), PrimaryKey, Length(100,true) */
    val packagename: Column[String] = column[String]("packagename", O.PrimaryKey, O.Length(100,varying=true))
    /** Database column statsDate DBType(BIGINT UNSIGNED) */
    val statsdate: Column[Long] = column[Long]("statsDate")
    /** Database column cpu DBType(DOUBLE) */
    val cpu: Column[Double] = column[Double]("cpu")
    /** Database column mem DBType(DOUBLE) */
    val mem: Column[Double] = column[Double]("mem")
    /** Database column traffic DBType(DOUBLE) */
    val traffic: Column[Double] = column[Double]("traffic")
    /** Database column battery DBType(DOUBLE) */
    val battery: Column[Double] = column[Double]("battery")
    /** Database column frequency DBType(INT) */
    val frequency: Column[Int] = column[Int]("frequency")
    /** Database column statstype DBType(VARCHAR), Length(10,true) */
    val statstype: Column[String] = column[String]("statstype", O.Length(10,varying=true))
  }
  /** Collection-like TableQuery object for table Lazyappstats */
  lazy val Lazyappstats = new TableQuery(tag => new Lazyappstats(tag))
  
  /** Entity class storing rows of table Lazyhistorytopic
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param title Database column title DBType(VARCHAR), Length(30,true)
   *  @param subtitle Database column subtitle DBType(VARCHAR), Length(50,true), Default(None)
   *  @param img Database column img DBType(VARCHAR), Length(100,true)
   *  @param topictype Database column topictype DBType(VARCHAR), Length(10,true)
   *  @param topicposition Database column topicposition DBType(VARCHAR), Length(10,true)
   *  @param creation Database column creation DBType(BIGINT)
   *  @param action Database column action DBType(VARCHAR), Length(150,true)
   *  @param lazyadminId Database column lazyadmin_id DBType(BIGINT UNSIGNED)
   *  @param expired Database column expired DBType(BIGINT UNSIGNED)
   *  @param topicstatus Database column topicstatus DBType(VARCHAR), Length(20,true) */
  case class LazyhistorytopicRow(id: Long, title: String, subtitle: Option[String] = None, img: String, topictype: String, topicposition: String, creation: Long, action: String, lazyadminId: Long, expired: Long, topicstatus: String)
  /** GetResult implicit for fetching LazyhistorytopicRow objects using plain SQL queries */
  implicit def GetResultLazyhistorytopicRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]]): GR[LazyhistorytopicRow] = GR{
    prs => import prs._
    LazyhistorytopicRow.tupled((<<[Long], <<[String], <<?[String], <<[String], <<[String], <<[String], <<[Long], <<[String], <<[Long], <<[Long], <<[String]))
  }
  /** Table description of table LazyHistoryTopic. Objects of this class serve as prototypes for rows in queries. */
  class Lazyhistorytopic(_tableTag: Tag) extends Table[LazyhistorytopicRow](_tableTag, "LazyHistoryTopic") {
    def * = (id, title, subtitle, img, topictype, topicposition, creation, action, lazyadminId, expired, topicstatus) <> (LazyhistorytopicRow.tupled, LazyhistorytopicRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, title.?, subtitle, img.?, topictype.?, topicposition.?, creation.?, action.?, lazyadminId.?, expired.?, topicstatus.?).shaped.<>({r=>import r._; _1.map(_=> LazyhistorytopicRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title DBType(VARCHAR), Length(30,true) */
    val title: Column[String] = column[String]("title", O.Length(30,varying=true))
    /** Database column subtitle DBType(VARCHAR), Length(50,true), Default(None) */
    val subtitle: Column[Option[String]] = column[Option[String]]("subtitle", O.Length(50,varying=true), O.Default(None))
    /** Database column img DBType(VARCHAR), Length(100,true) */
    val img: Column[String] = column[String]("img", O.Length(100,varying=true))
    /** Database column topictype DBType(VARCHAR), Length(10,true) */
    val topictype: Column[String] = column[String]("topictype", O.Length(10,varying=true))
    /** Database column topicposition DBType(VARCHAR), Length(10,true) */
    val topicposition: Column[String] = column[String]("topicposition", O.Length(10,varying=true))
    /** Database column creation DBType(BIGINT) */
    val creation: Column[Long] = column[Long]("creation")
    /** Database column action DBType(VARCHAR), Length(150,true) */
    val action: Column[String] = column[String]("action", O.Length(150,varying=true))
    /** Database column lazyadmin_id DBType(BIGINT UNSIGNED) */
    val lazyadminId: Column[Long] = column[Long]("lazyadmin_id")
    /** Database column expired DBType(BIGINT UNSIGNED) */
    val expired: Column[Long] = column[Long]("expired")
    /** Database column topicstatus DBType(VARCHAR), Length(20,true) */
    val topicstatus: Column[String] = column[String]("topicstatus", O.Length(20,varying=true))
  }
  /** Collection-like TableQuery object for table Lazyhistorytopic */
  lazy val Lazyhistorytopic = new TableQuery(tag => new Lazyhistorytopic(tag))
  
  /** Entity class storing rows of table Lazytopic
   *  @param title Database column title DBType(VARCHAR), Length(30,true)
   *  @param subtitle Database column subtitle DBType(VARCHAR), Length(50,true), Default(None)
   *  @param img Database column img DBType(VARCHAR), Length(100,true)
   *  @param topictype Database column topictype DBType(VARCHAR), Length(10,true)
   *  @param topicposition Database column topicposition DBType(VARCHAR), Length(10,true)
   *  @param creation Database column creation DBType(BIGINT UNSIGNED)
   *  @param action Database column action DBType(VARCHAR), Length(150,true)
   *  @param lazyadminId Database column lazyadmin_id DBType(BIGINT UNSIGNED)
   *  @param expired Database column expired DBType(BIGINT UNSIGNED)
   *  @param topicstatus Database column topicstatus DBType(VARCHAR), Length(20,true)
   *  @param location Database column location DBType(INT) */
  case class LazytopicRow(title: String, subtitle: Option[String] = None, img: String, topictype: String, topicposition: String, var creation: Long, action: String, lazyadminId: Long, expired: Long, topicstatus: String, location: Int)
  /** GetResult implicit for fetching LazytopicRow objects using plain SQL queries */
  implicit def GetResultLazytopicRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[Long], e3: GR[Int]): GR[LazytopicRow] = GR{
    prs => import prs._
    LazytopicRow.tupled((<<[String], <<?[String], <<[String], <<[String], <<[String], <<[Long], <<[String], <<[Long], <<[Long], <<[String], <<[Int]))
  }
  /** Table description of table LazyTopic. Objects of this class serve as prototypes for rows in queries. */
  class Lazytopic(_tableTag: Tag) extends Table[LazytopicRow](_tableTag, "LazyTopic") {
    def * = (title, subtitle, img, topictype, topicposition, creation, action, lazyadminId, expired, topicstatus, location) <> (LazytopicRow.tupled, LazytopicRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (title.?, subtitle, img.?, topictype.?, topicposition.?, creation.?, action.?, lazyadminId.?, expired.?, topicstatus.?, location.?).shaped.<>({r=>import r._; _1.map(_=> LazytopicRow.tupled((_1.get, _2, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column title DBType(VARCHAR), Length(30,true) */
    val title: Column[String] = column[String]("title", O.Length(30,varying=true))
    /** Database column subtitle DBType(VARCHAR), Length(50,true), Default(None) */
    val subtitle: Column[Option[String]] = column[Option[String]]("subtitle", O.Length(50,varying=true), O.Default(None))
    /** Database column img DBType(VARCHAR), Length(100,true) */
    val img: Column[String] = column[String]("img", O.Length(100,varying=true))
    /** Database column topictype DBType(VARCHAR), Length(10,true) */
    val topictype: Column[String] = column[String]("topictype", O.Length(10,varying=true))
    /** Database column topicposition DBType(VARCHAR), Length(10,true) */
    val topicposition: Column[String] = column[String]("topicposition", O.Length(10,varying=true))
    /** Database column creation DBType(BIGINT UNSIGNED) */
    val creation: Column[Long] = column[Long]("creation")
    /** Database column action DBType(VARCHAR), Length(150,true) */
    val action: Column[String] = column[String]("action", O.Length(150,varying=true))
    /** Database column lazyadmin_id DBType(BIGINT UNSIGNED) */
    val lazyadminId: Column[Long] = column[Long]("lazyadmin_id")
    /** Database column expired DBType(BIGINT UNSIGNED) */
    val expired: Column[Long] = column[Long]("expired")
    /** Database column topicstatus DBType(VARCHAR), Length(20,true) */
    val topicstatus: Column[String] = column[String]("topicstatus", O.Length(20,varying=true))
    /** Database column location DBType(INT) */
    val location: Column[Int] = column[Int]("location")
  }
  /** Collection-like TableQuery object for table Lazytopic */
  lazy val Lazytopic = new TableQuery(tag => new Lazytopic(tag))
  
  /** Entity class storing rows of table UAppcomments
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param apppkgId Database column apppkg_id DBType(BIGINT UNSIGNED)
   *  @param deviceId Database column device_id DBType(BIGINT UNSIGNED)
   *  @param comment Database column comment DBType(VARCHAR), Length(250,true)
   *  @param star Database column star DBType(TINYINT)
   *  @param liked Database column liked DBType(TINYINT)
   *  @param commentdate Database column commentDate DBType(BIGINT) */
  case class UAppcommentsRow(var id: Long, apppkgId: Long, deviceId: Long, comment: String, star: Byte, liked: Byte, var commentdate: Long)
  /** GetResult implicit for fetching UAppcommentsRow objects using plain SQL queries */
  implicit def GetResultUAppcommentsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Byte]): GR[UAppcommentsRow] = GR{
    prs => import prs._
    UAppcommentsRow.tupled((<<[Long], <<[Long], <<[Long], <<[String], <<[Byte], <<[Byte], <<[Long]))
  }
  /** Table description of table U_AppComments. Objects of this class serve as prototypes for rows in queries. */
  class UAppcomments(_tableTag: Tag) extends Table[UAppcommentsRow](_tableTag, "U_AppComments") {
    def * = (id, apppkgId, deviceId, comment, star, liked, commentdate) <> (UAppcommentsRow.tupled, UAppcommentsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, apppkgId.?, deviceId.?, comment.?, star.?, liked.?, commentdate.?).shaped.<>({r=>import r._; _1.map(_=> UAppcommentsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column apppkg_id DBType(BIGINT UNSIGNED) */
    val apppkgId: Column[Long] = column[Long]("apppkg_id")
    /** Database column device_id DBType(BIGINT UNSIGNED) */
    val deviceId: Column[Long] = column[Long]("device_id")
    /** Database column comment DBType(VARCHAR), Length(250,true) */
    val comment: Column[String] = column[String]("comment", O.Length(250,varying=true))
    /** Database column star DBType(TINYINT) */
    val star: Column[Byte] = column[Byte]("star")
    /** Database column liked DBType(TINYINT) */
    val liked: Column[Byte] = column[Byte]("liked")
    /** Database column commentDate DBType(BIGINT) */
    val commentdate: Column[Long] = column[Long]("commentDate")
    
    /** Foreign key referencing Appdev (database name fk_device_id) */
    lazy val appdevFk = foreignKey("fk_device_id", deviceId, Appdev)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Apppkg (database name fk_U_AppComments_apppkg_id) */
    lazy val apppkgFk = foreignKey("fk_U_AppComments_apppkg_id", apppkgId, Apppkg)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table UAppcomments */
  lazy val UAppcomments = new TableQuery(tag => new UAppcomments(tag))
  
  /** Entity class storing rows of table UApps
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param packagename Database column packagename DBType(VARCHAR), Length(100,true)
   *  @param title Database column title DBType(VARCHAR), Length(30,true)
   *  @param versioncode Database column versioncode DBType(VARCHAR), Length(25,true)
   *  @param uDeviceId Database column u_device_id DBType(BIGINT UNSIGNED)
   *  @param updatetime Database column updateTime DBType(BIGINT UNSIGNED) */
  case class UAppsRow(id: Long, packagename: String, title: String, versioncode: String, uDeviceId: Long, updatetime: Long)
  /** GetResult implicit for fetching UAppsRow objects using plain SQL queries */
  implicit def GetResultUAppsRow(implicit e0: GR[Long], e1: GR[String]): GR[UAppsRow] = GR{
    prs => import prs._
    UAppsRow.tupled((<<[Long], <<[String], <<[String], <<[String], <<[Long], <<[Long]))
  }
  /** Table description of table U_Apps. Objects of this class serve as prototypes for rows in queries. */
  class UApps(_tableTag: Tag) extends Table[UAppsRow](_tableTag, "U_Apps") {
    def * = (id, packagename, title, versioncode, uDeviceId, updatetime) <> (UAppsRow.tupled, UAppsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, packagename.?, title.?, versioncode.?, uDeviceId.?, updatetime.?).shaped.<>({r=>import r._; _1.map(_=> UAppsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column packagename DBType(VARCHAR), Length(100,true) */
    val packagename: Column[String] = column[String]("packagename", O.Length(100,varying=true))
    /** Database column title DBType(VARCHAR), Length(30,true) */
    val title: Column[String] = column[String]("title", O.Length(30,varying=true))
    /** Database column versioncode DBType(VARCHAR), Length(25,true) */
    val versioncode: Column[String] = column[String]("versioncode", O.Length(25,varying=true))
    /** Database column u_device_id DBType(BIGINT UNSIGNED) */
    val uDeviceId: Column[Long] = column[Long]("u_device_id")
    /** Database column updateTime DBType(BIGINT UNSIGNED) */
    val updatetime: Column[Long] = column[Long]("updateTime")
    
    /** Foreign key referencing UDevice (database name fk_u_device_id) */
    lazy val uDeviceFk = foreignKey("fk_u_device_id", uDeviceId, UDevice)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    
    /** Uniqueness Index over (packagename) (database name uk_packagename) */
    val index1 = index("uk_packagename", packagename, unique=true)
  }
  /** Collection-like TableQuery object for table UApps */
  lazy val UApps = new TableQuery(tag => new UApps(tag))
  
  /** Entity class storing rows of table UAppstats
   *  @param packagename Database column packagename DBType(VARCHAR), Length(100,true)
   *  @param deviceId Database column device_id DBType(BIGINT UNSIGNED)
   *  @param cpu Database column cpu DBType(DOUBLE)
   *  @param mem Database column mem DBType(DOUBLE)
   *  @param traffic Database column traffic DBType(DOUBLE)
   *  @param battery Database column battery DBType(DOUBLE)
   *  @param frequency Database column frequency DBType(INT UNSIGNED)
   *  @param statsdate Database column statsDate DBType(BIGINT) */
  case class UAppstatsRow(packagename: String, deviceId: Long, cpu: Double, mem: Double, traffic: Double, battery: Double, frequency: Int, var statsdate: Long)
  /** GetResult implicit for fetching UAppstatsRow objects using plain SQL queries */
  implicit def GetResultUAppstatsRow(implicit e0: GR[String], e1: GR[Long], e2: GR[Double], e3: GR[Int]): GR[UAppstatsRow] = GR{
    prs => import prs._
    UAppstatsRow.tupled((<<[String], <<[Long], <<[Double], <<[Double], <<[Double], <<[Double], <<[Int], <<[Long]))
  }
  /** Table description of table U_AppStats. Objects of this class serve as prototypes for rows in queries. */
  class UAppstats(_tableTag: Tag) extends Table[UAppstatsRow](_tableTag, "U_AppStats") {
    def * = (packagename, deviceId, cpu, mem, traffic, battery, frequency, statsdate) <> (UAppstatsRow.tupled, UAppstatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (packagename.?, deviceId.?, cpu.?, mem.?, traffic.?, battery.?, frequency.?, statsdate.?).shaped.<>({r=>import r._; _1.map(_=> UAppstatsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column packagename DBType(VARCHAR), Length(100,true) */
    val packagename: Column[String] = column[String]("packagename", O.Length(100,varying=true))
    /** Database column device_id DBType(BIGINT UNSIGNED) */
    val deviceId: Column[Long] = column[Long]("device_id")
    /** Database column cpu DBType(DOUBLE) */
    val cpu: Column[Double] = column[Double]("cpu")
    /** Database column mem DBType(DOUBLE) */
    val mem: Column[Double] = column[Double]("mem")
    /** Database column traffic DBType(DOUBLE) */
    val traffic: Column[Double] = column[Double]("traffic")
    /** Database column battery DBType(DOUBLE) */
    val battery: Column[Double] = column[Double]("battery")
    /** Database column frequency DBType(INT UNSIGNED) */
    val frequency: Column[Int] = column[Int]("frequency")
    /** Database column statsDate DBType(BIGINT) */
    val statsdate: Column[Long] = column[Long]("statsDate")
    
    /** Foreign key referencing Appdev (database name fk_U_AppStats_device_id) */
    lazy val appdevFk = foreignKey("fk_U_AppStats_device_id", deviceId, Appdev)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table UAppstats */
  lazy val UAppstats = new TableQuery(tag => new UAppstats(tag))
  
  /** Entity class storing rows of table UDevice
   *  @param id Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey
   *  @param name Database column name DBType(VARCHAR), Length(50,true)
   *  @param position Database column position DBType(VARCHAR), Length(25,true)
   *  @param registerdate Database column registerDate DBType(BIGINT UNSIGNED)
   *  @param uuid Database column uuid DBType(VARCHAR), Length(50,true)
   *  @param system Database column system DBType(VARCHAR), Length(50,true)
   *  @param producer Database column producer DBType(VARCHAR), Length(50,true)
   *  @param isbind Database column isbind DBType(TINYINT UNSIGNED)
   *  @param phone Database column phone DBType(VARCHAR), Length(20,true)
   *  @param providername Database column providername DBType(VARCHAR), Length(20,true) */
  case class UDeviceRow(var id: Long, name: String, position: String, var registerdate: Long,var  uuid: String, system: String, producer: String, var isbind: Byte, phone: String, providername: String)
  /** GetResult implicit for fetching UDeviceRow objects using plain SQL queries */
  implicit def GetResultUDeviceRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Byte]): GR[UDeviceRow] = GR{
    prs => import prs._
    UDeviceRow.tupled((<<[Long], <<[String], <<[String], <<[Long], <<[String], <<[String], <<[String], <<[Byte], <<[String], <<[String]))
  }
  /** Table description of table U_device. Objects of this class serve as prototypes for rows in queries. */
  class UDevice(_tableTag: Tag) extends Table[UDeviceRow](_tableTag, "U_device") {
    def * = (id, name, position, registerdate, uuid, system, producer, isbind, phone, providername) <> (UDeviceRow.tupled, UDeviceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, name.?, position.?, registerdate.?, uuid.?, system.?, producer.?, isbind.?, phone.?, providername.?).shaped.<>({r=>import r._; _1.map(_=> UDeviceRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name DBType(VARCHAR), Length(50,true) */
    val name: Column[String] = column[String]("name", O.Length(50,varying=true))
    /** Database column position DBType(VARCHAR), Length(25,true) */
    val position: Column[String] = column[String]("position", O.Length(25,varying=true))
    /** Database column registerDate DBType(BIGINT UNSIGNED) */
    val registerdate: Column[Long] = column[Long]("registerDate")
    /** Database column uuid DBType(VARCHAR), Length(50,true) */
    val uuid: Column[String] = column[String]("uuid", O.Length(50,varying=true))
    /** Database column system DBType(VARCHAR), Length(50,true) */
    val system: Column[String] = column[String]("system", O.Length(50,varying=true))
    /** Database column producer DBType(VARCHAR), Length(50,true) */
    val producer: Column[String] = column[String]("producer", O.Length(50,varying=true))
    /** Database column isbind DBType(TINYINT UNSIGNED) */
    val isbind: Column[Byte] = column[Byte]("isbind")
    /** Database column phone DBType(VARCHAR), Length(20,true) */
    val phone: Column[String] = column[String]("phone", O.Length(20,varying=true))
    /** Database column providername DBType(VARCHAR), Length(20,true) */
    val providername: Column[String] = column[String]("providername", O.Length(20,varying=true))
    
    /** Uniqueness Index over (phone) (database name uk_U_device_phone) */
    val index1 = index("uk_U_device_phone", phone, unique=true)
    /** Uniqueness Index over (uuid) (database name uk_U_device_uuid) */
    val index2 = index("uk_U_device_uuid", uuid, unique=true)
  }
  /** Collection-like TableQuery object for table UDevice */
  lazy val UDevice = new TableQuery(tag => new UDevice(tag))
  
  /** Entity class storing rows of table VLazyappTags
   *  @param id Database column id DBType(BIGINT UNSIGNED), Default(0)
   *  @param lastApppkgId Database column last_apppkg_id DBType(BIGINT UNSIGNED)
   *  @param appdevId Database column appdev_id DBType(BIGINT UNSIGNED)
   *  @param packagename Database column packagename DBType(VARCHAR), Length(100,true)
   *  @param title Database column title DBType(VARCHAR), Length(30,true)
   *  @param icon Database column icon DBType(VARCHAR), Length(100,true)
   *  @param desc Database column desc DBType(VARCHAR), Length(250,true)
   *  @param creation Database column creation DBType(BIGINT)
   *  @param apptype Database column apptype DBType(VARCHAR), Length(25,true)
   *  @param status Database column status DBType(VARCHAR), Length(25,true)
   *  @param downloadcount Database column downloadcount DBType(INT), Default(None)
   *  @param installedcount Database column installedcount DBType(INT), Default(None)
   *  @param commentcount Database column commentcount DBType(INT), Default(None)
   *  @param speitysort Database column speitysort DBType(INT), Default(None)
   *  @param topsort Database column topsort DBType(INT), Default(None)
   *  @param hotsort Database column hotsort DBType(INT), Default(None)
   *  @param othersort Database column othersort DBType(INT), Default(None)
   *  @param appcategoriesName Database column appcategories_name DBType(VARCHAR), Length(25,true)
   *  @param weigth Database column weigth DBType(INT) */
  case class VLazyappTagsRow(id: Long = 0L, lastApppkgId: Long, appdevId: Long, packagename: String, title: String, icon: String, desc: String, creation: Long, apptype: String, status: String, downloadcount: Option[Int] = None, installedcount: Option[Int] = None, commentcount: Option[Int] = None, speitysort: Option[Int] = None, topsort: Option[Int] = None, hotsort: Option[Int] = None, othersort: Option[Int] = None, appcategoriesName: String, weigth: Int)
  /** GetResult implicit for fetching VLazyappTagsRow objects using plain SQL queries */
  implicit def GetResultVLazyappTagsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[Int]], e3: GR[Int]): GR[VLazyappTagsRow] = GR{
    prs => import prs._
    VLazyappTagsRow.tupled((<<[Long], <<[Long], <<[Long], <<[String], <<[String], <<[String], <<[String], <<[Long], <<[String], <<[String], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<[String], <<[Int]))
  }
  /** Table description of table V_Lazyapp_Tags. Objects of this class serve as prototypes for rows in queries. */
  class VLazyappTags(_tableTag: Tag) extends Table[VLazyappTagsRow](_tableTag, "V_Lazyapp_Tags") {
    def * = (id, lastApppkgId, appdevId, packagename, title, icon, desc, creation, apptype, status, downloadcount, installedcount, commentcount, speitysort, topsort, hotsort, othersort, appcategoriesName, weigth) <> (VLazyappTagsRow.tupled, VLazyappTagsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, lastApppkgId.?, appdevId.?, packagename.?, title.?, icon.?, desc.?, creation.?, apptype.?, status.?, downloadcount, installedcount, commentcount, speitysort, topsort, hotsort, othersort, appcategoriesName.?, weigth.?).shaped.<>({r=>import r._; _1.map(_=> VLazyappTagsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11, _12, _13, _14, _15, _16, _17, _18.get, _19.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT UNSIGNED), Default(0) */
    val id: Column[Long] = column[Long]("id", O.Default(0L))
    /** Database column last_apppkg_id DBType(BIGINT UNSIGNED) */
    val lastApppkgId: Column[Long] = column[Long]("last_apppkg_id")
    /** Database column appdev_id DBType(BIGINT UNSIGNED) */
    val appdevId: Column[Long] = column[Long]("appdev_id")
    /** Database column packagename DBType(VARCHAR), Length(100,true) */
    val packagename: Column[String] = column[String]("packagename", O.Length(100,varying=true))
    /** Database column title DBType(VARCHAR), Length(30,true) */
    val title: Column[String] = column[String]("title", O.Length(30,varying=true))
    /** Database column icon DBType(VARCHAR), Length(100,true) */
    val icon: Column[String] = column[String]("icon", O.Length(100,varying=true))
    /** Database column desc DBType(VARCHAR), Length(250,true) */
    val desc: Column[String] = column[String]("desc", O.Length(250,varying=true))
    /** Database column creation DBType(BIGINT) */
    val creation: Column[Long] = column[Long]("creation")
    /** Database column apptype DBType(VARCHAR), Length(25,true) */
    val apptype: Column[String] = column[String]("apptype", O.Length(25,varying=true))
    /** Database column status DBType(VARCHAR), Length(25,true) */
    val status: Column[String] = column[String]("status", O.Length(25,varying=true))
    /** Database column downloadcount DBType(INT), Default(None) */
    val downloadcount: Column[Option[Int]] = column[Option[Int]]("downloadcount", O.Default(None))
    /** Database column installedcount DBType(INT), Default(None) */
    val installedcount: Column[Option[Int]] = column[Option[Int]]("installedcount", O.Default(None))
    /** Database column commentcount DBType(INT), Default(None) */
    val commentcount: Column[Option[Int]] = column[Option[Int]]("commentcount", O.Default(None))
    /** Database column speitysort DBType(INT), Default(None) */
    val speitysort: Column[Option[Int]] = column[Option[Int]]("speitysort", O.Default(None))
    /** Database column topsort DBType(INT), Default(None) */
    val topsort: Column[Option[Int]] = column[Option[Int]]("topsort", O.Default(None))
    /** Database column hotsort DBType(INT), Default(None) */
    val hotsort: Column[Option[Int]] = column[Option[Int]]("hotsort", O.Default(None))
    /** Database column othersort DBType(INT), Default(None) */
    val othersort: Column[Option[Int]] = column[Option[Int]]("othersort", O.Default(None))
    /** Database column appcategories_name DBType(VARCHAR), Length(25,true) */
    val appcategoriesName: Column[String] = column[String]("appcategories_name", O.Length(25,varying=true))
    /** Database column weigth DBType(INT) */
    val weigth: Column[Int] = column[Int]("weigth")
  }
  /** Collection-like TableQuery object for table VLazyappTags */
  lazy val VLazyappTags = new TableQuery(tag => new VLazyappTags(tag))
}