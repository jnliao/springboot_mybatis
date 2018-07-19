

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_idx` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'aaa@163.com', '22', '2018-07-18 13:18:22');
INSERT INTO `user` VALUES ('2', 'bbb@163.com', '23', '2018-07-18 14:41:03');
INSERT INTO `user` VALUES ('3', 'ccc@163.com', '24', '2018-07-18 14:41:36');
SET FOREIGN_KEY_CHECKS=1;
