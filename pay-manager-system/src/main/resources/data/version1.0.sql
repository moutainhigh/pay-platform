create table tb_pay_channel(
	id char(36) primary key ,
	channel_code varchar(50) UNIQUE comment '通道编码' ,
	channel_name varchar(50) comment '通道名称' ,
	cost_rate decimal(10,3) default 0 comment '成本费率' ,
	`is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
	create_time datetime comment '创建时间'
) comment '支付通道';



CREATE TABLE `tb_merchant` (
  `id` char(36) COLLATE utf8mb4_bin NOT NULL,
  `merchant_no` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商家编号',
  `merchant_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商户名称',
  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '真实姓名',
  `identity_code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证号码',
  `id_card_img1` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证-正面',
  `id_card_img2` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证-反面',
  `icp_img` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'icp证件',
  `merchant_secret` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商家密钥',
  `notifySecret` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '回调密钥,回调商户系统时,商户需要用该字段进行aes解密,16位密钥',
  `check_status` varchar(50) COLLATE utf8mb4_bin DEFAULT 'waitCheck' COMMENT '审核状态(waitCheck:待审核 success:审核通过 fail:审核失败)',
  `check_desc` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '审核备注',
  `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `merchant_no` (`merchant_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商家';


create table tb_merchant_rate(
	id char(36) primary key ,
	merchant_id char(36) comment '商家ID' ,
	channel_id char(36) comment '通道ID' ,
	rate decimal(10,3) comment '商家费率' default 0 ,
	create_time datetime comment '创建时间'
) comment '商家费率';


create table tb_order(
	id char(36) primary key ,
	merchant_order_no varchar(50) UNIQUE comment '商户订单号' ,
	platform_order_no varchar(50) UNIQUE comment '平台订单号' ,
	pay_code varchar(50) UNIQUE comment '第三方支付单号' ,
	goods_name varchar(255) comment '商品名称',
	order_amount decimal(10,2) comment '订单金额(元)' ,
	rate decimal(10,3) comment '费率' ,
	handling_fee  decimal(10,2) comment '手续费(元)',
	merchant_amount decimal(10,2) comment '商户金额(元)' ,
	merchant_id char(36) comment '商家ID' ,
	merchant_no varchar(255) comment '商家编号',
	channel_id char(36) comment '通道ID' ,
	pay_way varchar(50) comment '支付方式(zfbScanCode:支付宝扫码支付 zfbH5:支付宝h5支付 wxScanCode:微信扫码支付 wxH5:微信H5支付) ' ,
    pay_status varchar(50) default 'waitPay' comment '支付状态(waitPay:待支付 payed:已支付 payFail:支付失败)',
	pay_time datetime comment '支付时间' ,
	notify_url varchar(500) comment '商户回调地址' ,
	notify_status varchar(500) comment '回调商户状态(notNotify:未通知 notifyed:已通知,并成功收到了商户反馈)' ,
	notify_num int default 0 default 0 comment '通知次数' ,
	create_time datetime comment '创建时间'
) comment '订单';
