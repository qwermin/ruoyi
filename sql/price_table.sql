-- ----------------------------
-- Table structure for price
-- ----------------------------
DROP TABLE IF EXISTS `price`;
CREATE TABLE `price` (
  `price_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `item_name` varchar(100) NOT NULL DEFAULT '' COMMENT '物品名称',
  `server_name` varchar(100) NOT NULL DEFAULT '' COMMENT '服务器',
  `item_type` varchar(50) NOT NULL DEFAULT '' COMMENT '物品类型',
  `item_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '物品金额',
  `converted_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '换算金额',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`price_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物价表';

-- ----------------------------
-- Menu and permissions for price
-- ----------------------------
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('物价管理', 0, 4, 'price', 'business/price/index', NULL, 'Price', 1, 0, 'C', '0', '0', 'business:price:list', 'money', 'admin', sysdate(), '物价管理菜单');

SET @parentId = LAST_INSERT_ID();

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('物价查询', @parentId, 1, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'business:price:query', '#', 'admin', sysdate(), '');
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('物价新增', @parentId, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'business:price:add', '#', 'admin', sysdate(), '');
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('物价修改', @parentId, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'business:price:edit', '#', 'admin', sysdate(), '');
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('物价删除', @parentId, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'business:price:remove', '#', 'admin', sysdate(), '');
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('物价导出', @parentId, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'business:price:export', '#', 'admin', sysdate(), '');
