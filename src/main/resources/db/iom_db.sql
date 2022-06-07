/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : iom_db

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 06/06/2022 20:17:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `per_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限代码字符串',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `per_code`(`per_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '查看用户', 'sys:user:view');
INSERT INTO `permission` VALUES (2, '添加用户', 'sys:user:add');
INSERT INTO `permission` VALUES (3, '删除用户', 'sys:user:remove');
INSERT INTO `permission` VALUES (4, '修改用户', 'sys:user:update');
INSERT INTO `permission` VALUES (5, '查看角色', 'sys:role:view');
INSERT INTO `permission` VALUES (6, '添加角色', 'sys:role:add');
INSERT INTO `permission` VALUES (7, '删除角色', 'sys:role:remove');
INSERT INTO `permission` VALUES (8, '修改角色', 'sys:role:update');
INSERT INTO `permission` VALUES (9, '查看权限', 'sys:permission:view');
INSERT INTO `permission` VALUES (10, '添加权限', 'sys:permission:add');
INSERT INTO `permission` VALUES (11, '删除权限', 'sys:permission:remove');
INSERT INTO `permission` VALUES (12, '修改权限', 'sys:permission:update');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin');
INSERT INTO `role` VALUES (2, 'customer');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int NOT NULL COMMENT '角色id',
  `permission_id` int NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 1, 4);
INSERT INTO `role_permission` VALUES (5, 1, 5);
INSERT INTO `role_permission` VALUES (6, 1, 6);
INSERT INTO `role_permission` VALUES (7, 1, 7);
INSERT INTO `role_permission` VALUES (8, 1, 8);
INSERT INTO `role_permission` VALUES (9, 1, 9);
INSERT INTO `role_permission` VALUES (10, 1, 10);
INSERT INTO `role_permission` VALUES (11, 1, 11);
INSERT INTO `role_permission` VALUES (12, 1, 12);
INSERT INTO `role_permission` VALUES (13, 2, 1);
INSERT INTO `role_permission` VALUES (14, 2, 5);
INSERT INTO `role_permission` VALUES (15, 2, 9);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帐号',
  `password` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `reg_time` datetime NOT NULL COMMENT '注册时间',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', 'admin', '2022-03-10 16:41:32', '986813986@qq.com', '13767977111');
INSERT INTO `user` VALUES (2, 'wang', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', 'wang', '2022-03-10 16:41:32', '', '');
INSERT INTO `user` VALUES (3, 'guest', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', 'guest', '2022-03-10 16:41:32', '', '');
INSERT INTO `user` VALUES (4, 'laowang', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', '隔壁老王', '2022-04-14 09:18:49', '986813986@qq.com', '13767977172');
INSERT INTO `user` VALUES (5, 'laowang01', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', '隔壁老王', '2022-04-14 09:19:47', '986813986@qq.com', '13767972');
INSERT INTO `user` VALUES (6, 'laowang02', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', '隔壁老王', '2022-04-14 09:33:22', '986813986@qq.com', '13767977172');
INSERT INTO `user` VALUES (7, 'admin01', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', 'admin01', '2022-06-06 06:00:06', '986813986@qq.com', '13767977172');
INSERT INTO `user` VALUES (11, 'admin03', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', 'admin02', '2022-06-06 06:42:42', '986813986@qq.com', '13767977172');
INSERT INTO `user` VALUES (12, 'admin04', 'NzQ4ODZGQTBDREUxQzAyMUNCNjc5QTU0MEQwRDQ2ODk=', 'admin02', '2022-06-06 06:43:41', '986813986@qq.com', '13767977172');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
