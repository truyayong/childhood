/*
Navicat MySQL Data Transfer
Source Server         : ����
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : test1
Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001
Date: 2016-11-05 21:17:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '����id',
  `name` varchar(45) DEFAULT NULL COMMENT '�û���',
  `password` varchar(45) DEFAULT NULL COMMENT '����',
  `description` varchar(45) DEFAULT NULL COMMENT '����ǩ��',
  `avaterUrl` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;