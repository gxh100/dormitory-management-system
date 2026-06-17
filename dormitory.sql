/*
Navicat MySQL Data Transfer
Source Server         : localhost
Source Database       : dormitory
Target Server Type    : MYSQL
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for absent
-- ----------------------------
DROP TABLE IF EXISTS `absent`;
CREATE TABLE `absent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `building_id` int(11) DEFAULT NULL,
  `dormitory_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `dormitory_admin_id` int(11) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  `reason` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

INSERT INTO `absent` VALUES ('13', '1', '2', '5', '16', '2025-09-30 16:30:08', '身体不舒服，回家修养了');
INSERT INTO `absent` VALUES ('15', '2', '5', '76', '33', '2025-10-10 14:11:10', '出去玩了，暂时回不去');
INSERT INTO `absent` VALUES ('20', '1', '1', '2', '16', '2025-09-30 15:24:28', '找同学玩去了，规定时间没回宿舍');
INSERT INTO `absent` VALUES ('21', '2', '4', '17', '29', '2025-09-30 15:26:13', '家里有事儿，回老家了');
INSERT INTO `absent` VALUES ('24', '2', '4', '13', '2', '2025-10-10 14:05:40', '家里有事儿，回老家了');
INSERT INTO `absent` VALUES ('25', '1', '3', '1', '2', '2025-12-23 21:25:32', '出去约会了');
INSERT INTO `absent` VALUES ('26', '1', '1', '2', '16', '2025-12-30 00:00:00', 'asdada');

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `introduction` varchar(1000) DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

INSERT INTO `building` VALUES ('1', '1号楼', '计算机学院宿舍一号楼', '16');
INSERT INTO `building` VALUES ('2', '2号楼', '计算机学院宿舍二号楼', '16');
INSERT INTO `building` VALUES ('17', '6号楼', '大数据宿舍一号楼', '2');
INSERT INTO `building` VALUES ('24', '12号楼', '大数据专业专用宿舍楼', '16');
INSERT INTO `building` VALUES ('25', '5号宿舍楼', '新建的豪华宿舍楼，单人单间', '16');

-- ----------------------------
-- Table structure for dormitory
-- ----------------------------
DROP TABLE IF EXISTS `dormitory`;
CREATE TABLE `dormitory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `building_id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT '0' COMMENT '默认0',
  `telephone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

INSERT INTO `dormitory` VALUES ('1', '1', '101', '4', '0', '88230001');
INSERT INTO `dormitory` VALUES ('2', '21', '1102', '2', '2', '13811111111');
INSERT INTO `dormitory` VALUES ('3', '1', '211', '4', '0', '88230007');
INSERT INTO `dormitory` VALUES ('4', '2', '212', '6', '1', '88230008');
INSERT INTO `dormitory` VALUES ('5', '2', '321', '8', '6', '88230013');
INSERT INTO `dormitory` VALUES ('6', '2', '322', '10', '10', '88230016');
INSERT INTO `dormitory` VALUES ('39', '19', '1001', '5', '3', '01118881');
INSERT INTO `dormitory` VALUES ('42', '17', '888', '2', '2', '13688888888');

-- ----------------------------
-- Table structure for moveout
-- ----------------------------
DROP TABLE IF EXISTS `moveout`;
CREATE TABLE `moveout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(11) DEFAULT NULL,
  `dormitory_id` varchar(50) DEFAULT NULL,
  `reason` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

INSERT INTO `moveout` VALUES ('4', '4', '39', '毕业了，啦啦啦', '2025-10-09 13:36:39');
INSERT INTO `moveout` VALUES ('5', '5', '2', '已经毕业了', '2022-04-14');
INSERT INTO `moveout` VALUES ('15', '63', '6', '休学一段时间，暂时离开学校，不在住宿', '2025-09-28 16:15:25');
INSERT INTO `moveout` VALUES ('17', '18', '4', '毕业', '2022-04-27');
INSERT INTO `moveout` VALUES ('24', '2', '1', '已毕业！', '2025-09-26');
INSERT INTO `moveout` VALUES ('26', '3', '1', '已成功毕业！不需要再住校了', '2025-09-27 14:00:00');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `dormitory_id` int(11) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `create_date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

INSERT INTO `student` VALUES ('1', '2025006', '王伟', '女', '3', '入住', '2025-09-29 13:34:35');
INSERT INTO `student` VALUES ('2', '002', '曹海', '男', '1', '迁出', '2022-04-14');
INSERT INTO `student` VALUES ('3', '003', '李力', '男', '1', '迁出', '2022-04-14');
INSERT INTO `student` VALUES ('4', '004', '赵昭', '男', '39', '迁出', '2022-04-14');
INSERT INTO `student` VALUES ('5', '005', '王维利', '男', '2', '迁出', '2022-04-14');
INSERT INTO `student` VALUES ('6', '006', '李双', '女', '2', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('7', '0077', '李易峰', '女', '36', '迁出', '2022-04-18');
INSERT INTO `student` VALUES ('8', '008', '孙奇', '男', '2', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('9', '009', '李立', '女', '3', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('11', '011', '赵正义', '男', '3', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('12', '012', '李明', '男', '3', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('13', '013', '许鹏飞', '男', '4', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('14', '014', '朱海', '男', '4', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('15', '015', '苏苏苏', '男', '4', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('16', '016', '李雪', '女', '4', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('17', '017', '李爽', '女', '4', '入住', '2022-04-14');
INSERT INTO `student` VALUES ('18', '018', '王纯', '女', '4', '迁出', '2022-04-14');
INSERT INTO `student` VALUES ('63', '019', '小明', '男', '5', '迁出', '2022-04-17');
INSERT INTO `student` VALUES ('76', '2025007', '张三', '男', '5', '入住', '2025-09-29 13:28:38');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `flag` int(11) NOT NULL DEFAULT '0' COMMENT '0：宿管  1：系统管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', 'songyu', '1234567890', '宋玉', '女', '13312345678', '0');
INSERT INTO `user` VALUES ('2', 'wwl', '123123', '王力', '男', '13612345678', '0');
INSERT INTO `user` VALUES ('16', 'zzl', '123123', '张三', '女', '13312345678', '0');
INSERT INTO `user` VALUES ('19', 'admin', '123456', '王五', '男', '13312345678', '1');
INSERT INTO `user` VALUES ('29', 'xiaowang', '666666', '王二', '女', '13511111188', '0');
INSERT INTO `user` VALUES ('37', 'xiaoli', '123456', '小李子', '男', '13131313', '0');
INSERT INTO `user` VALUES ('38', 'xiaozhang', '131313', '张三', '男', 'qqeqeqeq', '0');
INSERT INTO `user` VALUES ('41', 'xiaozhang', 'qeqeqe', 'zdadad', '男', '1313131', '0');
INSERT INTO `user` VALUES ('43', 'xiaoliu', '1313111', '小六', '男', '13511111111', '1');
INSERT INTO `user` VALUES ('44', 'admin22', '123456', '管理员', '男', '13522222222', '1');

SET FOREIGN_KEY_CHECKS=1;