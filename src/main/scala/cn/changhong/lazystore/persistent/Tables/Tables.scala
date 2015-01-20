package cn.changhong.lazystore.persistent.Tables
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
//object Tables extends {
//  val profile = scala.slick.driver.MySQLDriver
//} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
object Tables {
  val profile: scala.slick.driver.JdbcProfile=scala.slick.driver.MySQLDriver
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = AppApk.ddl ++ AppBase.ddl ++ AppCategory.ddl ++ AppComm.ddl ++ AppDevice.ddl ++ AppDeviceMap.ddl ++ AppDevInfo.ddl ++ AppInfo.ddl ++ AppStat.ddl ++ AppSubCategory.ddl ++ AppSubCatMap.ddl ++ AppSubjectBase.ddl ++ AppSubjectInfo1.ddl ++ AppSubjectInfo2.ddl ++ AppSubjectInfo3.ddl ++ AppSuggest.ddl ++ AppTypeKey.ddl ++ AppTypeName.ddl
  
  /** Entity class storing rows of table AppApk
   *  @param apkId Database column apk_id DBType(INT), AutoInc, PrimaryKey
   *  @param apkName Database column apk_name DBType(VARCHAR), Length(100,true)
   *  @param apkUrl Database column apk_url DBType(VARCHAR), Length(200,true) */
  case class AppApkRow(apkId: Int, apkName: String, apkUrl: String)
  /** GetResult implicit for fetching AppApkRow objects using plain SQL queries */
  implicit def GetResultAppApkRow(implicit e0: GR[Int], e1: GR[String]): GR[AppApkRow] = GR{
    prs => import prs._
    AppApkRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table app_apk. Objects of this class serve as prototypes for rows in queries. */
  class AppApk(_tableTag: Tag) extends Table[AppApkRow](_tableTag, "app_apk") {
    def * = (apkId, apkName, apkUrl) <> (AppApkRow.tupled, AppApkRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (apkId.?, apkName.?, apkUrl.?).shaped.<>({r=>import r._; _1.map(_=> AppApkRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column apk_id DBType(INT), AutoInc, PrimaryKey */
    val apkId: Column[Int] = column[Int]("apk_id", O.AutoInc, O.PrimaryKey)
    /** Database column apk_name DBType(VARCHAR), Length(100,true) */
    val apkName: Column[String] = column[String]("apk_name", O.Length(100,varying=true))
    /** Database column apk_url DBType(VARCHAR), Length(200,true) */
    val apkUrl: Column[String] = column[String]("apk_url", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table AppApk */
  lazy val AppApk = new TableQuery(tag => new AppApk(tag))
  
  /** Entity class storing rows of table AppBase
   *  @param appId Database column app_id DBType(INT), PrimaryKey
   *  @param appName Database column app_name DBType(VARCHAR), Length(100,true)
   *  @param appIcon Database column app_icon DBType(VARCHAR), Length(500,true)
   *  @param appStar Database column app_star DBType(TINYINT)
   *  @param appBrife Database column app_brife DBType(VARCHAR), Length(200,true)
   *  @param appSize Database column app_size DBType(DECIMAL)
   *  @param appDownload Database column app_download DBType(INT), Default(None)
   *  @param appUpdate Database column app_update DBType(BIGINT) */
  case class AppBaseRow(appId: Int, appName: String, appIcon: String, appStar: Byte, appBrife: String, appSize: scala.math.BigDecimal, appDownload: Option[Int] = None, appUpdate: Long)
  /** GetResult implicit for fetching AppBaseRow objects using plain SQL queries */
  implicit def GetResultAppBaseRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Byte], e3: GR[scala.math.BigDecimal], e4: GR[Option[Int]], e5: GR[Long]): GR[AppBaseRow] = GR{
    prs => import prs._
    AppBaseRow.tupled((<<[Int], <<[String], <<[String], <<[Byte], <<[String], <<[scala.math.BigDecimal], <<?[Int], <<[Long]))
  }
  /** Table description of table app_base. Objects of this class serve as prototypes for rows in queries. */
  class AppBase(_tableTag: Tag) extends Table[AppBaseRow](_tableTag, "app_base") {
    def * = (appId, appName, appIcon, appStar, appBrife, appSize, appDownload, appUpdate) <> (AppBaseRow.tupled, AppBaseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (appId.?, appName.?, appIcon.?, appStar.?, appBrife.?, appSize.?, appDownload, appUpdate.?).shaped.<>({r=>import r._; _1.map(_=> AppBaseRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column app_id DBType(INT), PrimaryKey */
    val appId: Column[Int] = column[Int]("app_id", O.PrimaryKey)
    /** Database column app_name DBType(VARCHAR), Length(100,true) */
    val appName: Column[String] = column[String]("app_name", O.Length(100,varying=true))
    /** Database column app_icon DBType(VARCHAR), Length(500,true) */
    val appIcon: Column[String] = column[String]("app_icon", O.Length(500,varying=true))
    /** Database column app_star DBType(TINYINT) */
    val appStar: Column[Byte] = column[Byte]("app_star")
    /** Database column app_brife DBType(VARCHAR), Length(200,true) */
    val appBrife: Column[String] = column[String]("app_brife", O.Length(200,varying=true))
    /** Database column app_size DBType(DECIMAL) */
    val appSize: Column[scala.math.BigDecimal] = column[scala.math.BigDecimal]("app_size")
    /** Database column app_download DBType(INT), Default(None) */
    val appDownload: Column[Option[Int]] = column[Option[Int]]("app_download", O.Default(None))
    /** Database column app_update DBType(BIGINT) */
    val appUpdate: Column[Long] = column[Long]("app_update")
  }
  /** Collection-like TableQuery object for table AppBase */
  lazy val AppBase = new TableQuery(tag => new AppBase(tag))
  
  /** Entity class storing rows of table AppCategory
   *  @param categoryId Database column category_id DBType(INT), AutoInc, PrimaryKey
   *  @param categoryName Database column category_name DBType(VARCHAR), Length(20,true)
   *  @param categoryDesc Database column category_desc DBType(VARCHAR), Length(400,true)
   *  @param categoryType Database column category_type DBType(TINYINT) */
  case class AppCategoryRow(categoryId: Int, categoryName: String, categoryDesc: String, categoryType: Byte)
  /** GetResult implicit for fetching AppCategoryRow objects using plain SQL queries */
  implicit def GetResultAppCategoryRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Byte]): GR[AppCategoryRow] = GR{
    prs => import prs._
    AppCategoryRow.tupled((<<[Int], <<[String], <<[String], <<[Byte]))
  }
  /** Table description of table app_category. Objects of this class serve as prototypes for rows in queries. */
  class AppCategory(_tableTag: Tag) extends Table[AppCategoryRow](_tableTag, "app_category") {
    def * = (categoryId, categoryName, categoryDesc, categoryType) <> (AppCategoryRow.tupled, AppCategoryRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (categoryId.?, categoryName.?, categoryDesc.?, categoryType.?).shaped.<>({r=>import r._; _1.map(_=> AppCategoryRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column category_id DBType(INT), AutoInc, PrimaryKey */
    val categoryId: Column[Int] = column[Int]("category_id", O.AutoInc, O.PrimaryKey)
    /** Database column category_name DBType(VARCHAR), Length(20,true) */
    val categoryName: Column[String] = column[String]("category_name", O.Length(20,varying=true))
    /** Database column category_desc DBType(VARCHAR), Length(400,true) */
    val categoryDesc: Column[String] = column[String]("category_desc", O.Length(400,varying=true))
    /** Database column category_type DBType(TINYINT) */
    val categoryType: Column[Byte] = column[Byte]("category_type")
  }
  /** Collection-like TableQuery object for table AppCategory */
  lazy val AppCategory = new TableQuery(tag => new AppCategory(tag))
  
  /** Entity class storing rows of table AppComm
   *  @param commId Database column comm_id DBType(INT), AutoInc, PrimaryKey
   *  @param appId Database column app_id DBType(INT)
   *  @param appVerId Database column app_ver_id DBType(INT)
   *  @param deviceId Database column device_id DBType(INT)
   *  @param comment Database column comment DBType(VARCHAR), Length(500,true)
   *  @param appStar Database column app_star DBType(TINYINT)
   *  @param commUpload Database column comm_upload DBType(BIGINT), Default(None) */
  case class AppCommRow(commId: Int, appId: Int, appVerId: Int, deviceId: Int, comment: String, appStar: Byte, commUpload: Option[Long] = None)
  /** GetResult implicit for fetching AppCommRow objects using plain SQL queries */
  implicit def GetResultAppCommRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Byte], e3: GR[Option[Long]]): GR[AppCommRow] = GR{
    prs => import prs._
    AppCommRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[String], <<[Byte], <<?[Long]))
  }
  /** Table description of table app_comm. Objects of this class serve as prototypes for rows in queries. */
  class AppComm(_tableTag: Tag) extends Table[AppCommRow](_tableTag, "app_comm") {
    def * = (commId, appId, appVerId, deviceId, comment, appStar, commUpload) <> (AppCommRow.tupled, AppCommRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (commId.?, appId.?, appVerId.?, deviceId.?, comment.?, appStar.?, commUpload).shaped.<>({r=>import r._; _1.map(_=> AppCommRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column comm_id DBType(INT), AutoInc, PrimaryKey */
    val commId: Column[Int] = column[Int]("comm_id", O.AutoInc, O.PrimaryKey)
    /** Database column app_id DBType(INT) */
    val appId: Column[Int] = column[Int]("app_id")
    /** Database column app_ver_id DBType(INT) */
    val appVerId: Column[Int] = column[Int]("app_ver_id")
    /** Database column device_id DBType(INT) */
    val deviceId: Column[Int] = column[Int]("device_id")
    /** Database column comment DBType(VARCHAR), Length(500,true) */
    val comment: Column[String] = column[String]("comment", O.Length(500,varying=true))
    /** Database column app_star DBType(TINYINT) */
    val appStar: Column[Byte] = column[Byte]("app_star")
    /** Database column comm_upload DBType(BIGINT), Default(None) */
    val commUpload: Column[Option[Long]] = column[Option[Long]]("comm_upload", O.Default(None))
    
    /** Foreign key referencing AppBase (database name FK_app_comm_app_id) */
    lazy val appBaseFk = foreignKey("FK_app_comm_app_id", appVerId, AppBase)(r => r.appId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppDevice (database name FK_app_comm_device_id) */
    lazy val appDeviceFk = foreignKey("FK_app_comm_device_id", deviceId, AppDevice)(r => r.deviceId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppInfo (database name FK_app_comm_app_ver_id) */
    lazy val appInfoFk = foreignKey("FK_app_comm_app_ver_id", appVerId, AppInfo)(r => r.appVerId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppComm */
  lazy val AppComm = new TableQuery(tag => new AppComm(tag))
  
  /** Entity class storing rows of table AppDevice
   *  @param deviceId Database column device_id DBType(INT), AutoInc, PrimaryKey
   *  @param deviceName Database column device_name DBType(VARCHAR), Length(200,true)
   *  @param deviceType Database column device_type DBType(VARCHAR), Length(20,true)
   *  @param deviceUpload Database column device_upload DBType(BIGINT), Default(None)
   *  @param deviceUpdate Database column device_update DBType(BIGINT), Default(None) */
  case class AppDeviceRow(deviceId: Int, deviceName: String, deviceType: String, deviceUpload: Option[Long] = None, deviceUpdate: Option[Long] = None)
  /** GetResult implicit for fetching AppDeviceRow objects using plain SQL queries */
  implicit def GetResultAppDeviceRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Long]]): GR[AppDeviceRow] = GR{
    prs => import prs._
    AppDeviceRow.tupled((<<[Int], <<[String], <<[String], <<?[Long], <<?[Long]))
  }
  /** Table description of table app_device. Objects of this class serve as prototypes for rows in queries. */
  class AppDevice(_tableTag: Tag) extends Table[AppDeviceRow](_tableTag, "app_device") {
    def * = (deviceId, deviceName, deviceType, deviceUpload, deviceUpdate) <> (AppDeviceRow.tupled, AppDeviceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (deviceId.?, deviceName.?, deviceType.?, deviceUpload, deviceUpdate).shaped.<>({r=>import r._; _1.map(_=> AppDeviceRow.tupled((_1.get, _2.get, _3.get, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column device_id DBType(INT), AutoInc, PrimaryKey */
    val deviceId: Column[Int] = column[Int]("device_id", O.AutoInc, O.PrimaryKey)
    /** Database column device_name DBType(VARCHAR), Length(200,true) */
    val deviceName: Column[String] = column[String]("device_name", O.Length(200,varying=true))
    /** Database column device_type DBType(VARCHAR), Length(20,true) */
    val deviceType: Column[String] = column[String]("device_type", O.Length(20,varying=true))
    /** Database column device_upload DBType(BIGINT), Default(None) */
    val deviceUpload: Column[Option[Long]] = column[Option[Long]]("device_upload", O.Default(None))
    /** Database column device_update DBType(BIGINT), Default(None) */
    val deviceUpdate: Column[Option[Long]] = column[Option[Long]]("device_update", O.Default(None))
  }
  /** Collection-like TableQuery object for table AppDevice */
  lazy val AppDevice = new TableQuery(tag => new AppDevice(tag))
  
  /** Entity class storing rows of table AppDeviceMap
   *  @param deviceId Database column device_id DBType(INT), PrimaryKey
   *  @param appId Database column app_id DBType(INT)
   *  @param appVerId Database column app_ver_id DBType(INT)
   *  @param appDeviceType Database column app_device_type DBType(TINYINT), Default(None)
   *  @param appUpload Database column app_upload DBType(BIGINT) */
  case class AppDeviceMapRow(deviceId: Int, appId: Int, appVerId: Int, appDeviceType: Option[Byte] = None, appUpload: Long)
  /** GetResult implicit for fetching AppDeviceMapRow objects using plain SQL queries */
  implicit def GetResultAppDeviceMapRow(implicit e0: GR[Int], e1: GR[Option[Byte]], e2: GR[Long]): GR[AppDeviceMapRow] = GR{
    prs => import prs._
    AppDeviceMapRow.tupled((<<[Int], <<[Int], <<[Int], <<?[Byte], <<[Long]))
  }
  /** Table description of table app_device_map. Objects of this class serve as prototypes for rows in queries. */
  class AppDeviceMap(_tableTag: Tag) extends Table[AppDeviceMapRow](_tableTag, "app_device_map") {
    def * = (deviceId, appId, appVerId, appDeviceType, appUpload) <> (AppDeviceMapRow.tupled, AppDeviceMapRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (deviceId.?, appId.?, appVerId.?, appDeviceType, appUpload.?).shaped.<>({r=>import r._; _1.map(_=> AppDeviceMapRow.tupled((_1.get, _2.get, _3.get, _4, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column device_id DBType(INT), PrimaryKey */
    val deviceId: Column[Int] = column[Int]("device_id", O.PrimaryKey)
    /** Database column app_id DBType(INT) */
    val appId: Column[Int] = column[Int]("app_id")
    /** Database column app_ver_id DBType(INT) */
    val appVerId: Column[Int] = column[Int]("app_ver_id")
    /** Database column app_device_type DBType(TINYINT), Default(None) */
    val appDeviceType: Column[Option[Byte]] = column[Option[Byte]]("app_device_type", O.Default(None))
    /** Database column app_upload DBType(BIGINT) */
    val appUpload: Column[Long] = column[Long]("app_upload")
    
    /** Foreign key referencing AppBase (database name FK_app_device_map_app_id) */
    lazy val appBaseFk = foreignKey("FK_app_device_map_app_id", appId, AppBase)(r => r.appId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppDevice (database name FK_app_device_map_device_id) */
    lazy val appDeviceFk = foreignKey("FK_app_device_map_device_id", deviceId, AppDevice)(r => r.deviceId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppInfo (database name FK_app_device_map_app_ver_id) */
    lazy val appInfoFk = foreignKey("FK_app_device_map_app_ver_id", appVerId, AppInfo)(r => r.appVerId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppDeviceMap */
  lazy val AppDeviceMap = new TableQuery(tag => new AppDeviceMap(tag))
  
  /** Entity class storing rows of table AppDevInfo
   *  @param devId Database column dev_id DBType(INT), AutoInc, PrimaryKey
   *  @param devName Database column dev_name DBType(VARCHAR), Length(200,true)
   *  @param devSite Database column dev_site DBType(VARCHAR), Length(300,true), Default(None)
   *  @param devConnectInfo Database column dev_connect_info DBType(VARCHAR), Length(2000,true), Default(None) */
  case class AppDevInfoRow(devId: Int, devName: String, devSite: Option[String] = None, devConnectInfo: Option[String] = None)
  /** GetResult implicit for fetching AppDevInfoRow objects using plain SQL queries */
  implicit def GetResultAppDevInfoRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[AppDevInfoRow] = GR{
    prs => import prs._
    AppDevInfoRow.tupled((<<[Int], <<[String], <<?[String], <<?[String]))
  }
  /** Table description of table app_dev_info. Objects of this class serve as prototypes for rows in queries. */
  class AppDevInfo(_tableTag: Tag) extends Table[AppDevInfoRow](_tableTag, "app_dev_info") {
    def * = (devId, devName, devSite, devConnectInfo) <> (AppDevInfoRow.tupled, AppDevInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (devId.?, devName.?, devSite, devConnectInfo).shaped.<>({r=>import r._; _1.map(_=> AppDevInfoRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column dev_id DBType(INT), AutoInc, PrimaryKey */
    val devId: Column[Int] = column[Int]("dev_id", O.AutoInc, O.PrimaryKey)
    /** Database column dev_name DBType(VARCHAR), Length(200,true) */
    val devName: Column[String] = column[String]("dev_name", O.Length(200,varying=true))
    /** Database column dev_site DBType(VARCHAR), Length(300,true), Default(None) */
    val devSite: Column[Option[String]] = column[Option[String]]("dev_site", O.Length(300,varying=true), O.Default(None))
    /** Database column dev_connect_info DBType(VARCHAR), Length(2000,true), Default(None) */
    val devConnectInfo: Column[Option[String]] = column[Option[String]]("dev_connect_info", O.Length(2000,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table AppDevInfo */
  lazy val AppDevInfo = new TableQuery(tag => new AppDevInfo(tag))
  
  /** Entity class storing rows of table AppInfo
   *  @param appVerId Database column app_ver_id DBType(INT), AutoInc, PrimaryKey
   *  @param appId Database column app_id DBType(INT)
   *  @param appVerName Database column app_ver_name DBType(VARCHAR), Length(100,true)
   *  @param appVerIcon Database column app_ver_icon DBType(VARCHAR), Length(500,true)
   *  @param appVerStar Database column app_ver_star DBType(TINYINT)
   *  @param appDownload Database column app_download DBType(VARCHAR), Length(100,true)
   *  @param appLabel Database column app_label DBType(VARCHAR), Length(100,true)
   *  @param appNote Database column app_note DBType(VARCHAR), Length(200,true)
   *  @param appImage Database column app_image DBType(VARCHAR), Length(1000,true)
   *  @param appIntro Database column app_intro DBType(VARCHAR), Length(2000,true)
   *  @param appUpdateDate Database column app_update_date DBType(BIGINT)
   *  @param appVerSize Database column app_ver_size DBType(DECIMAL)
   *  @param appUserVar Database column app_user_var DBType(VARCHAR), Length(20,true)
   *  @param appExpense Database column app_expense DBType(VARCHAR), Length(200,true)
   *  @param devId Database column dev_id DBType(VARCHAR), Length(200,true)
   *  @param appSource Database column app_source DBType(VARCHAR), Length(200,true), Default(None)
   *  @param apkId Database column apk_id DBType(INT)
   *  @param appStatus Database column app_status DBType(TINYINT)
   *  @param appUpload Database column app_upload DBType(BIGINT)
   *  @param appUpdate Database column app_update DBType(BIGINT)
   *  @param appAuthor Database column app_author DBType(INT) */
  case class AppInfoRow(appVerId: Int, appId: Int, appVerName: String, appVerIcon: String, appVerStar: Byte, appDownload: String, appLabel: String, appNote: String, appImage: String, appIntro: String, appUpdateDate: Long, appVerSize: scala.math.BigDecimal, appUserVar: String, appExpense: String, devId: String, appSource: Option[String] = None, apkId: Int, appStatus: Byte, appUpload: Long, appUpdate: Long, appAuthor: Int)
  /** GetResult implicit for fetching AppInfoRow objects using plain SQL queries */
  implicit def GetResultAppInfoRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Byte], e3: GR[Long], e4: GR[scala.math.BigDecimal], e5: GR[Option[String]]): GR[AppInfoRow] = GR{
    prs => import prs._
    AppInfoRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[Byte], <<[String], <<[String], <<[String], <<[String], <<[String], <<[Long], <<[scala.math.BigDecimal], <<[String], <<[String], <<[String], <<?[String], <<[Int], <<[Byte], <<[Long], <<[Long], <<[Int]))
  }
  /** Table description of table app_info. Objects of this class serve as prototypes for rows in queries. */
  class AppInfo(_tableTag: Tag) extends Table[AppInfoRow](_tableTag, "app_info") {
    def * = (appVerId, appId, appVerName, appVerIcon, appVerStar, appDownload, appLabel, appNote, appImage, appIntro, appUpdateDate, appVerSize, appUserVar, appExpense, devId, appSource, apkId, appStatus, appUpload, appUpdate, appAuthor) <> (AppInfoRow.tupled, AppInfoRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (appVerId.?, appId.?, appVerName.?, appVerIcon.?, appVerStar.?, appDownload.?, appLabel.?, appNote.?, appImage.?, appIntro.?, appUpdateDate.?, appVerSize.?, appUserVar.?, appExpense.?, devId.?, appSource, apkId.?, appStatus.?, appUpload.?, appUpdate.?, appAuthor.?).shaped.<>({r=>import r._; _1.map(_=> AppInfoRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16, _17.get, _18.get, _19.get, _20.get, _21.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column app_ver_id DBType(INT), AutoInc, PrimaryKey */
    val appVerId: Column[Int] = column[Int]("app_ver_id", O.AutoInc, O.PrimaryKey)
    /** Database column app_id DBType(INT) */
    val appId: Column[Int] = column[Int]("app_id")
    /** Database column app_ver_name DBType(VARCHAR), Length(100,true) */
    val appVerName: Column[String] = column[String]("app_ver_name", O.Length(100,varying=true))
    /** Database column app_ver_icon DBType(VARCHAR), Length(500,true) */
    val appVerIcon: Column[String] = column[String]("app_ver_icon", O.Length(500,varying=true))
    /** Database column app_ver_star DBType(TINYINT) */
    val appVerStar: Column[Byte] = column[Byte]("app_ver_star")
    /** Database column app_download DBType(VARCHAR), Length(100,true) */
    val appDownload: Column[String] = column[String]("app_download", O.Length(100,varying=true))
    /** Database column app_label DBType(VARCHAR), Length(100,true) */
    val appLabel: Column[String] = column[String]("app_label", O.Length(100,varying=true))
    /** Database column app_note DBType(VARCHAR), Length(200,true) */
    val appNote: Column[String] = column[String]("app_note", O.Length(200,varying=true))
    /** Database column app_image DBType(VARCHAR), Length(1000,true) */
    val appImage: Column[String] = column[String]("app_image", O.Length(1000,varying=true))
    /** Database column app_intro DBType(VARCHAR), Length(2000,true) */
    val appIntro: Column[String] = column[String]("app_intro", O.Length(2000,varying=true))
    /** Database column app_update_date DBType(BIGINT) */
    val appUpdateDate: Column[Long] = column[Long]("app_update_date")
    /** Database column app_ver_size DBType(DECIMAL) */
    val appVerSize: Column[scala.math.BigDecimal] = column[scala.math.BigDecimal]("app_ver_size")
    /** Database column app_user_var DBType(VARCHAR), Length(20,true) */
    val appUserVar: Column[String] = column[String]("app_user_var", O.Length(20,varying=true))
    /** Database column app_expense DBType(VARCHAR), Length(200,true) */
    val appExpense: Column[String] = column[String]("app_expense", O.Length(200,varying=true))
    /** Database column dev_id DBType(VARCHAR), Length(200,true) */
    val devId: Column[String] = column[String]("dev_id", O.Length(200,varying=true))
    /** Database column app_source DBType(VARCHAR), Length(200,true), Default(None) */
    val appSource: Column[Option[String]] = column[Option[String]]("app_source", O.Length(200,varying=true), O.Default(None))
    /** Database column apk_id DBType(INT) */
    val apkId: Column[Int] = column[Int]("apk_id")
    /** Database column app_status DBType(TINYINT) */
    val appStatus: Column[Byte] = column[Byte]("app_status")
    /** Database column app_upload DBType(BIGINT) */
    val appUpload: Column[Long] = column[Long]("app_upload")
    /** Database column app_update DBType(BIGINT) */
    val appUpdate: Column[Long] = column[Long]("app_update")
    /** Database column app_author DBType(INT) */
    val appAuthor: Column[Int] = column[Int]("app_author")
    
    /** Foreign key referencing AppApk (database name FK_app_info_apk_id) */
    lazy val appApkFk = foreignKey("FK_app_info_apk_id", appId, AppApk)(r => r.apkId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppBase (database name FK_app_info_app_id) */
    lazy val appBaseFk = foreignKey("FK_app_info_app_id", appId, AppBase)(r => r.appId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppDevInfo (database name FK_app_info_dev_id) */
    lazy val appDevInfoFk = foreignKey("FK_app_info_dev_id", appId, AppDevInfo)(r => r.devId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppInfo */
  lazy val AppInfo = new TableQuery(tag => new AppInfo(tag))
  
  /** Entity class storing rows of table AppStat
   *  @param deviceId Database column device_id DBType(INT), PrimaryKey
   *  @param appVerId Database column app_ver_id DBType(INT)
   *  @param cpuInfo Database column cpu_info DBType(VARCHAR), Length(200,true), Default(None)
   *  @param memInfo Database column mem_info DBType(VARCHAR), Length(200,true), Default(None)
   *  @param dataInfo Database column data_info DBType(VARCHAR), Length(200,true), Default(None)
   *  @param wifiInfo Database column wifi_info DBType(VARCHAR), Length(200,true), Default(None)
   *  @param batteryInfo Database column battery_info DBType(VARCHAR), Length(200,true), Default(None)
   *  @param statUpload Database column stat_upload DBType(BIGINT), Default(None) */
  case class AppStatRow(deviceId: Int, appVerId: Int, cpuInfo: Option[String] = None, memInfo: Option[String] = None, dataInfo: Option[String] = None, wifiInfo: Option[String] = None, batteryInfo: Option[String] = None, statUpload: Option[Long] = None)
  /** GetResult implicit for fetching AppStatRow objects using plain SQL queries */
  implicit def GetResultAppStatRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Long]]): GR[AppStatRow] = GR{
    prs => import prs._
    AppStatRow.tupled((<<[Int], <<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[Long]))
  }
  /** Table description of table app_stat. Objects of this class serve as prototypes for rows in queries. */
  class AppStat(_tableTag: Tag) extends Table[AppStatRow](_tableTag, "app_stat") {
    def * = (deviceId, appVerId, cpuInfo, memInfo, dataInfo, wifiInfo, batteryInfo, statUpload) <> (AppStatRow.tupled, AppStatRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (deviceId.?, appVerId.?, cpuInfo, memInfo, dataInfo, wifiInfo, batteryInfo, statUpload).shaped.<>({r=>import r._; _1.map(_=> AppStatRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column device_id DBType(INT), PrimaryKey */
    val deviceId: Column[Int] = column[Int]("device_id", O.PrimaryKey)
    /** Database column app_ver_id DBType(INT) */
    val appVerId: Column[Int] = column[Int]("app_ver_id")
    /** Database column cpu_info DBType(VARCHAR), Length(200,true), Default(None) */
    val cpuInfo: Column[Option[String]] = column[Option[String]]("cpu_info", O.Length(200,varying=true), O.Default(None))
    /** Database column mem_info DBType(VARCHAR), Length(200,true), Default(None) */
    val memInfo: Column[Option[String]] = column[Option[String]]("mem_info", O.Length(200,varying=true), O.Default(None))
    /** Database column data_info DBType(VARCHAR), Length(200,true), Default(None) */
    val dataInfo: Column[Option[String]] = column[Option[String]]("data_info", O.Length(200,varying=true), O.Default(None))
    /** Database column wifi_info DBType(VARCHAR), Length(200,true), Default(None) */
    val wifiInfo: Column[Option[String]] = column[Option[String]]("wifi_info", O.Length(200,varying=true), O.Default(None))
    /** Database column battery_info DBType(VARCHAR), Length(200,true), Default(None) */
    val batteryInfo: Column[Option[String]] = column[Option[String]]("battery_info", O.Length(200,varying=true), O.Default(None))
    /** Database column stat_upload DBType(BIGINT), Default(None) */
    val statUpload: Column[Option[Long]] = column[Option[Long]]("stat_upload", O.Default(None))
    
    /** Foreign key referencing AppDevice (database name FK_app_stat_device_id) */
    lazy val appDeviceFk = foreignKey("FK_app_stat_device_id", deviceId, AppDevice)(r => r.deviceId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppInfo (database name FK_app_stat_app_ver_id) */
    lazy val appInfoFk = foreignKey("FK_app_stat_app_ver_id", appVerId, AppInfo)(r => r.appVerId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppStat */
  lazy val AppStat = new TableQuery(tag => new AppStat(tag))
  
  /** Entity class storing rows of table AppSubCategory
   *  @param subCategoryId Database column sub_category_id DBType(INT), AutoInc, PrimaryKey
   *  @param categoryId Database column category_id DBType(INT)
   *  @param subCategoryName Database column sub_category_name DBType(VARCHAR), Length(20,true)
   *  @param subCategoryDesc Database column sub_category_desc DBType(VARCHAR), Length(400,true)
   *  @param subCategoryType Database column sub_category_type DBType(TINYINT) */
  case class AppSubCategoryRow(subCategoryId: Int, categoryId: Int, subCategoryName: String, subCategoryDesc: String, subCategoryType: Byte)
  /** GetResult implicit for fetching AppSubCategoryRow objects using plain SQL queries */
  implicit def GetResultAppSubCategoryRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Byte]): GR[AppSubCategoryRow] = GR{
    prs => import prs._
    AppSubCategoryRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[Byte]))
  }
  /** Table description of table app_sub_category. Objects of this class serve as prototypes for rows in queries. */
  class AppSubCategory(_tableTag: Tag) extends Table[AppSubCategoryRow](_tableTag, "app_sub_category") {
    def * = (subCategoryId, categoryId, subCategoryName, subCategoryDesc, subCategoryType) <> (AppSubCategoryRow.tupled, AppSubCategoryRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (subCategoryId.?, categoryId.?, subCategoryName.?, subCategoryDesc.?, subCategoryType.?).shaped.<>({r=>import r._; _1.map(_=> AppSubCategoryRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column sub_category_id DBType(INT), AutoInc, PrimaryKey */
    val subCategoryId: Column[Int] = column[Int]("sub_category_id", O.AutoInc, O.PrimaryKey)
    /** Database column category_id DBType(INT) */
    val categoryId: Column[Int] = column[Int]("category_id")
    /** Database column sub_category_name DBType(VARCHAR), Length(20,true) */
    val subCategoryName: Column[String] = column[String]("sub_category_name", O.Length(20,varying=true))
    /** Database column sub_category_desc DBType(VARCHAR), Length(400,true) */
    val subCategoryDesc: Column[String] = column[String]("sub_category_desc", O.Length(400,varying=true))
    /** Database column sub_category_type DBType(TINYINT) */
    val subCategoryType: Column[Byte] = column[Byte]("sub_category_type")
    
    /** Foreign key referencing AppCategory (database name FK_app_sub_category_category_id) */
    lazy val appCategoryFk = foreignKey("FK_app_sub_category_category_id", categoryId, AppCategory)(r => r.categoryId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppSubCategory */
  lazy val AppSubCategory = new TableQuery(tag => new AppSubCategory(tag))
  
  /** Entity class storing rows of table AppSubCatMap
   *  @param appId Database column app_id DBType(INT), PrimaryKey
   *  @param appVerId Database column app_ver_id DBType(INT)
   *  @param categoryId Database column category_id DBType(INT)
   *  @param subCategoryId Database column sub_category_id DBType(INT) */
  case class AppSubCatMapRow(appId: Int, appVerId: Int, categoryId: Int, subCategoryId: Int)
  /** GetResult implicit for fetching AppSubCatMapRow objects using plain SQL queries */
  implicit def GetResultAppSubCatMapRow(implicit e0: GR[Int]): GR[AppSubCatMapRow] = GR{
    prs => import prs._
    AppSubCatMapRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table app_sub_cat_map. Objects of this class serve as prototypes for rows in queries. */
  class AppSubCatMap(_tableTag: Tag) extends Table[AppSubCatMapRow](_tableTag, "app_sub_cat_map") {
    def * = (appId, appVerId, categoryId, subCategoryId) <> (AppSubCatMapRow.tupled, AppSubCatMapRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (appId.?, appVerId.?, categoryId.?, subCategoryId.?).shaped.<>({r=>import r._; _1.map(_=> AppSubCatMapRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column app_id DBType(INT), PrimaryKey */
    val appId: Column[Int] = column[Int]("app_id", O.PrimaryKey)
    /** Database column app_ver_id DBType(INT) */
    val appVerId: Column[Int] = column[Int]("app_ver_id")
    /** Database column category_id DBType(INT) */
    val categoryId: Column[Int] = column[Int]("category_id")
    /** Database column sub_category_id DBType(INT) */
    val subCategoryId: Column[Int] = column[Int]("sub_category_id")
    
    /** Foreign key referencing AppBase (database name FK_app_sub_cat_mapp_app_id) */
    lazy val appBaseFk = foreignKey("FK_app_sub_cat_mapp_app_id", appId, AppBase)(r => r.appId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppCategory (database name FK_app_sub_cat_map_category_id) */
    lazy val appCategoryFk = foreignKey("FK_app_sub_cat_map_category_id", categoryId, AppCategory)(r => r.categoryId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppInfo (database name FK_app_sub_cat_map_app_ver_id) */
    lazy val appInfoFk = foreignKey("FK_app_sub_cat_map_app_ver_id", appVerId, AppInfo)(r => r.appVerId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppSubCategory (database name FK_app_sub_cat_map_sub_category_id) */
    lazy val appSubCategoryFk = foreignKey("FK_app_sub_cat_map_sub_category_id", subCategoryId, AppSubCategory)(r => r.subCategoryId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppSubCatMap */
  lazy val AppSubCatMap = new TableQuery(tag => new AppSubCatMap(tag))
  
  /** Entity class storing rows of table AppSubjectBase
   *  @param subjectId Database column subject_id DBType(INT), AutoInc, PrimaryKey
   *  @param subjectName Database column subject_name DBType(VARCHAR), Length(200,true)
   *  @param subjectUrl Database column subject_url DBType(VARCHAR), Length(200,true)
   *  @param subjectType Database column subject_type DBType(TINYINT)
   *  @param subjectStatus Database column subject_status DBType(TINYINT)
   *  @param subjectUpload Database column subject_upload DBType(BIGINT)
   *  @param subjectUpdate Database column subject_update DBType(BIGINT)
   *  @param subjectAuthor Database column subject_author DBType(INT) */
  case class AppSubjectBaseRow(subjectId: Int, subjectName: String, subjectUrl: String, subjectType: Byte, subjectStatus: Byte, subjectUpload: Long, subjectUpdate: Long, subjectAuthor: Int)
  /** GetResult implicit for fetching AppSubjectBaseRow objects using plain SQL queries */
  implicit def GetResultAppSubjectBaseRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Byte], e3: GR[Long]): GR[AppSubjectBaseRow] = GR{
    prs => import prs._
    AppSubjectBaseRow.tupled((<<[Int], <<[String], <<[String], <<[Byte], <<[Byte], <<[Long], <<[Long], <<[Int]))
  }
  /** Table description of table app_subject_base. Objects of this class serve as prototypes for rows in queries. */
  class AppSubjectBase(_tableTag: Tag) extends Table[AppSubjectBaseRow](_tableTag, "app_subject_base") {
    def * = (subjectId, subjectName, subjectUrl, subjectType, subjectStatus, subjectUpload, subjectUpdate, subjectAuthor) <> (AppSubjectBaseRow.tupled, AppSubjectBaseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (subjectId.?, subjectName.?, subjectUrl.?, subjectType.?, subjectStatus.?, subjectUpload.?, subjectUpdate.?, subjectAuthor.?).shaped.<>({r=>import r._; _1.map(_=> AppSubjectBaseRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column subject_id DBType(INT), AutoInc, PrimaryKey */
    val subjectId: Column[Int] = column[Int]("subject_id", O.AutoInc, O.PrimaryKey)
    /** Database column subject_name DBType(VARCHAR), Length(200,true) */
    val subjectName: Column[String] = column[String]("subject_name", O.Length(200,varying=true))
    /** Database column subject_url DBType(VARCHAR), Length(200,true) */
    val subjectUrl: Column[String] = column[String]("subject_url", O.Length(200,varying=true))
    /** Database column subject_type DBType(TINYINT) */
    val subjectType: Column[Byte] = column[Byte]("subject_type")
    /** Database column subject_status DBType(TINYINT) */
    val subjectStatus: Column[Byte] = column[Byte]("subject_status")
    /** Database column subject_upload DBType(BIGINT) */
    val subjectUpload: Column[Long] = column[Long]("subject_upload")
    /** Database column subject_update DBType(BIGINT) */
    val subjectUpdate: Column[Long] = column[Long]("subject_update")
    /** Database column subject_author DBType(INT) */
    val subjectAuthor: Column[Int] = column[Int]("subject_author")
  }
  /** Collection-like TableQuery object for table AppSubjectBase */
  lazy val AppSubjectBase = new TableQuery(tag => new AppSubjectBase(tag))
  
  /** Entity class storing rows of table AppSubjectInfo1
   *  @param sub1Id Database column sub1_id DBType(INT), AutoInc, PrimaryKey
   *  @param subjectId Database column subject_id DBType(INT)
   *  @param appId Database column app_id DBType(INT) */
  case class AppSubjectInfo1Row(sub1Id: Int, subjectId: Int, appId: Int)
  /** GetResult implicit for fetching AppSubjectInfo1Row objects using plain SQL queries */
  implicit def GetResultAppSubjectInfo1Row(implicit e0: GR[Int]): GR[AppSubjectInfo1Row] = GR{
    prs => import prs._
    AppSubjectInfo1Row.tupled((<<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table app_subject_info1. Objects of this class serve as prototypes for rows in queries. */
  class AppSubjectInfo1(_tableTag: Tag) extends Table[AppSubjectInfo1Row](_tableTag, "app_subject_info1") {
    def * = (sub1Id, subjectId, appId) <> (AppSubjectInfo1Row.tupled, AppSubjectInfo1Row.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (sub1Id.?, subjectId.?, appId.?).shaped.<>({r=>import r._; _1.map(_=> AppSubjectInfo1Row.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column sub1_id DBType(INT), AutoInc, PrimaryKey */
    val sub1Id: Column[Int] = column[Int]("sub1_id", O.AutoInc, O.PrimaryKey)
    /** Database column subject_id DBType(INT) */
    val subjectId: Column[Int] = column[Int]("subject_id")
    /** Database column app_id DBType(INT) */
    val appId: Column[Int] = column[Int]("app_id")
    
    /** Foreign key referencing AppBase (database name FK_app_subject_info1_app_id) */
    lazy val appBaseFk = foreignKey("FK_app_subject_info1_app_id", appId, AppBase)(r => r.appId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppSubjectBase (database name FK_app_subject_info1_subject_id) */
    lazy val appSubjectBaseFk = foreignKey("FK_app_subject_info1_subject_id", appId, AppSubjectBase)(r => r.subjectId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppSubjectInfo1 */
  lazy val AppSubjectInfo1 = new TableQuery(tag => new AppSubjectInfo1(tag))
  
  /** Entity class storing rows of table AppSubjectInfo2
   *  @param sub2Id Database column sub2_id DBType(INT), AutoInc, PrimaryKey
   *  @param subjectId Database column subject_id DBType(INT)
   *  @param appId Database column app_id DBType(INT) */
  case class AppSubjectInfo2Row(sub2Id: Int, subjectId: Int, appId: Int)
  /** GetResult implicit for fetching AppSubjectInfo2Row objects using plain SQL queries */
  implicit def GetResultAppSubjectInfo2Row(implicit e0: GR[Int]): GR[AppSubjectInfo2Row] = GR{
    prs => import prs._
    AppSubjectInfo2Row.tupled((<<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table app_subject_info2. Objects of this class serve as prototypes for rows in queries. */
  class AppSubjectInfo2(_tableTag: Tag) extends Table[AppSubjectInfo2Row](_tableTag, "app_subject_info2") {
    def * = (sub2Id, subjectId, appId) <> (AppSubjectInfo2Row.tupled, AppSubjectInfo2Row.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (sub2Id.?, subjectId.?, appId.?).shaped.<>({r=>import r._; _1.map(_=> AppSubjectInfo2Row.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column sub2_id DBType(INT), AutoInc, PrimaryKey */
    val sub2Id: Column[Int] = column[Int]("sub2_id", O.AutoInc, O.PrimaryKey)
    /** Database column subject_id DBType(INT) */
    val subjectId: Column[Int] = column[Int]("subject_id")
    /** Database column app_id DBType(INT) */
    val appId: Column[Int] = column[Int]("app_id")
    
    /** Foreign key referencing AppBase (database name FK_app_subject_info2_app_id) */
    lazy val appBaseFk = foreignKey("FK_app_subject_info2_app_id", appId, AppBase)(r => r.appId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppSubjectBase (database name FK_app_subject_info2_subject_id) */
    lazy val appSubjectBaseFk = foreignKey("FK_app_subject_info2_subject_id", appId, AppSubjectBase)(r => r.subjectId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppSubjectInfo2 */
  lazy val AppSubjectInfo2 = new TableQuery(tag => new AppSubjectInfo2(tag))
  
  /** Entity class storing rows of table AppSubjectInfo3
   *  @param sub3Id Database column sub3_id DBType(INT), AutoInc, PrimaryKey
   *  @param subjectId Database column subject_id DBType(INT)
   *  @param appId Database column app_id DBType(INT) */
  case class AppSubjectInfo3Row(sub3Id: Int, subjectId: Int, appId: Int)
  /** GetResult implicit for fetching AppSubjectInfo3Row objects using plain SQL queries */
  implicit def GetResultAppSubjectInfo3Row(implicit e0: GR[Int]): GR[AppSubjectInfo3Row] = GR{
    prs => import prs._
    AppSubjectInfo3Row.tupled((<<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table app_subject_info3. Objects of this class serve as prototypes for rows in queries. */
  class AppSubjectInfo3(_tableTag: Tag) extends Table[AppSubjectInfo3Row](_tableTag, "app_subject_info3") {
    def * = (sub3Id, subjectId, appId) <> (AppSubjectInfo3Row.tupled, AppSubjectInfo3Row.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (sub3Id.?, subjectId.?, appId.?).shaped.<>({r=>import r._; _1.map(_=> AppSubjectInfo3Row.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column sub3_id DBType(INT), AutoInc, PrimaryKey */
    val sub3Id: Column[Int] = column[Int]("sub3_id", O.AutoInc, O.PrimaryKey)
    /** Database column subject_id DBType(INT) */
    val subjectId: Column[Int] = column[Int]("subject_id")
    /** Database column app_id DBType(INT) */
    val appId: Column[Int] = column[Int]("app_id")
    
    /** Foreign key referencing AppBase (database name FK_app_subject_info3_app_id) */
    lazy val appBaseFk = foreignKey("FK_app_subject_info3_app_id", appId, AppBase)(r => r.appId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing AppSubjectBase (database name FK_app_subject_info3_subject_id) */
    lazy val appSubjectBaseFk = foreignKey("FK_app_subject_info3_subject_id", appId, AppSubjectBase)(r => r.subjectId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppSubjectInfo3 */
  lazy val AppSubjectInfo3 = new TableQuery(tag => new AppSubjectInfo3(tag))
  
  /** Entity class storing rows of table AppSuggest
   *  @param suggestId Database column suggest_id DBType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param deviceId Database column device_id DBType(INT)
   *  @param userSuggest Database column user_suggest DBType(VARCHAR), Length(400,true)
   *  @param userContact Database column user_contact DBType(VARCHAR), Length(50,true), Default(None)
   *  @param suggestUpload Database column suggest_upload DBType(BIGINT)
   *  @param suggestStatus Database column suggest_status DBType(TINYINT) */
  case class AppSuggestRow(suggestId: Int, deviceId: Int, userSuggest: String, userContact: Option[String] = None, suggestUpload: Long, suggestStatus: Byte)
  /** GetResult implicit for fetching AppSuggestRow objects using plain SQL queries */
  implicit def GetResultAppSuggestRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[Long], e4: GR[Byte]): GR[AppSuggestRow] = GR{
    prs => import prs._
    AppSuggestRow.tupled((<<[Int], <<[Int], <<[String], <<?[String], <<[Long], <<[Byte]))
  }
  /** Table description of table app_suggest. Objects of this class serve as prototypes for rows in queries. */
  class AppSuggest(_tableTag: Tag) extends Table[AppSuggestRow](_tableTag, "app_suggest") {
    def * = (suggestId, deviceId, userSuggest, userContact, suggestUpload, suggestStatus) <> (AppSuggestRow.tupled, AppSuggestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (suggestId.?, deviceId.?, userSuggest.?, userContact, suggestUpload.?, suggestStatus.?).shaped.<>({r=>import r._; _1.map(_=> AppSuggestRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column suggest_id DBType(INT UNSIGNED), AutoInc, PrimaryKey */
    val suggestId: Column[Int] = column[Int]("suggest_id", O.AutoInc, O.PrimaryKey)
    /** Database column device_id DBType(INT) */
    val deviceId: Column[Int] = column[Int]("device_id")
    /** Database column user_suggest DBType(VARCHAR), Length(400,true) */
    val userSuggest: Column[String] = column[String]("user_suggest", O.Length(400,varying=true))
    /** Database column user_contact DBType(VARCHAR), Length(50,true), Default(None) */
    val userContact: Column[Option[String]] = column[Option[String]]("user_contact", O.Length(50,varying=true), O.Default(None))
    /** Database column suggest_upload DBType(BIGINT) */
    val suggestUpload: Column[Long] = column[Long]("suggest_upload")
    /** Database column suggest_status DBType(TINYINT) */
    val suggestStatus: Column[Byte] = column[Byte]("suggest_status")
    
    /** Foreign key referencing AppDevice (database name FK_app_suggest_device_id) */
    lazy val appDeviceFk = foreignKey("FK_app_suggest_device_id", deviceId, AppDevice)(r => r.deviceId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AppSuggest */
  lazy val AppSuggest = new TableQuery(tag => new AppSuggest(tag))
  
  /** Entity class storing rows of table AppTypeKey
   *  @param typeParId Database column type_par_id DBType(INT)
   *  @param typeCheId Database column type_che_id DBType(INT) */
  case class AppTypeKeyRow(typeParId: Int, typeCheId: Int)
  /** GetResult implicit for fetching AppTypeKeyRow objects using plain SQL queries */
  implicit def GetResultAppTypeKeyRow(implicit e0: GR[Int]): GR[AppTypeKeyRow] = GR{
    prs => import prs._
    AppTypeKeyRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table app_type_key. Objects of this class serve as prototypes for rows in queries. */
  class AppTypeKey(_tableTag: Tag) extends Table[AppTypeKeyRow](_tableTag, "app_type_key") {
    def * = (typeParId, typeCheId) <> (AppTypeKeyRow.tupled, AppTypeKeyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (typeParId.?, typeCheId.?).shaped.<>({r=>import r._; _1.map(_=> AppTypeKeyRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column type_par_id DBType(INT) */
    val typeParId: Column[Int] = column[Int]("type_par_id")
    /** Database column type_che_id DBType(INT) */
    val typeCheId: Column[Int] = column[Int]("type_che_id")
  }
  /** Collection-like TableQuery object for table AppTypeKey */
  lazy val AppTypeKey = new TableQuery(tag => new AppTypeKey(tag))
  
  /** Entity class storing rows of table AppTypeName
   *  @param typeKeyId Database column type_key_id DBType(INT), AutoInc, PrimaryKey
   *  @param typeName Database column type_name DBType(INT)
   *  @param typeNameComm Database column type_name_comm DBType(VARCHAR), Length(100,true), Default(None) */
  case class AppTypeNameRow(typeKeyId: Int, typeName: Int, typeNameComm: Option[String] = None)
  /** GetResult implicit for fetching AppTypeNameRow objects using plain SQL queries */
  implicit def GetResultAppTypeNameRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[AppTypeNameRow] = GR{
    prs => import prs._
    AppTypeNameRow.tupled((<<[Int], <<[Int], <<?[String]))
  }
  /** Table description of table app_type_name. Objects of this class serve as prototypes for rows in queries. */
  class AppTypeName(_tableTag: Tag) extends Table[AppTypeNameRow](_tableTag, "app_type_name") {
    def * = (typeKeyId, typeName, typeNameComm) <> (AppTypeNameRow.tupled, AppTypeNameRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (typeKeyId.?, typeName.?, typeNameComm).shaped.<>({r=>import r._; _1.map(_=> AppTypeNameRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column type_key_id DBType(INT), AutoInc, PrimaryKey */
    val typeKeyId: Column[Int] = column[Int]("type_key_id", O.AutoInc, O.PrimaryKey)
    /** Database column type_name DBType(INT) */
    val typeName: Column[Int] = column[Int]("type_name")
    /** Database column type_name_comm DBType(VARCHAR), Length(100,true), Default(None) */
    val typeNameComm: Column[Option[String]] = column[Option[String]]("type_name_comm", O.Length(100,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table AppTypeName */
  lazy val AppTypeName = new TableQuery(tag => new AppTypeName(tag))
}