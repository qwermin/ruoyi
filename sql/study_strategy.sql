-- ----------------------------
-- 学习攻略表
-- ----------------------------
DROP TABLE IF EXISTS `study_strategy`;
CREATE TABLE `study_strategy` (
  `strategy_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '攻略ID',
  `strategy_code` varchar(64) NOT NULL COMMENT '攻略编码',
  `strategy_name` varchar(100) NOT NULL COMMENT '攻略名称',
  `strategy_url` varchar(500) DEFAULT '' COMMENT '攻略地址链接',
  `strategy_type` varchar(20) DEFAULT '' COMMENT '攻略类型',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习攻略表';

-- ----------------------------
-- 初始化数据 (使用新编码格式: ST + YYYYMMDD + 4位数字)
-- ----------------------------
INSERT INTO `study_strategy` VALUES (1, 'ST202602120001', 'Vue3学习攻略', 'https://vuejs.org/guide/introduction.html', 'frontend', 'admin', NOW(), 'admin', NOW(), '0');
INSERT INTO `study_strategy` VALUES (2, 'ST202602120002', 'Spring Boot学习攻略', 'https://spring.io/projects/spring-boot', 'backend', 'admin', NOW(), 'admin', NOW(), '0');
INSERT INTO `study_strategy` VALUES (3, 'ST202602120003', 'Element Plus学习攻略', 'https://element-plus.org/zh-CN/', 'frontend', 'admin', NOW(), 'admin', NOW(), '0');
