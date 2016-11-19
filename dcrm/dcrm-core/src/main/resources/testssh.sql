/*
Navicat MySQL Data Transfer

Source Server         : LocalMySQL
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : testssh

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-11-05 22:25:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_info
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT 'app名称',
  `decription` varchar(255) DEFAULT '' COMMENT '简要描述',
  `code` varchar(60) NOT NULL DEFAULT '' COMMENT '编码',
  `version` varchar(25) NOT NULL DEFAULT '' COMMENT '版本号',
  `url` varchar(255) DEFAULT '' COMMENT '主页地址',
  `appmd5` varchar(32) NOT NULL DEFAULT '' COMMENT 'app md5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键：id',
  `parent_id` int(32) unsigned NOT NULL COMMENT '父资源id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '资源名称',
  `description` varchar(160) DEFAULT '' COMMENT '资源描述',
  `url` varchar(255) DEFAULT '' COMMENT '链接地址',
  `type` int(1) unsigned zerofill NOT NULL COMMENT '资源类型：0-URL资源，1-组件资源, 2-虚拟根资源',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '标识码',
  `module` varchar(100) NOT NULL DEFAULT '' COMMENT '模块名称',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `last_modifed_by` varchar(50) DEFAULT '' COMMENT '最后修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(25) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(200) DEFAULT '' COMMENT '角色简介',
  `role_type` int(1) unsigned zerofill NOT NULL COMMENT '角色类型：0：普通用户，1：管理员',
  `role_code` varchar(20) NOT NULL DEFAULT '' COMMENT '角色编码：RXXXX（R0001）',
  `related_res_ids` varchar(1600) DEFAULT '' COMMENT '关联资源列表(以'',''分隔)',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `last_modifed_by` varchar(50) DEFAULT '' COMMENT '最后修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键Id',
  `login_name` varchar(30) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` varchar(60) NOT NULL DEFAULT '' COMMENT '密码',
  `true_name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `mobile_phone` char(11) NOT NULL DEFAULT '' COMMENT '手机',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `related_role_ids` varchar(200) DEFAULT '' COMMENT '关联角色id序列(以","分隔)',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别：0：男；1：女',
  `last_login_ip` char(15) DEFAULT '' COMMENT '上次登录IP',
  `current_login_ip` char(15) DEFAULT '' COMMENT '当前登录IP',
  `login_attempt_times` int(16) unsigned DEFAULT NULL COMMENT '登录尝试次数',
  `login_faild_time` timestamp NULL DEFAULT NULL COMMENT '登录失败时间',
  `password_first_modified_flag` int(1) unsigned zerofill NOT NULL COMMENT '是否修改过默认密码：0：未修改，1：已修改',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(50) DEFAULT '' COMMENT '创建人',
  `last_modified_by` varchar(50) DEFAULT '' COMMENT '最后修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
