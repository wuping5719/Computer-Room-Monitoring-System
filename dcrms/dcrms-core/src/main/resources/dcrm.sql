/*
Navicat MySQL Data Transfer

Source Server         : LocalMySQL
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : dcrm

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-03-03 15:44:26
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of actiontype
-- ----------------------------
INSERT INTO `actiontype` VALUES ('1', '取消报警', '0');
INSERT INTO `actiontype` VALUES ('2', '查看站点视频', '0');
INSERT INTO `actiontype` VALUES ('3', '云台控制', '0');
INSERT INTO `actiontype` VALUES ('4', '权限分配', '1');

-- ----------------------------
-- Table structure for alertrecord
-- ----------------------------
DROP TABLE IF EXISTS `alertrecord`;
CREATE TABLE `alertrecord` (
  `alertId` bigint(64) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '站点Id',
  `reasonId` int(25) unsigned NOT NULL DEFAULT '0' COMMENT '警报原因Id',
  `reasonLevel` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '报警级别',
  `alertTime` datetime NOT NULL COMMENT '警报时间',
  `instrumentId` int(32) unsigned NOT NULL DEFAULT '0' COMMENT '报警设备Id',
  `solved` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '报警是否处理:：0(未处理)，1(已处理)',
  PRIMARY KEY (`alertId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alertrecord
-- ----------------------------
INSERT INTO `alertrecord` VALUES ('1', '1', '1', '0', '2017-02-12 14:38:38', '1', '0');
INSERT INTO `alertrecord` VALUES ('2', '1', '5', '2', '2017-02-12 14:53:54', '1', '0');
INSERT INTO `alertrecord` VALUES ('3', '1', '2', '1', '2017-02-12 15:04:02', '1', '0');
INSERT INTO `alertrecord` VALUES ('4', '1', '4', '0', '2017-02-13 20:59:27', '1', '1');
INSERT INTO `alertrecord` VALUES ('5', '1', '3', '1', '2017-02-13 20:59:54', '1', '0');
INSERT INTO `alertrecord` VALUES ('6', '1', '6', '2', '2017-02-08 21:00:14', '1', '1');
INSERT INTO `alertrecord` VALUES ('7', '1', '1', '0', '2017-02-10 21:00:40', '1', '0');
INSERT INTO `alertrecord` VALUES ('8', '1', '2', '0', '2017-02-06 21:01:00', '1', '0');

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `cityId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cityName` char(60) NOT NULL DEFAULT '' COMMENT '城市名',
  `longitude` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '经度',
  `latitude` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '纬度',
  `areaCode` varchar(20) NOT NULL DEFAULT '' COMMENT '区号',
  `siteNum` int(10) unsigned DEFAULT '0' COMMENT '机房站点数目',
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '青岛', '120.00', '36.00', 'C0532', '12');
INSERT INTO `city` VALUES ('2', '北京', '116.00', '40.00', 'C010', '58');
INSERT INTO `city` VALUES ('3', '上海', '121.00', '31.00', 'C021', '60');
INSERT INTO `city` VALUES ('4', '深圳', '114.00', '23.00', 'C0755', '68');
INSERT INTO `city` VALUES ('5', '广州', '113.00', '23.00', 'C020', '46');
INSERT INTO `city` VALUES ('6', '成都', '104.00', '31.00', 'C028', '28');
INSERT INTO `city` VALUES ('7', '重庆', '107.00', '30.00', 'C023', '18');
INSERT INTO `city` VALUES ('8', '沈阳', '124.00', '42.00', 'C024', '12');
INSERT INTO `city` VALUES ('9', '西安', '109.00', '34.00', 'C029', '16');
INSERT INTO `city` VALUES ('10', '长沙', '113.00', '28.00', 'C0731', '8');

-- ----------------------------
-- Table structure for coordinate
-- ----------------------------
DROP TABLE IF EXISTS `coordinate`;
CREATE TABLE `coordinate` (
  `coordinateId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coordinateName` char(50) NOT NULL DEFAULT '' COMMENT '坐标系名',
  `unit` char(10) NOT NULL DEFAULT '' COMMENT '单位',
  `upLimit` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '上限',
  `lowLimit` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '下限',
  `visible` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '可见性',
  PRIMARY KEY (`coordinateId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coordinate
-- ----------------------------
INSERT INTO `coordinate` VALUES ('1', '温度', '℃', '100.00', '-30.00', '0');
INSERT INTO `coordinate` VALUES ('2', '湿度', '%', '100.00', '0.00', '0');
INSERT INTO `coordinate` VALUES ('3', '电压', 'V', '430.00', '0.00', '0');
INSERT INTO `coordinate` VALUES ('4', '电流', 'A', '50.00', '0.00', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of instrument
-- ----------------------------
INSERT INTO `instrument` VALUES ('1', '1', '精密空调PEX1035-1', '艾默生', 'AMSPEX', '0', 'AMSPEX', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logs
-- ----------------------------
INSERT INTO `logs` VALUES ('1', '1', '1', '2017-03-03 15:42:31');
INSERT INTO `logs` VALUES ('2', '2', '2', '2017-03-03 15:42:42');

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reason
-- ----------------------------
INSERT INTO `reason` VALUES ('1', '精密空调C1002回风湿度过高', '0', '1');
INSERT INTO `reason` VALUES ('2', '精密空调C1002模块1压缩机故障', '1', '1');
INSERT INTO `reason` VALUES ('3', '精密空调C1002模块1泵1故障', '1', '1');
INSERT INTO `reason` VALUES ('4', '精密空调C5000送风温度过高', '1', '1');
INSERT INTO `reason` VALUES ('5', '精密空调C5000模块2气流故障', '2', '1');
INSERT INTO `reason` VALUES ('6', '稳压电源交流输入A相相电压告警', '1', '1');
INSERT INTO `reason` VALUES ('7', '烟感告警', '0', '0');
INSERT INTO `reason` VALUES ('8', '火感告警', '0', '0');
INSERT INTO `reason` VALUES ('9', '红外告警', '2', '0');
INSERT INTO `reason` VALUES ('10', '水浸告警', '0', '0');
INSERT INTO `reason` VALUES ('11', '门磁告警', '1', '0');
INSERT INTO `reason` VALUES ('12', 'UPS输出电压过低', '0', '1');

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
  `gmtCreate` datetime DEFAULT NULL COMMENT '创建日期',
  `gmtModified` datetime DEFAULT NULL COMMENT '最后修改日期',
  `parentId` int(32) unsigned NOT NULL COMMENT '父资源Id',
  PRIMARY KEY (`resId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('1', '站点导航', '\\', '2', '0', '站点导航', '2017-02-27 22:51:15', '2017-02-27 22:51:18', '10');
INSERT INTO `resources` VALUES ('2', '全国机房站点导航', 'loadIndex.do', '0', '0', '全国站点导航', '2017-02-27 22:52:34', '2017-02-27 22:52:36', '1');
INSERT INTO `resources` VALUES ('3', '城市站点地图', 'loadIndexMap.do', '0', '0', '城市站点地图', '2017-02-27 22:53:33', '2017-02-27 22:53:36', '1');
INSERT INTO `resources` VALUES ('4', '站点3D虚拟图', 'loadSite3dView.do', '0', '0', '站点3D虚拟图', '2017-02-27 22:54:29', '2017-02-27 22:54:31', '3');
INSERT INTO `resources` VALUES ('5', '查看站点多路视频', 'loadMultVideo.do', '0', '0', '站点多路视频', '2017-02-27 22:55:19', '2017-02-27 22:55:23', '10');
INSERT INTO `resources` VALUES ('6', '查看报警信息', 'loadAlertInfos.do', '0', '0', '报警信息', '2017-02-27 22:56:04', '2017-02-27 22:56:06', '10');
INSERT INTO `resources` VALUES ('7', '取消全部报警', 'cancelAllAlerts.do', '0', '0', '取消全部报警', '2017-02-27 22:56:54', '2017-02-27 22:56:56', '6');
INSERT INTO `resources` VALUES ('8', '查看站点数据曲线', 'loadSiteCurve.do', '0', '0', '站点数据曲线', '2017-02-27 22:57:44', '2017-02-27 22:57:46', '10');
INSERT INTO `resources` VALUES ('9', '设备管理', '\\', '1', '0', '设备管理', '2017-02-27 22:58:17', '2017-02-27 22:58:19', '10');
INSERT INTO `resources` VALUES ('10', '机房监控云服务系统', '\\', '2', '0', '云服务系统', '2017-02-28 16:51:59', '2017-02-28 16:52:02', '0');

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
  `gmtCreate` datetime NOT NULL COMMENT '记录创建日期',
  `gmtModified` datetime NOT NULL COMMENT '记录修改日期',
  `createBy` varchar(50) DEFAULT '' COMMENT '创建人',
  `lastModifedBy` varchar(50) DEFAULT '' COMMENT '最后修改人',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', '日常管理系统', '', '', '2017-02-06 15:12:47', '2017-02-14 15:12:32', 'Admin', 'Admin');
INSERT INTO `role` VALUES ('2', '设备操作员', '操作机房设备', '', '', '2017-02-07 15:12:50', '2017-02-14 15:12:35', 'Admin', 'Admin');
INSERT INTO `role` VALUES ('3', '机房运维员', '实时查看报警，运维机房', '', '', '2017-02-13 15:12:53', '2017-02-14 15:12:38', 'Admin', 'Admin');
INSERT INTO `role` VALUES ('4', '巡视员', '消防', '', '', '2017-02-13 15:12:57', '2017-02-14 15:12:41', 'Admin', 'Admin');
INSERT INTO `role` VALUES ('5', '视频监控管理员', '防盗', '', '', '2017-02-13 15:13:00', '2017-02-14 15:12:43', 'Admin', 'Admin');
INSERT INTO `role` VALUES ('6', '用户管理员', '管理维护用户信息', '', '', '2017-02-14 16:28:06', '2017-02-14 16:28:08', 'Admin', 'Admin');
INSERT INTO `role` VALUES ('7', '总经理', '公司主管', '', '', '2017-02-14 16:28:41', '2017-02-14 16:28:44', 'Admin', 'Admin');
INSERT INTO `role` VALUES ('8', '安全管理员', '入侵检测', '', '', '2017-02-13 16:30:29', '2017-02-14 16:30:33', 'Admin', 'Admin');

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
  `accuracy` decimal(32,2) NOT NULL DEFAULT '0.00' COMMENT '数据精度',
  `upperLimit` decimal(32,2) NOT NULL DEFAULT '0.00' COMMENT '数据上限',
  `lowerLimit` decimal(32,2) NOT NULL DEFAULT '0.00' COMMENT '数据下限',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '传感器类别',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '传感器状态',
  `visible` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '可见性',
  PRIMARY KEY (`sensorId`),
  KEY `sensor_insId` (`insId`),
  CONSTRAINT `sensor_insId` FOREIGN KEY (`insId`) REFERENCES `instrument` (`insId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sensor
-- ----------------------------
INSERT INTO `sensor` VALUES ('1', '1', '1', '回风温度', '℃', '0.10', '100.00', '-30.00', '0', '0', '0');
INSERT INTO `sensor` VALUES ('2', '1', '1', '外部温度', '℃', '0.10', '100.00', '-30.00', '0', '0', '0');
INSERT INTO `sensor` VALUES ('3', '1', '3', 'A相电压', 'V', '0.10', '430.00', '0.00', '0', '0', '0');
INSERT INTO `sensor` VALUES ('4', '1', '4', 'A相电流', 'A', '0.10', '50.00', '0.00', '0', '0', '0');
INSERT INTO `sensor` VALUES ('5', '1', '2', '回风湿度', '%', '0.10', '100.00', '0.00', '0', '0', '0');

-- ----------------------------
-- Table structure for site
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site` (
  `siteId` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteName` char(30) NOT NULL DEFAULT '' COMMENT '站点名',
  `address` char(80) DEFAULT '' COMMENT '地址',
  `longitude` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '经度',
  `latitude` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '纬度',
  `description` varchar(100) DEFAULT '' COMMENT '简介',
  `cityId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '所属城市Id',
  PRIMARY KEY (`siteId`),
  KEY `site_cityId` (`cityId`),
  CONSTRAINT `site_cityId` FOREIGN KEY (`cityId`) REFERENCES `city` (`cityId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of site
-- ----------------------------
INSERT INTO `site` VALUES ('1', '金家岭', '崂山区燕岭路', '120.464', '36.108', '青岛广电站点机房', '1');
INSERT INTO `site` VALUES ('2', '明星大厅', '崂山区', '120.394', '36.085', '青岛广电站点机房', '1');
INSERT INTO `site` VALUES ('3', '澄海三路', '市南区', '120.407', '36.065', '青岛广电站点机房', '1');
INSERT INTO `site` VALUES ('4', '人民一路', '市北区', '120.359', '36.113', '青岛广电站点机房', '1');
INSERT INTO `site` VALUES ('5', '浮山后', '市北区同安四路', '120.416', '36.104', '青岛广电站点机房', '1');
INSERT INTO `site` VALUES ('6', '李村', '李沧区果园路', '120.424', '36.159', '青岛广电站点机房', '1');
INSERT INTO `site` VALUES ('7', '王哥庄', '崂山区', '120.627', '36.263', '青岛广电站点机房', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sitedata
-- ----------------------------
INSERT INTO `sitedata` VALUES ('1', '2016-12-09 14:27:15', '1:24.61@2:32.13@3:220@4:5.1@5:56.1', '1');
INSERT INTO `sitedata` VALUES ('2', '2016-12-09 14:27:50', '1:24.80@2:31.21@3:221@4:5.6@5:58.2', '1');
INSERT INTO `sitedata` VALUES ('3', '2016-12-09 14:28:18', '1:25.20@2:33.21@3:222@4:6.1@5:55.5', '1');
INSERT INTO `sitedata` VALUES ('4', '2016-12-09 14:28:52', '1:24.25@2:31.21@3:220@4:5.2@5:56.5', '1');
INSERT INTO `sitedata` VALUES ('5', '2016-12-09 14:29:25', '1:25.28@2:33.31@3:223@4:6.2@5:59.5', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `siteId` int(20) unsigned NOT NULL DEFAULT '0' COMMENT '站点Id(外键)',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '姓名',
  `userName` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` char(64) NOT NULL DEFAULT '' COMMENT '密码',
  `telephone` char(11) NOT NULL DEFAULT '' COMMENT '电话号码',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '用户类别',
  `roleId` varchar(30) NOT NULL DEFAULT '' COMMENT '关联角色Id',
  `sex` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '性别：0：男；1：女',
  `gmtModified` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`userId`),
  KEY `id` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '0', '小雪', 'xueer', 'xiao123', '18765989898', 'xiaoxue@163.com', '0', '1', '1', '2017-02-08 16:37:45');
INSERT INTO `user` VALUES ('2', '1', '王磊', 'wanglei', 'wang123', '18723459898', '1283956@qq.com', '1', '2', '0', '2017-02-14 16:37:47');
INSERT INTO `user` VALUES ('3', '2', '刘敏', 'liumin', 'min123', '15623459221', 'min55666@163.com', '1', '3', '1', '2017-02-06 16:37:56');
INSERT INTO `user` VALUES ('4', '3', '张明', 'zhangming', 'zhang2456', '15623256211', 'zhangming88@163.com', '1', '2', '0', '2017-02-09 16:37:59');
INSERT INTO `user` VALUES ('5', '4', '张涛', 'zhangtao', 'tao5566', '18723256218', 'zhangtao@163.com', '1', '4', '0', '2017-02-10 16:38:03');
INSERT INTO `user` VALUES ('6', '1', '刘鹏', 'liupeng', 'peng222', '18765876210', 'liupeng@163.com', '1', '5', '0', '2017-02-12 16:38:06');
INSERT INTO `user` VALUES ('7', '1', '余浩', 'yuhao', 'hao222', '18765950912', 'haog@163.com', '1', '2', '0', '2017-02-13 16:38:09');
INSERT INTO `user` VALUES ('8', '1', '吴迪', 'wudi', 'di123456', '15688901102', '1678922221@qq.com', '1', '5', '0', '2017-02-13 16:38:13');
