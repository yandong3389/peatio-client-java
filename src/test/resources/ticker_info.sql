/*
Navicat MySQL Data Transfer

Source Server         : 192.168.8.182_3306
Source Server Version : 50532
Source Host           : 192.168.8.182:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2017-07-07 15:02:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ticker_info`
-- ----------------------------
DROP TABLE IF EXISTS `ticker_info`;
CREATE TABLE `ticker_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buy` decimal(10,0) NOT NULL,
  `sell` decimal(10,0) NOT NULL,
  `low` decimal(10,0) NOT NULL,
  `high` decimal(10,0) NOT NULL,
  `last` decimal(10,0) NOT NULL,
  `vol` decimal(10,0) NOT NULL,
  `at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ticker_info
-- ----------------------------
