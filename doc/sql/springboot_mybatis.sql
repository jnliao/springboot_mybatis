
-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `module_code` varchar(50) NOT NULL COMMENT '模块编码',
  `module_name` varchar(50) NOT NULL COMMENT '模块名称',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，默认为否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code_unique` (`module_code`) USING BTREE,
  KEY `idx_code_deleted` (`module_code`,`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统模块表';

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('1', 'user', '用户模块', '2018-07-24 15:42:23', '2018-07-24 15:42:26', '0');

-- ----------------------------
-- Table structure for open_api_config
-- ----------------------------
DROP TABLE IF EXISTS `open_api_config`;
CREATE TABLE `open_api_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL COMMENT '账号',
  `api_key` varchar(50) NOT NULL COMMENT '秘钥',
  `api_urls` varchar(3000) NOT NULL COMMENT '允许访问的接口',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，默认为否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_unique` (`account`) USING BTREE,
  KEY `idx_account_deleted` (`account`,`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='对外接口权限配置表';

-- ----------------------------
-- Records of open_api_config
-- ----------------------------
INSERT INTO `open_api_config` VALUES ('1', 'test1', 'cf23732a32fd874edcaba875ab5091d1', '/bigClass/outInterface/user/findById', '2018-07-24 14:08:06', '2018-07-24 14:08:06', '0');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `module_id` bigint(20) NOT NULL COMMENT '模块id',
  `permission_code` varchar(100) NOT NULL COMMENT '权限编码',
  `permission_name` varchar(100) NOT NULL COMMENT '权限名称',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，默认为否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code_unique` (`permission_code`) USING BTREE,
  KEY `idx_code_deleted` (`permission_code`,`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '1', 'user.search', '查询用户', '2018-07-24 17:58:06', '2018-07-24 17:58:06', '0');
INSERT INTO `permission` VALUES ('2', '1', 'user.add', '新增用户', '2018-07-24 15:49:56', '2018-07-24 15:50:00', '0');
INSERT INTO `permission` VALUES ('3', '1', 'user.update', '修改用户', '2018-07-24 15:50:36', '2018-07-24 15:50:39', '0');
INSERT INTO `permission` VALUES ('4', '1', 'user.delete', '删除用户', '2018-07-24 15:51:19', '2018-07-24 15:51:23', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，默认为否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code_unique` (`role_code`) USING BTREE,
  KEY `idx_code_deleted` (`role_code`,`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '管理员', '2018-07-24 15:41:37', '2018-07-24 15:41:40', '0');

-- ----------------------------
-- Table structure for role_permission_rel
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_rel`;
CREATE TABLE `role_permission_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_role_permission_unique` (`role_id`,`permission_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE,
  KEY `idx_pernission_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';

-- ----------------------------
-- Records of role_permission_rel
-- ----------------------------
INSERT INTO `role_permission_rel` VALUES ('1', '1', '1', '2018-07-24 15:52:02');
INSERT INTO `role_permission_rel` VALUES ('2', '1', '2', '2018-07-24 15:52:21');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nick_name` varchar(50) NOT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，默认为否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_unique` (`account`) USING BTREE,
  KEY `idx_account_deleted` (`account`,`is_deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'aaa@163.com', '$2a$10$wG2lFJpNaRpYaERmbrkCnuQRbbX2KORATfosKoOuqeyRyaV32uz9a', 'aaa', 'aaa@163.com', '2018-07-24 16:13:54', '2018-07-24 16:13:54', '0');
INSERT INTO `user` VALUES ('2', 'bbb@163.com', '$2a$10$C2MGsG6f.30dIWjBrDgP9eOB9e1uAXBvIyb0uUuOSkuwy2ytJtooC', 'bbb', 'bbb@163.com', '2018-07-24 14:07:44', '2018-07-24 14:07:44', '0');
INSERT INTO `user` VALUES ('3', 'ccc@163.com', '$2a$10$Kb0nMm418bEK3tfNKlZwou/rhrybYYW98sRuH9luOxonZ1Odv9yqO', 'ccc', 'ccc@163.com', '2018-07-24 14:18:55', '2018-07-24 14:18:55', '0');

-- ----------------------------
-- Table structure for user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `user_role_rel`;
CREATE TABLE `user_role_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_role_unique` (`user_id`,`role_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Records of user_role_rel
-- ----------------------------
INSERT INTO `user_role_rel` VALUES ('1', '1', '1', '2018-07-24 15:48:40');
SET FOREIGN_KEY_CHECKS=1;
