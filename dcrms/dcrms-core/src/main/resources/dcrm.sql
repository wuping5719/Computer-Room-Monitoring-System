/*
Navicat MySQL Data Transfer

Source Server         : LocalMySQL
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : dcrm

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-11-29 20:23:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for actiontype
-- ----------------------------
DROP TABLE IF EXISTS `actiontype`;
CREATE TABLE `actiontype` (
  `actionId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` char(60) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '动作级别',
  PRIMARY KEY (`actionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for alertrecord
-- ----------------------------
DROP TABLE IF EXISTS `alertrecord`;
CREATE TABLE `alertrecord` (
  `alertId` bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '站点Id',
  `reasonId` int(25) unsigned NOT NULL DEFAULT '0' COMMENT '警报原因Id',
  `alertTime` datetime NOT NULL COMMENT '警报时间',
  `instrumentId` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '报警设备Id',
  PRIMARY KEY (`alertId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `cityId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cityName` char(60) NOT NULL DEFAULT '' COMMENT '城市名',
  `longitude` float(10,0) NOT NULL DEFAULT '0' COMMENT '经度',
  `latitude` float(10,0) NOT NULL DEFAULT '0' COMMENT '纬度',
  `areaCode` varchar(20) NOT NULL DEFAULT '' COMMENT '区号',
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for coordinate
-- ----------------------------
DROP TABLE IF EXISTS `coordinate`;
CREATE TABLE `coordinate` (
  `coordinateId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '站点Id',
  `sensorId` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '传感器Id',
  `coordinateName` char(50) NOT NULL DEFAULT '' COMMENT '坐标系名',
  `unit` char(10) NOT NULL DEFAULT '' COMMENT '单位',
  `upLimit` float(20,0) NOT NULL DEFAULT '0' COMMENT '上限',
  `lowLimit` float(20,0) NOT NULL DEFAULT '0' COMMENT '下限',
  `visible` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '可见性',
  PRIMARY KEY (`coordinateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for instrument
-- ----------------------------
DROP TABLE IF EXISTS `instrument`;
CREATE TABLE `instrument` (
  `insId` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '站点Id',
  `insName` char(60) NOT NULL DEFAULT '' COMMENT '设备名',
  `manufacturer` varchar(120) DEFAULT '' COMMENT '制造商',
  `model` varchar(100) DEFAULT '' COMMENT '型号',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '设备类型',
  `description` varchar(200) DEFAULT '' COMMENT '简介',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '设备状态',
  `visible` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '可见性',
  PRIMARY KEY (`insId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `logId` int(36) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '用户Id',
  `actionId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '动作类型Id',
  `recordTime` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reason
-- ----------------------------
DROP TABLE IF EXISTS `reason`;
CREATE TABLE `reason` (
  `reasonId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '原因介绍',
  `level` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '级别',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '类型',
  PRIMARY KEY (`reasonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `resId` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` char(60) NOT NULL DEFAULT '' COMMENT '资源名称',
  `url` varchar(200) DEFAULT '' COMMENT 'URL',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '资源类型',
  `visible` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否显示',
  `description` varchar(100) DEFAULT '' COMMENT '资源描述',
  PRIMARY KEY (`resId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `roleName` char(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(100) DEFAULT '' COMMENT '角色描述',
  `userSequence` varchar(800) DEFAULT '' COMMENT '关联用户序列',
  `resSequence` varchar(2000) DEFAULT '' COMMENT '关联资源序列',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sensor
-- ----------------------------
DROP TABLE IF EXISTS `sensor`;
CREATE TABLE `sensor` (
  `sensorId` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `insId` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '设备Id',
  `coordinateId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '坐标系Id',
  `sensorName` char(60) NOT NULL DEFAULT '' COMMENT '传感器名称',
  `unit` char(10) NOT NULL DEFAULT '' COMMENT '数据单位',
  `accuracy` float(32,0) NOT NULL DEFAULT '0' COMMENT '数据精度',
  `upperLimit` float(32,0) NOT NULL DEFAULT '0' COMMENT '数据上限',
  `lowerLimit` float(32,0) NOT NULL DEFAULT '0' COMMENT '数据下限',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '传感器类别',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '传感器状态',
  `visible` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '可见性',
  PRIMARY KEY (`sensorId`),
  KEY `sensor_insId` (`insId`),
  CONSTRAINT `sensor_insId` FOREIGN KEY (`insId`) REFERENCES `instrument` (`insId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for site
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site` (
  `siteId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteName` char(30) NOT NULL DEFAULT '' COMMENT '站点名',
  `address` char(80) DEFAULT '' COMMENT '地址',
  `longitude` float(10,0) NOT NULL DEFAULT '0' COMMENT '经度',
  `latitude` float(10,0) NOT NULL DEFAULT '0' COMMENT '纬度',
  `description` varchar(100) DEFAULT '' COMMENT '简介',
  `cityId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '所属城市Id',
  PRIMARY KEY (`siteId`),
  KEY `site_cityId` (`cityId`),
  CONSTRAINT `site_cityId` FOREIGN KEY (`cityId`) REFERENCES `city` (`cityId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sitedata
-- ----------------------------
DROP TABLE IF EXISTS `sitedata`;
CREATE TABLE `sitedata` (
  `dataId` bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gatherTime` datetime NOT NULL COMMENT '采集时间',
  `value` varchar(3600) NOT NULL DEFAULT '' COMMENT '数据值',
  `siteId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '站点Id',
  PRIMARY KEY (`dataId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '站点Id(外键)',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '姓名',
  `userName` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `telephone` char(11) NOT NULL DEFAULT '' COMMENT '电话号码',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '用户类别',
  `roleId` varchar(30) NOT NULL DEFAULT '' COMMENT '关联角色Id',
  `sex` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '性别：0：男；1：女',
  PRIMARY KEY (`userId`),
  KEY `id` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
