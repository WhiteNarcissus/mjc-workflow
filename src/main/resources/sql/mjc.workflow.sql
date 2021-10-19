/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : workflow

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2021-10-19 23:40:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_action`
-- ----------------------------
DROP TABLE IF EXISTS `t_action`;
CREATE TABLE `t_action` (
  `action_id` bigint(32) NOT NULL COMMENT '行为id',
  `program_code` varchar(50) NOT NULL COMMENT '所执行程序代码',
  `case_id` bigint(32) NOT NULL,
  `node_code` varchar(32) NOT NULL COMMENT '节点代码',
  `task_id` bigint(32) NOT NULL COMMENT '任务id',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(30) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_action
-- ----------------------------

-- ----------------------------
-- Table structure for `t_node`
-- ----------------------------
DROP TABLE IF EXISTS `t_node`;
CREATE TABLE `t_node` (
  `node_id` bigint(32) NOT NULL,
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品代码',
  `node_code` varchar(20) NOT NULL COMMENT '节点代码',
  `node_name` varchar(4000) NOT NULL COMMENT '节点名称',
  `node_type` varchar(8) NOT NULL DEFAULT 'manual' COMMENT '节点类型manual,auto',
  `status` tinyint(2) NOT NULL COMMENT '是否有效 1 ，0 ',
  `is_show` tinyint(4) NOT NULL COMMENT '是否隐藏',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(30) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_node
-- ----------------------------

-- ----------------------------
-- Table structure for `t_node_program`
-- ----------------------------
DROP TABLE IF EXISTS `t_node_program`;
CREATE TABLE `t_node_program` (
  `node_program_id` bigint(32) NOT NULL,
  `program_code` varchar(50) NOT NULL,
  `node_code` varchar(20) NOT NULL,
  `priority` int(10) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(30) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`node_program_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_node_program
-- ----------------------------

-- ----------------------------
-- Table structure for `t_program`
-- ----------------------------
DROP TABLE IF EXISTS `t_program`;
CREATE TABLE `t_program` (
  `program_id` bigint(32) NOT NULL,
  `program_code` varchar(50) NOT NULL,
  `program_name` varchar(50) NOT NULL,
  `program_type` varchar(31) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `intput_field` longtext NOT NULL,
  `output_field` text NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `product_code` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(30) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`program_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_program
-- ----------------------------

-- ----------------------------
-- Table structure for `t_task`
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `task_id` bigint(32) NOT NULL COMMENT '任务id',
  `case_id` bigint(32) NOT NULL COMMENT '案件id',
  `cur_prog_index` int(20) NOT NULL DEFAULT '0' COMMENT '当前程序下标',
  `node_code` varchar(32) NOT NULL COMMENT '节点代码',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `last_node` varchar(32) NOT NULL COMMENT '上一节点',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(30) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task
-- ----------------------------

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL DEFAULT '',
  `gender` tinyint(255) NOT NULL,
  `age` int(11) NOT NULL,
  `telphone` varchar(255) NOT NULL,
  `register_mode` varchar(255) NOT NULL DEFAULT '',
  `third_party_id` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('9', 'zs', '1', '1', '17312341234', 'byPhone', '');

-- ----------------------------
-- Table structure for `user_password`
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrept_password` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('8', 'JfnnlDI7RTiF9RgfG2JNCw==', '9');
