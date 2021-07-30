/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : db_im_netty

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 15/07/2021 13:35:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_im_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_im_comment`;
CREATE TABLE `t_im_comment`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_im_comment
-- ----------------------------

-- ----------------------------
-- Table structure for t_im_friend
-- ----------------------------
DROP TABLE IF EXISTS `t_im_friend`;
CREATE TABLE `t_im_friend`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `uid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mark_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_im_friend
-- ----------------------------
INSERT INTO `t_im_friend` VALUES ('1412311164564140032', '1412300648782807040', '1412304972757909504', '', '2021-07-06 15:23:16');
INSERT INTO `t_im_friend` VALUES ('1412311164627054592', '1412304972757909504', '1412300648782807040', '', '2021-07-06 15:23:16');
INSERT INTO `t_im_friend` VALUES ('1412312794944630784', '1412304972757909504', '1412311668396519424', 'angular', '2021-07-06 15:29:44');
INSERT INTO `t_im_friend` VALUES ('1412312794973990912', '1412311668396519424', '1412304972757909504', '', '2021-07-06 15:29:44');
INSERT INTO `t_im_friend` VALUES ('1412313478788149248', '1412311668396519424', '1412300648782807040', '', '2021-07-06 15:32:28');
INSERT INTO `t_im_friend` VALUES ('1412313478817509376', '1412300648782807040', '1412311668396519424', '', '2021-07-06 15:32:28');
INSERT INTO `t_im_friend` VALUES ('1412315636128411648', '1412314729240195072', '1412300648782807040', '', '2021-07-06 15:41:02');
INSERT INTO `t_im_friend` VALUES ('1412315636149383168', '1412300648782807040', '1412314729240195072', '迪丽热巴', '2021-07-06 15:41:02');
INSERT INTO `t_im_friend` VALUES ('1412316906314657792', '1412316401152684032', '1412300648782807040', '', '2021-07-06 15:46:05');
INSERT INTO `t_im_friend` VALUES ('1412316906331435008', '1412300648782807040', '1412316401152684032', '古力娜扎', '2021-07-06 15:46:05');
INSERT INTO `t_im_friend` VALUES ('1412319825864814592', '1412318851687378944', '1412300648782807040', '', '2021-07-06 15:57:41');
INSERT INTO `t_im_friend` VALUES ('1412319825864814593', '1412300648782807040', '1412318851687378944', '字节跳动客服', '2021-07-06 15:57:41');

-- ----------------------------
-- Table structure for t_im_friend_request
-- ----------------------------
DROP TABLE IF EXISTS `t_im_friend_request`;
CREATE TABLE `t_im_friend_request`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `from` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `to` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` int(0) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_im_friend_request
-- ----------------------------

-- ----------------------------
-- Table structure for t_im_media
-- ----------------------------
DROP TABLE IF EXISTS `t_im_media`;
CREATE TABLE `t_im_media`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_im_media
-- ----------------------------

-- ----------------------------
-- Table structure for t_im_message
-- ----------------------------
DROP TABLE IF EXISTS `t_im_message`;
CREATE TABLE `t_im_message`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `from` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `to` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` int(0) NOT NULL DEFAULT 0,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `signed` int(0) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_im_message
-- ----------------------------
INSERT INTO `t_im_message` VALUES ('1412311167701479424', '1412304972757909504', '1412300648782807040', 0, '我已通过你的好友请求', 1, '2021-07-06 15:23:17', '2021-07-06 15:23:17');
INSERT INTO `t_im_message` VALUES ('1412312795359866880', '1412311668396519424', '1412304972757909504', 0, '我已通过你的好友请求', 1, '2021-07-06 15:29:45', '2021-07-06 15:29:45');
INSERT INTO `t_im_message` VALUES ('1412312890658648064', '1412304972757909504', '1412311668396519424', 0, '你好', 1, '2021-07-06 15:30:07', '2021-07-06 15:30:07');
INSERT INTO `t_im_message` VALUES ('1412312941657190400', '1412311668396519424', '1412304972757909504', 0, '好呀', 1, '2021-07-06 15:30:19', '2021-07-06 15:30:19');
INSERT INTO `t_im_message` VALUES ('1412313212563091456', '1412304972757909504', '1412300648782807040', 0, 'hello', 1, '2021-07-06 15:31:24', '2021-07-06 15:32:17');
INSERT INTO `t_im_message` VALUES ('1412313479081750528', '1412300648782807040', '1412311668396519424', 0, '我已通过你的好友请求', 1, '2021-07-06 15:32:28', '2021-07-06 15:32:28');
INSERT INTO `t_im_message` VALUES ('1412313542847754240', '1412300648782807040', '1412304972757909504', 0, 'hi', 1, '2021-07-06 15:32:43', '2021-07-08 10:25:13');
INSERT INTO `t_im_message` VALUES ('1412315636552036352', '1412300648782807040', '1412314729240195072', 0, '我已通过你的好友请求', 1, '2021-07-06 15:41:02', '2021-07-06 15:41:02');
INSERT INTO `t_im_message` VALUES ('1412316906675367936', '1412300648782807040', '1412316401152684032', 0, '我已通过你的好友请求', 1, '2021-07-06 15:46:05', '2021-07-06 15:46:05');
INSERT INTO `t_im_message` VALUES ('1412319826313605120', '1412300648782807040', '1412318851687378944', 0, '我已通过你的好友请求', 1, '2021-07-06 15:57:41', '2021-07-06 15:57:41');

