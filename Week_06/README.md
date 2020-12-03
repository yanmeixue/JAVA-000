## 作业
基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github

 ```sql
-- 用户
CREATE TABLE `user_login` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(20) NOT NULL COMMENT '用户登录名',
  `password` char(32) NOT NULL COMMENT 'md5加密的密码',
  `user_status` tinyint NOT NULL DEFAULT '1' COMMENT '用户状态',
  `modified_time` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户登陆信息表';

CREATE TABLE `user_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL COMMENT 'user_login表的自增ID',
  `user_real_name` varchar(20) NOT NULL COMMENT '用户真实姓名',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `identity_card_type` tinyint NOT NULL DEFAULT '1' COMMENT '证件类型：1 身份证，2 军官证，3 护照',
  `identity_card_no` varchar(20) NOT NULL COMMENT '证件号码',
  `mobile_phone` int unsigned NOT NULL COMMENT '手机号',
  `user_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `register_time` bigint NOT NULL COMMENT '注册时间',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `user_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '用户余额',
  `modified_time` bigint NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户详细信息表';

CREATE TABLE `user_addr` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `user_id` bigint unsigned NOT NULL COMMENT 'user_login表的自增ID',
  `zip_code` smallint NOT NULL COMMENT '邮编',
  `province` smallint NOT NULL COMMENT '地区表中省份的ID',
  `city` smallint NOT NULL COMMENT '地区表中城市的ID',
  `district` smallint NOT NULL COMMENT '地区表中的区ID',
  `address` varchar(255) NOT NULL COMMENT '具体的地址门牌号',
  `is_default` tinyint NOT NULL COMMENT '是否默认',
  `modified_time` bigint NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户地址表';

-- 商品
CREATE TABLE `product` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_sn` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品编码',
  `product_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `price` decimal(8,2) NOT NULL COMMENT '商品价格',
  `cost` decimal(8,2) NOT NULL COMMENT '商品成本',
  `current_cnt` int NOT NULL COMMENT '商品库存',
  `lock_cnt` int NOT NULL COMMENT '商品锁定数量',
  `publish_status` tinyint NOT NULL DEFAULT '0' COMMENT '上下架状态：0下架1上架',
  `weight` decimal(6,2) DEFAULT NULL COMMENT '商品重量',
  `length` decimal(6,2) DEFAULT NULL COMMENT '商品长度',
  `height` decimal(6,2) DEFAULT NULL COMMENT '商品高度',
  `width` decimal(6,2) DEFAULT NULL COMMENT '商品宽度',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品描述',
  `create_time` bigint NOT NULL COMMENT '商品录入时间',
  `modified_time` bigint NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品信息表';

CREATE TABLE `product_pic` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '商品图片ID',
  `product_id` bigint unsigned NOT NULL COMMENT '商品ID',
  `pic_desc` varchar(50) DEFAULT NULL COMMENT '图片描述',
  `pic_url` varchar(255) NOT NULL COMMENT '图片URL',
  `is_master` tinyint NOT NULL DEFAULT '0' COMMENT '是否主图：0.非主图1.主图',
  `pic_order` tinyint NOT NULL DEFAULT '0' COMMENT '图片排序',
  `pic_status` tinyint NOT NULL DEFAULT '1' COMMENT '图片是否有效：0无效 1有效',
  `modified_time` bigint NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品图片信息表';

-- 订单
CREATE TABLE `order_main` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_sn` bigint unsigned NOT NULL COMMENT '订单编号',
  `user_id` bigint unsigned NOT NULL COMMENT '下单人ID',
  `shipping_name` varchar(50) NOT NULL COMMENT '收货人姓名',
  `province` smallint NOT NULL COMMENT '省',
  `city` smallint NOT NULL COMMENT '市',
  `district` smallint NOT NULL COMMENT '区',
  `address` varchar(100) NOT NULL COMMENT '地址',
  `payment_method` tinyint NOT NULL COMMENT '支付方式：1现金，2余额，3网银，4支付宝，5微信',
  `order_money` decimal(8,2) NOT NULL COMMENT '订单金额',
  `shipping_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '运费金额',
  `payment_money` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `shipping_comp_name` varchar(20) DEFAULT NULL COMMENT '快递公司名称',
  `shipping_sn` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `create_time` bigint NOT NULL COMMENT '下单时间',
  `shipping_time` datetime DEFAULT NULL COMMENT '发货时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `order_status` tinyint NOT NULL DEFAULT '0' COMMENT '订单状态',
  `modified_time` bigint NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单主表';

CREATE TABLE `order_detail` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '订单详情表ID',
  `order_id` bigint unsigned NOT NULL COMMENT '订单表ID',
  `product_id` bigint unsigned NOT NULL COMMENT '订单商品ID',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `product_cnt` int NOT NULL DEFAULT '1' COMMENT '购买商品数量',
  `product_price` decimal(8,2) NOT NULL COMMENT '购买商品单价',
  `product_cost` decimal(8,2) NOT NULL COMMENT '商品成本',
  `modified_time` bigint NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单详情表';
 ```
