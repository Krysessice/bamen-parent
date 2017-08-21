/*
Navicat MySQL Data Transfer

Source Server         : localhost_mysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : room_card_mode

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-08-21 10:50:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_system_info
-- ----------------------------
DROP TABLE IF EXISTS `t_system_info`;
CREATE TABLE `t_system_info` (
  `F_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `F_GAME_NAME` varchar(25) DEFAULT '开心游戏' COMMENT '游戏名',
  `F_TITLE` varchar(50) DEFAULT '开心游戏代理商后台' COMMENT '代理后台标题',
  `F_SYS_FLAG` bit(1) NOT NULL DEFAULT b'1' COMMENT '标识，1为可用，0为禁用',
  `F_CREATOR` bigint(20) unsigned DEFAULT NULL COMMENT '创建者',
  `F_CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_MODIFIER` bigint(20) unsigned DEFAULT NULL COMMENT '修改者',
  `F_MODIFY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`F_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统信息';

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
  UNIQUE KEY `idx_sys_agent_unique1` (`F_ACCOUNT`) USING BTREE COMMENT '账号唯一'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='后台代理';

INSERT INTO t_system_info VALUES();