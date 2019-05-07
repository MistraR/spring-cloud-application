/*
 Navicat Premium Data Transfer

 Source Server         : 112.74.38.117
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 112.74.38.117
 Source Database       : springcloud

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : utf-8

 Date: 10/18/2018 08:30:00 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `email_template`
-- ----------------------------
DROP TABLE IF EXISTS `email_template`;
CREATE TABLE `email_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(64) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `system_role_id` int(11) DEFAULT NULL,
  `app_token_version` int(11) NOT NULL DEFAULT '268451841' COMMENT 'APP登录、Token版本号',
  `web_token_version` int(11) NOT NULL DEFAULT '268451841' COMMENT 'H5登录、Token版本号',
  `app_token_refresh_time` timestamp NULL DEFAULT NULL COMMENT 'apptoken最后刷新时间',
  `web_token_refresh_time` timestamp NULL DEFAULT NULL COMMENT 'webtoken最后刷新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`user_name`)
) ENGINE=MyISAM AUTO_INCREMENT=1042313781676752899 DEFAULT CHARSET=utf8;

CREATE TABLE `system_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `resource_type` int(11) DEFAULT '1',
  `url` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT '1',
  `system_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT '0' COMMENT '0:可用 1:不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;