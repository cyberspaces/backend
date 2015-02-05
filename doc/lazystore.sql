/*
Navicat MySQL Data Transfer

Source Server         : 10.9.52.31_1
Source Server Version : 50616
Source Host           : 10.9.52.31:13306
Source Database       : lazystore

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2015-02-05 17:07:53
*/
Drop DATABASE IF EXISTS `lazystore`;
Create DATABASE `lazystore` DEFAULT CHARACTER SET utf8;
Use lazystore;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appcategories
-- ----------------------------
DROP TABLE IF EXISTS `appcategories`;
CREATE TABLE `appcategories` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `alias` varchar(50) NOT NULL COMMENT '别名',
  `type` varchar(20) NOT NULL COMMENT '分类类型',
  `icon` varchar(50) NOT NULL COMMENT '图标',
  `description` varchar(150) DEFAULT NULL COMMENT '描述',
  `lazyadmin_id` bigint(20) unsigned NOT NULL COMMENT '分类创建者',
  `creation` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  `status` varchar(20) NOT NULL COMMENT '分类状态',
  `parent` varchar(50) NOT NULL COMMENT '父类别',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_AppCategoriese_phone` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for appdev
-- ----------------------------
DROP TABLE IF EXISTS `appdev`;
CREATE TABLE `appdev` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `company` varchar(50) DEFAULT NULL,
  `passwd` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  `weibo` varchar(50) DEFAULT NULL,
  `weixi` varchar(50) DEFAULT NULL,
  `intro` varchar(250) DEFAULT NULL,
  `verified` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_Appdev_company` (`company`),
  UNIQUE KEY `uk_Appdev_email` (`email`),
  UNIQUE KEY `uk_Appdev_website` (`website`),
  UNIQUE KEY `uk_Appdev_weibo` (`weibo`),
  UNIQUE KEY `uk_Appdev_weixi` (`weixi`),
  UNIQUE KEY `uk_Appdev_intro` (`intro`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='应用对应开发者的信息';

-- ----------------------------
-- Table structure for apppkg
-- ----------------------------
DROP TABLE IF EXISTS `apppkg`;
CREATE TABLE `apppkg` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `lazyapp_id` bigint(20) unsigned NOT NULL,
  `downloadurls` varchar(200) NOT NULL,
  `pkgsize` double(20,0) NOT NULL,
  `creation` bigint(20) NOT NULL,
  `md5` varchar(50) NOT NULL,
  `source` varchar(100) NOT NULL,
  `devcode` int(11) NOT NULL,
  `versioncode` varchar(25) NOT NULL,
  `changelogs` varchar(250) DEFAULT NULL,
  `screenshots` varchar(400) DEFAULT NULL,
  `maxsdkversion` int(11) NOT NULL,
  `minsdkversion` int(11) NOT NULL,
  `targetsdkversion` int(11) NOT NULL,
  `dangerouspermissions` varchar(250) DEFAULT NULL,
  `permissionlevel` varchar(20) DEFAULT NULL,
  `permissionstatement` varchar(20) DEFAULT NULL,
  `securitydetail` varchar(200) DEFAULT NULL,
  `securitystatus` varchar(25) DEFAULT NULL,
  `publishdate` bigint(20) DEFAULT NULL,
  `adminmsg` varchar(250) DEFAULT NULL,
  `lazyadmin_id` bigint(20) unsigned NOT NULL,
  `pkgstatus` varchar(25) DEFAULT NULL,
  `downloadcount` int(11) DEFAULT NULL,
  `installedcount` int(11) DEFAULT NULL,
  `dislikescount` int(11) DEFAULT NULL,
  `likescount` int(11) DEFAULT NULL,
  `commentcount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lazyapp_id` (`lazyapp_id`),
  KEY `fk_LazyAdmin_id` (`lazyadmin_id`),
  CONSTRAINT `fk_LazyAdmin_id` FOREIGN KEY (`lazyadmin_id`) REFERENCES `lazyadmin` (`id`),
  CONSTRAINT `fk_lazyapp_id` FOREIGN KEY (`lazyapp_id`) REFERENCES `lazyapp` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for apptags
-- ----------------------------
DROP TABLE IF EXISTS `apptags`;
CREATE TABLE `apptags` (
  `lazyapp_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(25) NOT NULL,
  `weight` int(1) NOT NULL,
  KEY `fk_apptags_lazyapp_id` (`lazyapp_id`),
  CONSTRAINT `fk_apptags_lazyapp_id` FOREIGN KEY (`lazyapp_id`) REFERENCES `lazyapp` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商城中应用对应的标签信息';

-- ----------------------------
-- Table structure for lazyadmin
-- ----------------------------
DROP TABLE IF EXISTS `lazyadmin`;
CREATE TABLE `lazyadmin` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL COMMENT '角色',
  `name` varchar(20) NOT NULL COMMENT '注册名',
  `passwd` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lazyapp
-- ----------------------------
DROP TABLE IF EXISTS `lazyapp`;
CREATE TABLE `lazyapp` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `last_apppkg_id` bigint(20) unsigned NOT NULL,
  `appdev_id` bigint(20) unsigned NOT NULL,
  `packagename` varchar(100) NOT NULL,
  `title` varchar(30) NOT NULL,
  `icon` varchar(100) NOT NULL,
  `desc` varchar(250) NOT NULL,
  `creation` bigint(20) NOT NULL,
  `apptype` varchar(25) NOT NULL,
  `status` varchar(25) NOT NULL,
  `t_downloadcount` int(11) DEFAULT NULL COMMENT '总下载次数',
  `t_installedcount` int(11) DEFAULT NULL COMMENT '总安装次数',
  `t_commentcount` int(11) DEFAULT NULL COMMENT '总评论总数',
  `speitysort` int(11) DEFAULT NULL,
  `topsort` int(11) DEFAULT NULL,
  `hotsort` int(11) DEFAULT NULL,
  `othersort` int(11) DEFAULT NULL,
  `updateddate` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_Lazyapp_packagename` (`packagename`),
  UNIQUE KEY `uk_Lazyapp_title` (`title`),
  KEY `fk_appdev_id` (`appdev_id`),
  KEY `fk_last_apppkg_id` (`last_apppkg_id`),
  CONSTRAINT `fk_appdev_id` FOREIGN KEY (`appdev_id`) REFERENCES `appdev` (`id`),
  CONSTRAINT `fk_last_apppkg_id` FOREIGN KEY (`last_apppkg_id`) REFERENCES `apppkg` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商城中所有应用程序的包信息';

-- ----------------------------
-- Table structure for lazyappstats
-- ----------------------------
DROP TABLE IF EXISTS `lazyappstats`;
CREATE TABLE `lazyappstats` (
  `packagename` varchar(100) NOT NULL,
  `statsdate` bigint(20) unsigned NOT NULL COMMENT '统计时间',
  `cpu` double NOT NULL COMMENT 'cpu使用情况',
  `mem` double NOT NULL COMMENT '内存使用情况',
  `traffic` double NOT NULL COMMENT '流量使用情况',
  `battery` double NOT NULL COMMENT '电量消耗',
  `frequency` int(11) NOT NULL COMMENT '使用频率',
  `statstype` varchar(10) NOT NULL COMMENT '统计类型 天,周,月,年',
  PRIMARY KEY (`packagename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个APP对应的统计信息';

-- ----------------------------
-- Table structure for lazyhistorytopic
-- ----------------------------
DROP TABLE IF EXISTS `lazyhistorytopic`;
CREATE TABLE `lazyhistorytopic` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '专题id',
  `title` varchar(30) NOT NULL COMMENT '专题主标题',
  `subtitle` varchar(50) DEFAULT NULL COMMENT '专题副标题',
  `img` varchar(100) NOT NULL COMMENT '专题图片',
  `topictype` varchar(10) NOT NULL COMMENT '专题类型',
  `topicposition` varchar(10) NOT NULL COMMENT '专题展示位置',
  `creation` bigint(20) NOT NULL COMMENT '专题创建时间',
  `action` varchar(150) NOT NULL COMMENT '专题动作',
  `lazyadmin_id` bigint(20) unsigned NOT NULL COMMENT '专题编辑者',
  `expired` bigint(20) unsigned NOT NULL COMMENT '专题使用时间范围',
  `topicstatus` varchar(20) NOT NULL COMMENT '专题状态(过期,活动)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史专题信息';

-- ----------------------------
-- Table structure for lazytopic
-- ----------------------------
DROP TABLE IF EXISTS `lazytopic`;
CREATE TABLE `lazytopic` (
  `title` varchar(30) NOT NULL COMMENT '专题主标题',
  `subtitle` varchar(50) DEFAULT NULL COMMENT '专题副标题',
  `img` varchar(100) NOT NULL COMMENT '专题图片',
  `topictype` varchar(10) NOT NULL COMMENT '专题类型',
  `topicposition` varchar(10) NOT NULL COMMENT '专题展示位置',
  `creation` bigint(20) unsigned NOT NULL COMMENT '专题创建时间',
  `action` varchar(150) NOT NULL COMMENT '专题动作',
  `lazyadmin_id` bigint(20) unsigned NOT NULL COMMENT '专题编辑者',
  `expired` bigint(20) unsigned NOT NULL COMMENT '专题使用时间范围',
  `topicstatus` varchar(20) NOT NULL COMMENT '专题状态(过期,活动)',
  `location` int(11) NOT NULL COMMENT '专题位置'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for u_appcomments
-- ----------------------------
DROP TABLE IF EXISTS `u_appcomments`;
CREATE TABLE `u_appcomments` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `apppkg_id` bigint(20) unsigned NOT NULL COMMENT '对应包Id',
  `device_id` bigint(20) unsigned NOT NULL COMMENT '终端Id',
  `comment` varchar(250) NOT NULL COMMENT '评论',
  `star` tinyint(4) NOT NULL COMMENT '星级 1,2,3,4,5',
  `liked` tinyint(4) NOT NULL COMMENT '喜不喜欢 1-喜欢，0-未评论，-1-不喜欢',
  `commentdate` bigint(20) NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`),
  KEY `fk_device_id` (`device_id`),
  KEY `fk_U_AppComments_apppkg_id` (`apppkg_id`),
  CONSTRAINT `fk_device_id` FOREIGN KEY (`device_id`) REFERENCES `appdev` (`id`),
  CONSTRAINT `fk_U_AppComments_apppkg_id` FOREIGN KEY (`apppkg_id`) REFERENCES `apppkg` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端对每个应用的所有评论';

-- ----------------------------
-- Table structure for u_apps
-- ----------------------------
DROP TABLE IF EXISTS `u_apps`;
CREATE TABLE `u_apps` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `packagename` varchar(100) NOT NULL COMMENT '包名',
  `title` varchar(30) NOT NULL COMMENT '名称',
  `versioncode` varchar(25) NOT NULL COMMENT '发布版本 终端获取的版本',
  `u_device_id` bigint(20) unsigned NOT NULL,
  `updatetime` bigint(20) unsigned NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_packagename` (`packagename`),
  KEY `fk_u_device_id` (`u_device_id`),
  CONSTRAINT `fk_u_device_id` FOREIGN KEY (`u_device_id`) REFERENCES `u_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for u_appstats
-- ----------------------------
DROP TABLE IF EXISTS `u_appstats`;
CREATE TABLE `u_appstats` (
  `packagename` varchar(100) NOT NULL COMMENT '包名',
  `device_id` bigint(20) unsigned NOT NULL COMMENT '终端id',
  `cpu` double NOT NULL COMMENT 'cpu使用情况',
  `mem` double NOT NULL COMMENT '内存使用情况',
  `traffic` double NOT NULL COMMENT '流量使用情况 统计当天内的流量使用',
  `battery` double NOT NULL COMMENT '电量消耗',
  `frequency` int(10) unsigned NOT NULL COMMENT '使用频率',
  `statsdate` bigint(20) NOT NULL COMMENT '上传时间 以天为单位',
  KEY `fk_U_AppStats_device_id` (`device_id`),
  CONSTRAINT `fk_U_AppStats_device_id` FOREIGN KEY (`device_id`) REFERENCES `appdev` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='终端上所有应用的统计信息';

-- ----------------------------
-- Table structure for u_device
-- ----------------------------
DROP TABLE IF EXISTS `u_device`;
CREATE TABLE `u_device` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '终端id',
  `name` varchar(50) NOT NULL COMMENT '终端名称',
  `position` varchar(25) NOT NULL COMMENT '注册区域信息',
  `registerdate` bigint(20) unsigned NOT NULL COMMENT '注册时间',
  `uuid` varchar(50) NOT NULL COMMENT '终端识别码 唯一',
  `system` varchar(50) NOT NULL COMMENT '终端系统',
  `producer` varchar(50) NOT NULL COMMENT '终端制造商',
  `isbind` tinyint(3) unsigned NOT NULL COMMENT '是否绑定',
  `phone` varchar(20) NOT NULL COMMENT '终端手机号 唯一',
  `providername` varchar(20) NOT NULL COMMENT '运营商',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_U_device_uuid` (`uuid`),
  UNIQUE KEY `uk_U_device_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='下载并安装lazystore应用商城的终端信息';

-- ----------------------------
-- View structure for v_lazyapp_apppkg
-- ----------------------------
DROP VIEW IF EXISTS `v_lazyapp_apppkg`;
CREATE ALGORITHM=UNDEFINED DEFINER=`appdev`@`%` SQL SECURITY DEFINER VIEW `v_lazyapp_apppkg` AS select `lazyapp`.`id` AS `lazyapp_id`,`lazyapp`.`last_apppkg_id` AS `last_apppkg_id`,`lazyapp`.`appdev_id` AS `appdev_id`,`lazyapp`.`packagename` AS `packagename`,`lazyapp`.`title` AS `title`,`lazyapp`.`icon` AS `icon`,`lazyapp`.`desc` AS `desc`,`lazyapp`.`creation` AS `creation`,`lazyapp`.`apptype` AS `apptype`,`lazyapp`.`status` AS `status`,`lazyapp`.`t_downloadcount` AS `t_downloadcount`,`lazyapp`.`t_installedcount` AS `t_installedcount`,`lazyapp`.`t_commentcount` AS `t_commentcount`,`lazyapp`.`speitysort` AS `speitysort`,`lazyapp`.`topsort` AS `topsort`,`lazyapp`.`hotsort` AS `hotsort`,`lazyapp`.`othersort` AS `othersort`,`lazyapp`.`updateddate` AS `updateddate`,`apppkg`.`id` AS `apppkg_id`,`apppkg`.`downloadurls` AS `downloadurls`,`apppkg`.`pkgsize` AS `pkgsize`,`apppkg`.`creation` AS `apppkg_creation`,`apppkg`.`md5` AS `md5`,`apppkg`.`source` AS `source`,`apppkg`.`devcode` AS `devcode`,`apppkg`.`versioncode` AS `versioncode`,`apppkg`.`changelogs` AS `changelogs`,`apppkg`.`screenshots` AS `screenshots`,`apppkg`.`maxsdkversion` AS `maxsdkversion`,`apppkg`.`minsdkversion` AS `minsdkversion`,`apppkg`.`targetsdkversion` AS `targetsdkversion`,`apppkg`.`dangerouspermissions` AS `dangerouspermissions`,`apppkg`.`permissionlevel` AS `permissionlevel`,`apppkg`.`permissionstatement` AS `permissionstatement`,`apppkg`.`securitydetail` AS `securitydetail`,`apppkg`.`securitystatus` AS `securitystatus`,`apppkg`.`publishdate` AS `publishdate`,`apppkg`.`adminmsg` AS `adminmsg`,`apppkg`.`lazyadmin_id` AS `lazyadmin_id`,`apppkg`.`pkgstatus` AS `pkgstatus`,`apppkg`.`downloadcount` AS `downloadcount`,`apppkg`.`installedcount` AS `installedcount`,`apppkg`.`dislikescount` AS `dislikescount`,`apppkg`.`likescount` AS `likescount`,`apppkg`.`commentcount` AS `commentcount` from (`lazyapp` join `apppkg` on((`lazyapp`.`last_apppkg_id` = `apppkg`.`id`))) ;

-- ----------------------------
-- View structure for v_lazyapp_apppkg_tags
-- ----------------------------
DROP VIEW IF EXISTS `v_lazyapp_apppkg_tags`;
CREATE ALGORITHM=UNDEFINED DEFINER=`appdev`@`%` SQL SECURITY DEFINER VIEW `v_lazyapp_apppkg_tags` AS select `v_lazyapp_apppkg`.`lazyapp_id` AS `lazyapp_id`,`v_lazyapp_apppkg`.`last_apppkg_id` AS `last_apppkg_id`,`v_lazyapp_apppkg`.`appdev_id` AS `appdev_id`,`v_lazyapp_apppkg`.`packagename` AS `packagename`,`v_lazyapp_apppkg`.`title` AS `title`,`v_lazyapp_apppkg`.`icon` AS `icon`,`v_lazyapp_apppkg`.`desc` AS `desc`,`v_lazyapp_apppkg`.`creation` AS `creation`,`v_lazyapp_apppkg`.`apptype` AS `apptype`,`v_lazyapp_apppkg`.`status` AS `status`,`v_lazyapp_apppkg`.`t_downloadcount` AS `t_downloadcount`,`v_lazyapp_apppkg`.`t_installedcount` AS `t_installedcount`,`v_lazyapp_apppkg`.`t_commentcount` AS `t_commentcount`,`v_lazyapp_apppkg`.`speitysort` AS `speitysort`,`v_lazyapp_apppkg`.`topsort` AS `topsort`,`v_lazyapp_apppkg`.`hotsort` AS `hotsort`,`v_lazyapp_apppkg`.`othersort` AS `othersort`,`v_lazyapp_apppkg`.`updateddate` AS `updateddate`,`v_lazyapp_apppkg`.`apppkg_id` AS `apppkg_id`,`v_lazyapp_apppkg`.`downloadurls` AS `downloadurls`,`v_lazyapp_apppkg`.`pkgsize` AS `pkgsize`,`v_lazyapp_apppkg`.`apppkg_creation` AS `apppkg_creation`,`v_lazyapp_apppkg`.`md5` AS `md5`,`v_lazyapp_apppkg`.`source` AS `source`,`v_lazyapp_apppkg`.`devcode` AS `devcode`,`v_lazyapp_apppkg`.`versioncode` AS `versioncode`,`v_lazyapp_apppkg`.`changelogs` AS `changelogs`,`v_lazyapp_apppkg`.`screenshots` AS `screenshots`,`v_lazyapp_apppkg`.`maxsdkversion` AS `maxsdkversion`,`v_lazyapp_apppkg`.`minsdkversion` AS `minsdkversion`,`v_lazyapp_apppkg`.`targetsdkversion` AS `targetsdkversion`,`v_lazyapp_apppkg`.`dangerouspermissions` AS `dangerouspermissions`,`v_lazyapp_apppkg`.`permissionlevel` AS `permissionlevel`,`v_lazyapp_apppkg`.`permissionstatement` AS `permissionstatement`,`v_lazyapp_apppkg`.`securitydetail` AS `securitydetail`,`v_lazyapp_apppkg`.`securitystatus` AS `securitystatus`,`v_lazyapp_apppkg`.`publishdate` AS `publishdate`,`v_lazyapp_apppkg`.`adminmsg` AS `adminmsg`,`v_lazyapp_apppkg`.`lazyadmin_id` AS `lazyadmin_id`,`v_lazyapp_apppkg`.`pkgstatus` AS `pkgstatus`,`v_lazyapp_apppkg`.`downloadcount` AS `downloadcount`,`v_lazyapp_apppkg`.`installedcount` AS `installedcount`,`v_lazyapp_apppkg`.`dislikescount` AS `dislikescount`,`v_lazyapp_apppkg`.`likescount` AS `likescount`,`v_lazyapp_apppkg`.`commentcount` AS `commentcount`,`apptags`.`tag` AS `tag`,`apptags`.`weight` AS `weight` from (`v_lazyapp_apppkg` join `apptags` on((`v_lazyapp_apppkg`.`lazyapp_id` = `apptags`.`lazyapp_id`))) ;

-- ----------------------------
-- View structure for v_lazyapp_tags
-- ----------------------------
DROP VIEW IF EXISTS `v_lazyapp_tags`;
CREATE ALGORITHM=UNDEFINED DEFINER=`appdev`@`%` SQL SECURITY DEFINER VIEW `v_lazyapp_tags` AS select `lazyapp`.`id` AS `lazyapp_id`,`lazyapp`.`last_apppkg_id` AS `last_apppkg_id`,`lazyapp`.`appdev_id` AS `appdev_id`,`lazyapp`.`packagename` AS `packagename`,`lazyapp`.`title` AS `title`,`lazyapp`.`icon` AS `icon`,`lazyapp`.`desc` AS `desc`,`lazyapp`.`creation` AS `creation`,`lazyapp`.`apptype` AS `apptype`,`lazyapp`.`status` AS `status`,`lazyapp`.`t_downloadcount` AS `t_downloadcount`,`lazyapp`.`t_installedcount` AS `t_installedcount`,`lazyapp`.`t_commentcount` AS `t_commentcount`,`lazyapp`.`speitysort` AS `speitysort`,`lazyapp`.`topsort` AS `topsort`,`lazyapp`.`hotsort` AS `hotsort`,`lazyapp`.`othersort` AS `othersort`,`lazyapp`.`updateddate` AS `updateddate`,`apptags`.`tag` AS `tag`,`apptags`.`weight` AS `weight` from (`lazyapp` join `apptags` on((`apptags`.`lazyapp_id` = `lazyapp`.`id`))) ;

SET FOREIGN_KEY_CHECKS=1;