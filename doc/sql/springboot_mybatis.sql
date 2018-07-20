
-- ----------------------------
-- Table structure for open_api_account_config
-- ----------------------------
DROP TABLE IF EXISTS `open_api_account_config`;
CREATE TABLE `open_api_account_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL COMMENT '账号',
  `api_key` varchar(50) NOT NULL COMMENT '秘钥',
  `api_urls` varchar(3000) NOT NULL COMMENT '允许访问的接口',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账号是否可用(默认是可用的)',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_unique` (`account`) USING BTREE,
  KEY `idx_account_disabled` (`account`,`enabled`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of open_api_account_config
-- ----------------------------
INSERT INTO `open_api_account_config` VALUES ('1', 'test1', 'daaefeae', '/bigClass/outInterface/user/findById', '1', '2018-07-20 17:53:00', '2018-07-20 17:53:00');

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
