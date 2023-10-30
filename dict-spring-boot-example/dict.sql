/*
 Navicat Premium Data Transfer

 Source Server         : dev-252
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : 192.168.0.252:3306
 Source Schema         : dict

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 26/10/2023 16:47:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for consumer
-- ----------------------------
DROP TABLE IF EXISTS `consumer`;
CREATE TABLE `consumer`  (
  `consumer_id` int(11) NOT NULL,
  `consumer_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_by` int(11) NOT NULL,
  `update_by` int(11) NOT NULL,
  PRIMARY KEY (`consumer_id`) USING BTREE,
  INDEX `user_idx`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of consumer
-- ----------------------------
INSERT INTO `consumer` VALUES (1, '刘备', 100, 101, 102);
INSERT INTO `consumer` VALUES (2, '关羽', 101, 100, 102);
INSERT INTO `consumer` VALUES (3, '张飞', 102, 101, 100);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (100, '刘邦', 0);
INSERT INTO `user` VALUES (101, '项羽', 0);
INSERT INTO `user` VALUES (102, '嬴政', 0);

SET FOREIGN_KEY_CHECKS = 1;
