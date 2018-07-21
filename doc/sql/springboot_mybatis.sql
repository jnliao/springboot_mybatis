
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
INSERT INTO `open_api_account_config` VALUES ('1', 'test1', 'cf23732a32fd874edcaba875ab5091d1', '/bigClass/outInterface/user/findById', '1', '2018-07-21 11:13:20', '2018-07-21 11:13:20');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
`email` varchar(100) DEFAULT NULL,
`age` int(11) DEFAULT NULL,
`create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
`account` varchar(100) DEFAULT NULL COMMENT '账号',
`password` varchar(100) DEFAULT NULL COMMENT '密码',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
PRIMARY KEY (`id`),
UNIQUE KEY `email_idx` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'aaa@163.com', '22', '2018-07-21 18:26:24', 'aaa@163.com', '$2a$10$wG2lFJpNaRpYaERmbrkCnuQRbbX2KORATfosKoOuqeyRyaV32uz9a', '2018-07-21 18:26:24');
INSERT INTO `user` VALUES ('2', 'bbb@163.com', '23', '2018-07-21 18:26:29', 'bbb@163.com', '$2a$10$C2MGsG6f.30dIWjBrDgP9eOB9e1uAXBvIyb0uUuOSkuwy2ytJtooC', '2018-07-21 18:26:29');
INSERT INTO `user` VALUES ('3', 'ccc@163.com', '24', '2018-07-21 18:24:55', 'ccc@163.com', '$2a$10$Kb0nMm418bEK3tfNKlZwou/rhrybYYW98sRuH9luOxonZ1Odv9yqO', '2018-07-21 18:24:55');
SET FOREIGN_KEY_CHECKS=1;
