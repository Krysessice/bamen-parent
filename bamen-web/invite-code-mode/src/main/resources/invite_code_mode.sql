/*
Navicat MySQL Data Transfer

Source Server         : localhost_mysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : invite_code_mode

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-09-06 14:13:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_card_gift_record
-- ----------------------------
DROP TABLE IF EXISTS `t_card_gift_record`;
CREATE TABLE `t_card_gift_record` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_PRESENTER` mediumint(8) unsigned NOT NULL COMMENT '赠送者',
  `F_PRESENTEE` mediumint(8) unsigned NOT NULL COMMENT '受赠者',
  `F_CARD_NUM` mediumint(9) NOT NULL COMMENT '房卡数',
  `F_GIFT_REASON` varchar(50) DEFAULT NULL COMMENT '赠送原因',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='房卡赠送记录';

-- ----------------------------
-- Table structure for t_game_type
-- ----------------------------
DROP TABLE IF EXISTS `t_game_type`;
CREATE TABLE `t_game_type` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_GAME_TYPE_ID` int(10) unsigned NOT NULL COMMENT '游戏类型id',
  `F_GAME_NAME` varchar(25) NOT NULL COMMENT '游戏名',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='游戏类型';

-- ----------------------------
-- Table structure for t_open_room_perhour_record
-- ----------------------------
DROP TABLE IF EXISTS `t_open_room_perhour_record`;
CREATE TABLE `t_open_room_perhour_record` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_GAME_TYPE_ID` int(10) unsigned DEFAULT NULL COMMENT '游戏类型id',
  `F_OPEN_ROOM_NUM` int(10) unsigned NOT NULL COMMENT '开房数',
  `F_CARD_COST` bigint(20) unsigned NOT NULL COMMENT '房卡消耗',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_t_open_room_perhour_record_unique1` (`F_GAME_TYPE_ID`,`F_CREATE_TIME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1675 DEFAULT CHARSET=utf8 COMMENT='每小时开房记录表';

-- ----------------------------
-- Table structure for t_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_order`;
CREATE TABLE `t_pay_order` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_ORDER_NO` varchar(100) NOT NULL COMMENT '订单号',
  `F_USER_ID` mediumint(8) unsigned DEFAULT NULL COMMENT '用户id',
  `F_GAME_ID` mediumint(8) unsigned NOT NULL COMMENT '游戏id',
  `F_ACCOUNTS` varchar(100) DEFAULT NULL COMMENT '微信标识',
  `F_PAY_PRICE` decimal(10,0) unsigned NOT NULL DEFAULT '0' COMMENT '支付金额',
  `F_CARD_GOLD` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '房卡',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_pay_order_unique1` (`F_ORDER_NO`) USING BTREE COMMENT '订单号唯一'
) ENGINE=InnoDB AUTO_INCREMENT=1709 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_pay_order_perday_statistic
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_order_perday_statistic`;
CREATE TABLE `t_pay_order_perday_statistic` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_USER_ID` mediumint(8) unsigned DEFAULT NULL COMMENT '用户id',
  `F_GAME_ID` mediumint(8) unsigned NOT NULL COMMENT '游戏id',
  `F_ACCOUNTS` varchar(100) DEFAULT NULL COMMENT '微信标识',
  `F_PAY_PRICE` decimal(10,0) unsigned NOT NULL DEFAULT '0' COMMENT '支付金额',
  `F_CARD_GOLD` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '房卡',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_pops_unique1` (`F_GAME_ID`,`F_CREATE_TIME`) USING BTREE COMMENT 'gameId, createDate 联合唯一'
) ENGINE=InnoDB AUTO_INCREMENT=1625 DEFAULT CHARSET=utf8 COMMENT='每日用户订单统计';

-- ----------------------------
-- Table structure for t_system_info
-- ----------------------------
DROP TABLE IF EXISTS `t_system_info`;
CREATE TABLE `t_system_info` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_GAME_NAME` varchar(25) DEFAULT '开心游戏' COMMENT '游戏名',
  `F_TITLE` varchar(50) DEFAULT '开心游戏邀请码后台' COMMENT '代理后台标题',
  `F_LEAST_RECRUIT_NUM` int(10) unsigned DEFAULT '0' COMMENT '最小招募人数',
  `F_T1_COMMISSION` float unsigned DEFAULT '0.4' COMMENT '直属团队提成',
  `F_T2_COMMISSION` float unsigned DEFAULT '0.08' COMMENT '二级团队提成',
  `F_T3_COMMISSION` float unsigned DEFAULT '0.05' COMMENT '三级团队提成',
  `F_IN_GROUP_LIMIT` tinyint(3) unsigned DEFAULT '5' COMMENT '用户加群房间上限',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统信息';

-- ----------------------------
-- Table structure for t_sys_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_agent`;
CREATE TABLE `t_sys_agent` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_USER_ID` mediumint(8) unsigned DEFAULT NULL COMMENT '用户id',
  `F_GAME_ID` mediumint(8) unsigned NOT NULL COMMENT '游戏id',
  `F_ACCOUNTS` varchar(100) DEFAULT NULL COMMENT '微信标识',
  `F_NICK_NAME` varchar(50) NOT NULL COMMENT '游戏昵称',
  `F_REAL_NAME` varchar(25) DEFAULT NULL COMMENT '真实姓名',
  `F_TEL` char(11) DEFAULT NULL COMMENT '手机号',
  `F_OPENING_BANK` varchar(32) DEFAULT NULL COMMENT '开户行',
  `F_BANK_ACCOUNT` char(19) DEFAULT NULL COMMENT '银行账号',
  `F_PROVINCE` varchar(25) DEFAULT NULL COMMENT '省',
  `F_CITY` varchar(25) DEFAULT NULL COMMENT '市',
  `F_IS_FINISH_INFO` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已完成基本信息的填写，默认为0',
  `F_PASSWORD` varchar(32) NOT NULL,
  `F_SECRET_KEY` varchar(36) NOT NULL,
  `F_LAST_LOGIN_TIME` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  `F_SUPER_AGENT_GAME_ID` mediumint(8) unsigned DEFAULT NULL COMMENT '上级代理id，关联用户gameId',
  `F_TRANS_MEMBER_TIME` timestamp NULL DEFAULT NULL COMMENT '变成会员，即填写上级代理id的时间',
  `F_IS_AUTHORIZED` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已被授权，默认为0',
  `F_AUTHORIZED_TIME` timestamp NULL DEFAULT NULL COMMENT '授权时间',
  `F_RECRUIT_NUM` int(10) unsigned DEFAULT '0' COMMENT '招募人数',
  `F_SHOW_ANNOUNCE` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否在首页显示公告',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_sys_agent_unique1` (`F_GAME_ID`) USING BTREE COMMENT '游戏id唯一',
  KEY `fgk_sys_agent_f1` (`F_SUPER_AGENT_GAME_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2469 DEFAULT CHARSET=utf8 COMMENT='后台代理表';

-- ----------------------------
-- Table structure for t_sys_agent_clear_record
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_agent_clear_record`;
CREATE TABLE `t_sys_agent_clear_record` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_START_DATE` date NOT NULL COMMENT '起始日期',
  `F_END_DATE` date NOT NULL COMMENT '结束日期',
  `F_PAY_PRICE` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '交易总额',
  `F_CLEAR_PRICE` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '结算总额',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='代理结算记录';

-- ----------------------------
-- Table structure for t_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_resource`;
CREATE TABLE `t_sys_resource` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_USER_ID` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `F_RESOURCE_ID` bigint(20) unsigned NOT NULL COMMENT '资源id',
  `F_RESOURCE_NAME` varchar(25) NOT NULL COMMENT '资源名',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_sys_resource_unique1` (`F_USER_ID`,`F_RESOURCE_ID`,`F_RESOURCE_NAME`) COMMENT '用户id，资源id，资源名联合唯一',
  KEY `fgk_sys_resource_f2` (`F_RESOURCE_ID`),
  CONSTRAINT `fgk_sys_resource_f1` FOREIGN KEY (`F_USER_ID`) REFERENCES `t_sys_agent` (`F_ID`) ON DELETE CASCADE,
  CONSTRAINT `fgk_sys_resource_f2` FOREIGN KEY (`F_RESOURCE_ID`) REFERENCES `t_sys_agent` (`F_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8527 DEFAULT CHARSET=utf8 COMMENT='用户资源表';

-- ----------------------------
-- Table structure for t_user_open_room_perday_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_open_room_perday_record`;
CREATE TABLE `t_user_open_room_perday_record` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_GAME_TYPE_ID` int(10) unsigned DEFAULT NULL COMMENT '游戏类型id',
  `F_USER_ID` mediumint(8) unsigned DEFAULT NULL COMMENT '用户id',
  `F_GAME_ID` mediumint(8) unsigned DEFAULT NULL COMMENT '游戏id',
  `F_ACCOUNTS` varchar(100) DEFAULT NULL COMMENT '微信标识',
  `F_OPEN_ROOM_NUM` int(10) unsigned NOT NULL COMMENT '开房数',
  `F_CARD_COST` bigint(20) unsigned NOT NULL COMMENT '房卡消耗',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_uorpr_unique1` (`F_GAME_TYPE_ID`,`F_GAME_ID`,`F_CREATE_TIME`)
) ENGINE=InnoDB AUTO_INCREMENT=6739 DEFAULT CHARSET=utf8 COMMENT='每小时开房记录表';
