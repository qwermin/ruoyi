-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '物品ID',
  `item_code` varchar(20) DEFAULT '' COMMENT '物品编码',
  `name` varchar(100) DEFAULT '' COMMENT '物品名称',
  `item_type` char(1) DEFAULT '0' COMMENT '物品种类（0=实体物品,1=虚拟物品）',
  `image_path` varchar(500) DEFAULT NULL COMMENT '图片路径',
  `purchase_price` decimal(10,2) DEFAULT NULL COMMENT '购买价格',
  `purchase_time` datetime DEFAULT NULL COMMENT '购买时间',
  `sell_price` decimal(10,2) DEFAULT NULL COMMENT '售出价格',
  `sell_time` datetime DEFAULT NULL COMMENT '售出时间',
  `price_difference` decimal(10,2) DEFAULT NULL COMMENT '差价',
  `profit_loss` decimal(10,2) DEFAULT NULL COMMENT '盈亏',
  `estimated_value` decimal(10,2) DEFAULT NULL COMMENT '估价',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `idx_item_code` (`item_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='物品表';

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, 'MH202602110001', '测试实体物品', '0', '/profile/upload/item_images/test1.jpg', 100.00, '2026-02-11 10:00:00', 150.00, '2026-02-11 15:00:00', 50.00, 50.00, 120.00, '0', 'admin', NOW(), 'admin', NOW(), '这是一个实体物品');
INSERT INTO `item` VALUES (2, 'MH202602110002', '测试虚拟物品', '1', '/profile/upload/item_images/test2.jpg', 200.00, '2026-02-11 11:00:00', 180.00, '2026-02-11 16:00:00', -20.00, -20.00, 190.00, '0', 'admin', NOW(), 'admin', NOW(), '这是一个虚拟物品');