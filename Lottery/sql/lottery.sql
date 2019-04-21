/*
Navicat MySQL Data Transfer

Source Server         : default
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : lottery

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2019-04-21 17:09:55
*/
DROP DATABASE IF EXISTS lottery;
CREATE DATABASE lottery
  DEFAULT CHARACTER SET utf8;
USE lottery;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `awardrecord`
-- ----------------------------
DROP TABLE IF EXISTS `awardrecord`;
CREATE TABLE `awardrecord` (
  `awardRecordId` int(11) NOT NULL AUTO_INCREMENT COMMENT '获奖记录id',
  `userId` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT ' 	用户id',
  `userName` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `prizeId` int(11) NOT NULL COMMENT '奖项id',
  `prizeName` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '奖项名',
  `prizeDesc` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '奖项描述',
  PRIMARY KEY (`awardRecordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of awardrecord
-- ----------------------------

-- ----------------------------
-- Table structure for `chatlog`
-- ----------------------------
DROP TABLE IF EXISTS `chatlog`;
CREATE TABLE `chatlog` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT 'QQ号或邮箱',
  `userName` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `time` date NOT NULL COMMENT '聊天记录时间',
  `isUseKeyword` tinyint(4) NOT NULL COMMENT '0没有，1有',
  `numberOfCharacters` int(11) NOT NULL COMMENT '除去关键词的字符数',
  `content` varchar(1024) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '聊天记录详情',
  PRIMARY KEY (`recordId`),
  KEY `userId` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of chatlog
-- ----------------------------

-- ----------------------------
-- Table structure for `condition`
-- ----------------------------
DROP TABLE IF EXISTS `condition`;
CREATE TABLE `condition` (
  `conditionId` int(11) NOT NULL AUTO_INCREMENT COMMENT '条件id',
  `activityName` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '活动名',
  `keyword` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '关键词',
  `copyWriting` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文案',
  `fileName` varchar(1024) COLLATE utf8_unicode_ci NOT NULL COMMENT '聊天记录文件名',
  `startTime` date DEFAULT NULL COMMENT '抽奖时间段起始点',
  `endTime` date DEFAULT NULL COMMENT '抽奖时间段终止点',
  `showTime` date DEFAULT NULL COMMENT '开奖时间点',
  `filter` int(11) NOT NULL DEFAULT '0' COMMENT '过滤规则：1不过滤，2普通过滤，3深度过滤',
  PRIMARY KEY (`conditionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of condition
-- ----------------------------

-- ----------------------------
-- Table structure for `prize`
-- ----------------------------
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize` (
  `prizeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '奖项id',
  `prizeName` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '奖项名',
  `prizeDesc` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '奖项描述',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '人数',
  PRIMARY KEY (`prizeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of prize
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT 'QQ号或邮箱',
  `userName` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '用户名',
  `userType` int(1) NOT NULL DEFAULT '0' COMMENT '用户类型：0普通学生，1助教，2老师，3系统消息',
  `isUseKeyword` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否使用过关键词:0没有，1有',
  `isInTime` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否在抽奖时间段中：0没有，1有',
  `activeDay` int(11) NOT NULL DEFAULT '0' COMMENT '有效发言天数',
  `continuousActiveDay` int(11) NOT NULL DEFAULT '0' COMMENT '最大连续有效发言天数',
  `activeRecord` int(11) NOT NULL DEFAULT '0' COMMENT '有效发言条数',
  `isPrize` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否中奖',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
