/*
Navicat MySQL Data Transfer

Source Server         : localhost_mysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : room_card_mode

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-08-29 09:15:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_card_bonus_record
-- ----------------------------
DROP TABLE IF EXISTS `t_card_bonus_record`;
CREATE TABLE `t_card_bonus_record` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_SYS_AGENT_ID` bigint(20) unsigned NOT NULL COMMENT '关联代理主键id',
  `F_PAY_ORDER_ID` bigint(20) unsigned NOT NULL COMMENT '关联订单主键id',
  `F_FIR_SUPER_AGENT_ID` bigint(20) unsigned DEFAULT NULL COMMENT '上级代理主键id',
  `F_FIR_BONUS` int(11) DEFAULT NULL COMMENT '上级代理返利',
  `F_SEC_SUPER_AGENT_ID` bigint(20) DEFAULT NULL COMMENT '上上级代理主键id',
  `F_SEC_BONUS` int(11) DEFAULT NULL COMMENT '上上级代理返利',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_t_card_bonus_unique1` (`F_SYS_AGENT_ID`) USING BTREE COMMENT '代理主键id唯一',
  UNIQUE KEY `idx_t_card_bonus_unique2` (`F_PAY_ORDER_ID`) USING BTREE COMMENT '订单主键id唯一',
  CONSTRAINT `fgk_F_PAY_ORDER_ID` FOREIGN KEY (`F_PAY_ORDER_ID`) REFERENCES `t_pay_order` (`F_ID`) ON DELETE CASCADE,
  CONSTRAINT `fgk_F_SYS_AGENT_ID3` FOREIGN KEY (`F_SYS_AGENT_ID`) REFERENCES `t_sys_agent` (`F_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房卡返利记录';

-- ----------------------------
-- Table structure for t_card_gift_for_agent_record
-- ----------------------------
DROP TABLE IF EXISTS `t_card_gift_for_agent_record`;
CREATE TABLE `t_card_gift_for_agent_record` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_PRESENTER` bigint(20) unsigned NOT NULL COMMENT '赠送者',
  `F_PRESENTEE` bigint(20) unsigned NOT NULL COMMENT '受赠者',
  `F_CARD_NUM` mediumint(9) NOT NULL COMMENT '房卡数',
  `F_GIFT_REASON` varchar(50) DEFAULT NULL COMMENT '赠送原因',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理房卡赠送记录';

-- ----------------------------
-- Table structure for t_card_gift_for_player_record
-- ----------------------------
DROP TABLE IF EXISTS `t_card_gift_for_player_record`;
CREATE TABLE `t_card_gift_for_player_record` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_PRESENTER` bigint(20) unsigned NOT NULL COMMENT '赠送者',
  `F_PRESENTEE` mediumint(8) unsigned NOT NULL COMMENT '受赠者',
  `F_CARD_NUM` mediumint(9) NOT NULL COMMENT '房卡数',
  `F_GIFT_REASON` varchar(50) DEFAULT NULL COMMENT '赠送原因',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家房卡赠送记录';

-- ----------------------------
-- Table structure for t_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_order`;
CREATE TABLE `t_pay_order` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_ORDER_NO` varchar(50) NOT NULL COMMENT '订单号',
  `F_SYS_AGENT_ID` bigint(20) unsigned NOT NULL COMMENT '关联代理主键id',
  `F_PAY_PRICE` decimal(10,2) unsigned NOT NULL COMMENT '支付金额',
  `F_CARD_NUM` int(10) unsigned NOT NULL COMMENT '购买的房卡数量',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_t_pay_order_unique1` (`F_SYS_AGENT_ID`) USING BTREE COMMENT '代理主键id唯一',
  CONSTRAINT `fgk_F_SYS_AGENT_ID2` FOREIGN KEY (`F_SYS_AGENT_ID`) REFERENCES `t_sys_agent` (`F_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_system_info
-- ----------------------------
DROP TABLE IF EXISTS `t_system_info`;
CREATE TABLE `t_system_info` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_GAME_NAME` varchar(25) DEFAULT '开心游戏' COMMENT '游戏名',
  `F_TITLE` varchar(50) DEFAULT '开心游戏房卡模式后台' COMMENT '代理后台标题',
  `F_RATE_LV1` decimal(2,2) unsigned NOT NULL DEFAULT '0.20' COMMENT '上级代理返利',
  `F_RATE_LV2` decimal(2,2) unsigned NOT NULL DEFAULT '0.05' COMMENT '上上级代理返利',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统信息';

-- ----------------------------
-- Table structure for t_sys_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_agent`;
CREATE TABLE `t_sys_agent` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_ACCOUNT` char(11) NOT NULL COMMENT '账号',
  `F_NICK_NAME` varchar(50) NOT NULL COMMENT '昵称',
  `F_PASSWORD` varchar(32) NOT NULL,
  `F_SECRET_KEY` varchar(36) NOT NULL,
  `F_SUPER_AGENT_ID` bigint(20) unsigned DEFAULT NULL COMMENT '上级id，主键id',
  `F_FIRST_CHARGE_TIME` datetime DEFAULT NULL COMMENT '首充时间',
  `F_LAST_LOGIN_TIME` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_t_sys_agent_unique1` (`F_ACCOUNT`) USING BTREE COMMENT '账号唯一'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台代理';

-- ----------------------------
-- Table structure for t_sys_agent_extend
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_agent_extend`;
CREATE TABLE `t_sys_agent_extend` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_SYS_AGENT_ID` bigint(20) unsigned NOT NULL COMMENT '关联代理主键id',
  `F_LEFT_CARD_NUM` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '剩余房卡数量',
  `F_SOLD_CARD_NUM` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '已售房卡数量',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`),
  UNIQUE KEY `idx_t_sys_agent_extend_unique1` (`F_SYS_AGENT_ID`) USING BTREE COMMENT '代理主键id唯一',
  CONSTRAINT `fgk_F_SYS_AGENT_ID` FOREIGN KEY (`F_SYS_AGENT_ID`) REFERENCES `t_sys_agent` (`F_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台代理扩展表';
