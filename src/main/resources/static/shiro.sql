/*
 Navicat Premium Data Transfer

 Source Server         : HCY
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : 47.104.249.85:3306
 Source Schema         : shiro

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : 65001

 Date: 11/10/2020 21:14:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_permission
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permission`;
CREATE TABLE `tbl_permission`  (
  `permission_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '许可序号',
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '许可名字',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_permission
-- ----------------------------
INSERT INTO `tbl_permission` VALUES ('28563443572E43D6AF1299ECCAFC0777', 'edit');
INSERT INTO `tbl_permission` VALUES ('EB16A1A6DCA34A6F877C8D18C5298592', 'view');

-- ----------------------------
-- Table structure for tbl_resource
-- ----------------------------
DROP TABLE IF EXISTS `tbl_resource`;
CREATE TABLE `tbl_resource`  (
  `resource_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源序号',
  `resource_user_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源的用户序号',
  `resource_permission_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源的许可序号',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_resource
-- ----------------------------
INSERT INTO `tbl_resource` VALUES ('230D7D0D983B464EBA1440FB9E57592C', 'F705347400044C8FBC6228C496BCAB23', 'EB16A1A6DCA34A6F877C8D18C5298592');
INSERT INTO `tbl_resource` VALUES ('A2432C2949A34BC5BB043124337806ED', '35A7ED97C67147E99C3C1F6E5AE14825', 'EB16A1A6DCA34A6F877C8D18C5298592');
INSERT INTO `tbl_resource` VALUES ('DDD40171DE114DFBB3487988DD7C55ED', 'F705347400044C8FBC6228C496BCAB23', '28563443572E43D6AF1299ECCAFC0777');

-- ----------------------------
-- Table structure for tbl_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role`  (
  `role_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限序号',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
INSERT INTO `tbl_role` VALUES ('7095AE08AF5C4DBBBEC48B7821D01278', 'user');
INSERT INTO `tbl_role` VALUES ('71E055B6DE6744D28CBAAF29A6969626', 'admin');

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user`  (
  `user_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户序号',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名字',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户权限',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('35A7ED97C67147E99C3C1F6E5AE14825', 'user', '8683c5733d829e17d4ebdf2eee26eaf9', '7095AE08AF5C4DBBBEC48B7821D01278');
INSERT INTO `tbl_user` VALUES ('b39eb110551812f4d911c9d61ad85b6e', 'HHH', '8683c5733d829e17d4ebdf2eee26eaf9', '7095AE08AF5C4DBBBEC48B7821D01278');
INSERT INTO `tbl_user` VALUES ('F705347400044C8FBC6228C496BCAB23', 'HCY', '8683c5733d829e17d4ebdf2eee26eaf9', '71E055B6DE6744D28CBAAF29A6969626');

SET FOREIGN_KEY_CHECKS = 1;
