/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.7.20 : Database - equipment
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`equipment` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `equipment`;

/*Table structure for table `tb_xn_equip_equip` */

DROP TABLE IF EXISTS `tb_xn_equip_equip`;

CREATE TABLE `tb_xn_equip_equip` (
  `equip_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `equip_name` varchar(100) NOT NULL COMMENT '设备名称',
  `type_id` int(11) NOT NULL COMMENT '设备类别',
  `equip_price` double NOT NULL COMMENT '设备价格',
  `equip_producer` varchar(100) DEFAULT NULL COMMENT '设备厂家',
  `add_people` varchar(100) NOT NULL COMMENT '添加设备人',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`equip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_xn_equip_equip` */

insert  into `tb_xn_equip_equip`(`equip_id`,`equip_name`,`type_id`,`equip_price`,`equip_producer`,`add_people`,`is_ok`,`create_time`,`update_time`,`remark`) values 
(1,'bb',2,56.34,'fadsf','admin',0,'2018-05-11 18:51:16','2018-05-11 18:55:47','fdasfasd'),
(2,'cc',5,58.69,'xSFD','admin',0,'2018-05-11 18:52:16','2018-05-11 18:55:47','');

/*Table structure for table `tb_xn_equip_type` */

DROP TABLE IF EXISTS `tb_xn_equip_type`;

CREATE TABLE `tb_xn_equip_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备类型ID',
  `type_name` varchar(100) NOT NULL COMMENT '设备类型名',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_xn_equip_type` */

insert  into `tb_xn_equip_type`(`type_id`,`type_name`,`is_ok`,`create_time`,`update_time`,`remark`) values 
(1,'医疗类',0,'2018-05-11 18:06:58','2018-05-11 18:06:58','医疗类'),
(2,'工程类',0,'2018-05-11 18:07:16','2018-05-11 18:07:16','工程类'),
(3,'教育类',0,'2018-05-11 18:07:25','2018-05-11 18:07:25','教育类'),
(4,'娱乐类',0,'2018-05-11 18:07:34','2018-05-11 18:08:31','娱乐类'),
(5,'电子类',0,'2018-05-11 18:07:59','2018-05-11 18:07:59','电子类'),
(6,'学习类',0,'2018-05-11 18:08:43','2018-05-11 18:10:30','学习类'),
(7,'交通类',0,'2018-05-11 18:08:52','2018-05-11 18:10:30','交通类');

/*Table structure for table `tb_xn_sys_module` */

DROP TABLE IF EXISTS `tb_xn_sys_module`;

CREATE TABLE `tb_xn_sys_module` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模块ID',
  `module_name` varchar(50) NOT NULL COMMENT '模块名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级模块ID',
  `module_level` tinyint(1) NOT NULL COMMENT '菜单级别',
  `action_url` varchar(100) DEFAULT NULL COMMENT '访问URL',
  `icon` varchar(100) DEFAULT 'null' COMMENT '菜单图标',
  `is_allowed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可分配(0:可以,1:不可以)',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效(0:有效,1:无效)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`module_id`),
  KEY `isDeleted` (`is_ok`),
  KEY `selected` (`parent_id`,`module_level`,`is_allowed`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `tb_xn_sys_module` */

insert  into `tb_xn_sys_module`(`module_id`,`module_name`,`parent_id`,`module_level`,`action_url`,`icon`,`is_allowed`,`is_ok`,`create_time`,`update_time`,`remark`) values 
(1,'系统管理',0,1,'-','&#xe705;',0,0,'2017-11-28 16:58:17','2018-05-11 16:15:33','系统管理'),
(2,'用户信息',1,2,'/manager/system/user/user-list.jsp','-',0,0,'2017-11-28 16:58:17','2018-05-11 17:12:28','用户信息'),
(3,'角色信息',1,2,'/manager/system/role/role-list.jsp','-',0,0,'2017-11-28 16:58:17','2018-05-11 17:12:35','角色信息'),
(4,'模块信息',1,2,'/manager/system/module/module-list.jsp','-',0,0,'2017-11-28 16:58:17','2018-05-11 17:12:42','模块信息'),
(30,'设备管理',0,1,'-','&#xe756;',1,0,'2018-05-11 17:07:34','2018-05-11 17:09:22','设备管理'),
(31,'设备类别信息',30,2,'/manager/equip/type/type-list.jsp','',0,0,'2018-05-11 17:10:29','2018-05-11 17:12:13','设备类别信息'),
(32,'设备信息',30,2,'/manager/equip/equip/equip-list.jsp','',0,0,'2018-05-11 17:11:28','2018-05-11 17:11:28','设备信息');

/*Table structure for table `tb_xn_sys_role` */

DROP TABLE IF EXISTS `tb_xn_sys_role`;

CREATE TABLE `tb_xn_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`),
  KEY `isDeleted` (`is_ok`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_xn_sys_role` */

insert  into `tb_xn_sys_role`(`role_id`,`role_name`,`is_ok`,`create_time`,`update_time`,`remark`) values 
(1,'超级管理员',0,'2017-11-28 16:47:04','2018-05-11 17:11:36','超级管理员'),
(7,'普通用户',0,'2018-05-11 18:14:09','2018-05-11 18:14:09','普通用户');

/*Table structure for table `tb_xn_sys_role_module` */

DROP TABLE IF EXISTS `tb_xn_sys_role_module`;

CREATE TABLE `tb_xn_sys_role_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `module_id` int(11) NOT NULL COMMENT '模块ID',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效，默认为0代表有效',
  PRIMARY KEY (`id`),
  KEY `isDeleted` (`is_ok`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8;

/*Data for the table `tb_xn_sys_role_module` */

insert  into `tb_xn_sys_role_module`(`id`,`role_id`,`module_id`,`is_ok`) values 
(372,1,1,0),
(373,1,2,0),
(374,1,3,0),
(375,1,4,0),
(376,1,30,0),
(377,1,31,0),
(378,1,32,0),
(379,7,30,0),
(380,7,31,0),
(381,7,32,0);

/*Table structure for table `tb_xn_sys_user` */

DROP TABLE IF EXISTS `tb_xn_sys_user`;

CREATE TABLE `tb_xn_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_name` varchar(50) NOT NULL COMMENT '账号名称',
  `user_pwd` varchar(50) NOT NULL COMMENT '账号密码',
  `user_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态：0：启用，1：禁用',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效：0：未删，1：删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  KEY `isDeleted` (`is_ok`),
  KEY `login` (`user_name`(10),`user_pwd`(10))
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `tb_xn_sys_user` */

insert  into `tb_xn_sys_user`(`user_id`,`role_id`,`user_name`,`user_pwd`,`user_state`,`is_ok`,`create_time`,`update_time`,`remark`) values 
(1,1,'admin','admin',0,0,'2017-11-28 18:06:19','2018-05-11 15:55:32','超级管理员'),
(16,7,'test','test',0,0,'2018-05-11 18:14:23','2018-05-11 18:14:23','测试');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