-- ----------------------------
-- Table structure for t_im_moment
-- ----------------------------
DROP TABLE IF EXISTS `t_im_moment`;
CREATE TABLE `t_im_moment`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `uid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `latitude` double NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `longitude` double NULL DEFAULT NULL,
  `visibility` int(0) NULL DEFAULT NULL,
  `fid_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_im_moment
-- ----------------------------

-- ----------------------------
-- Table structure for t_im_user
-- ----------------------------
DROP TABLE IF EXISTS `t_im_user`;
CREATE TABLE `t_im_user`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `face_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `face_imagehd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` int(0) NOT NULL DEFAULT 0,
  `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_qq9hn3x2wfc3kn71393t0lo4w`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_im_user
-- ----------------------------
INSERT INTO `t_im_user` VALUES ('1412300648782807040', 'vue', 'vue', '$2a$10$irm2r0Me19VRqj9.BITcjueH5TyBtzW/qQX2pQvSu5FbkmUovuIa.', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/59adaa86-cdd3-4a80-9f6a-0d86486f6309.png', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/59adaa86-cdd3-4a80-9f6a-0d86486f6309.png', 0, 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/qrcode/2021/07/06/c0b68a02-2356-4796-93d6-96a3e7fae81f.png', NULL, '2021-07-06 14:41:30', '2021-07-06 15:16:35');
INSERT INTO `t_im_user` VALUES ('1412304972757909504', 'react', 'react', '$2a$10$Jqg8ls4eg.GAxKufsZp7v.TT1e6B/VvUb7aB02bIu5KOxLn6qMQnm', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/10/b6addfce-43f6-48d3-95ed-23c34effab68.png', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/10/b6addfce-43f6-48d3-95ed-23c34effab68.png', 0, 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/qrcode/2021/07/06/e9e8f9cb-bc9b-412e-b9e7-f2b96f496721.png', NULL, '2021-07-06 14:58:40', '2021-07-14 18:09:15');
INSERT INTO `t_im_user` VALUES ('1412311668396519424', 'angular', 'angular', '$2a$10$lBVidsbmtuxOnqrx8ZIVo.QJRTpVoVryT3kFjDSXCjgUVx3GEzQQS', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/8b0f859d-dc63-4d3d-b5c4-a3a95b0d5239.png', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/8b0f859d-dc63-4d3d-b5c4-a3a95b0d5239.png', 0, 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/qrcode/2021/07/06/a502de43-4de1-4cf9-975b-9007dd1157da.png', NULL, '2021-07-06 15:25:17', '2021-07-06 15:29:04');
INSERT INTO `t_im_user` VALUES ('1412314729240195072', 'dilireba888', 'dilireba888', '$2a$10$.VvJrmY1Px2RnwJnJ8KddOxvoWjIdD2.DZtakJIRBlyS3KIEGLM9C', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/30b105f8-38f9-40ac-99a1-45b82fd4c3ae.png', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/30b105f8-38f9-40ac-99a1-45b82fd4c3ae.png', 0, 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/qrcode/2021/07/06/333892da-02fc-4480-bfdd-d06a9c525355.png', NULL, '2021-07-06 15:37:26', '2021-07-06 15:39:16');
INSERT INTO `t_im_user` VALUES ('1412316401152684032', 'gulinazha', 'gulinazha', '$2a$10$WJJQqwzr8xrSERzuG1c5ZONraSAfEdB5LKGcItG4oovomHO/glwyi', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/ed103174-01b5-44cc-bd7d-64a4b73b6d08.png', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/ed103174-01b5-44cc-bd7d-64a4b73b6d08.png', 0, 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/qrcode/2021/07/06/7d595af4-ae6e-4b41-ae40-b3eb741c1583.png', NULL, '2021-07-06 15:44:05', '2021-07-06 15:44:44');
INSERT INTO `t_im_user` VALUES ('1412318851687378944', 'bytedance', 'bytedance', '$2a$10$SstuCtcHYhS63ZwBujdJdOohDZxOeK4eQTMFu56cc.b4ZrRP7XfXa', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/329ac4f4-24de-4c6a-854a-62f99e5c68f3.png', 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/avatar/2021/07/06/329ac4f4-24de-4c6a-854a-62f99e5c68f3.png', 0, 'https://im-netty.oss-cn-guangzhou.aliyuncs.com/qrcode/2021/07/06/9c885d35-95b6-44a4-a711-5814f2521438.png', NULL, '2021-07-06 15:53:49', '2021-07-06 15:56:10');

SET FOREIGN_KEY_CHECKS = 1;
