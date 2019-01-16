/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : pay-platform-test

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 01/16/2019 15:01:32 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary`;
CREATE TABLE `tb_dictionary` (
  `id` char(36) COLLATE utf8_bin NOT NULL,
  `dict_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '字典类型',
  `dict_desc` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '字典描述',
  `item_code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类码',
  `item_desc` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类描述',
  `parent_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点id',
  `level` tinyint(4) DEFAULT NULL COMMENT '级别',
  `seq` int(11) DEFAULT NULL COMMENT '排序字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';

-- ----------------------------
--  Records of `tb_dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `tb_dictionary` VALUES ('001', '', '字典列表', null, null, null, '0', '1', '2016-10-20 22:51:15', '0', null), ('03fe5164-d4ef-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'singleImage', '单图片', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '3', '2017-01-07 23:36:11', '0', null), ('081ee934-d4ef-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'multiImage', '多图片', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '4', '2017-01-07 23:36:17', '0', null), ('22da831c-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'radio', '单选按钮', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '5', '2017-02-05 13:51:28', '0', null), ('278386e8-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'checkbox', '复选框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '6', '2017-02-05 13:51:35', '0', null), ('2b9f4e10-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'select', '下拉框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '7', '2017-02-05 13:51:42', '0', null), ('3f2a7eb8-161d-11e9-9b0a-810fc5b125a5', 'payWay', '支付方式', null, null, '001', '1', '17', '2019-01-12 11:50:48', '0', null), ('4034594a-165a-11e9-9b0a-810fc5b125a5', 'notifyStatus', '回调状态', null, null, '001', '1', '19', '2019-01-12 19:07:29', '0', null), ('461be90e-165a-11e9-9b0a-810fc5b125a5', 'notifyStatus', '回调状态', 'notNotify', '未通知', '4034594a-165a-11e9-9b0a-810fc5b125a5', '2', '1', '2019-01-12 19:07:39', '0', null), ('4794bc44-161d-11e9-9b0a-810fc5b125a5', 'payWay', '支付方式', 'zfbScanCode', '支付宝扫码支付', '3f2a7eb8-161d-11e9-9b0a-810fc5b125a5', '2', '1', '2019-01-12 11:51:02', '0', null), ('4d3c5d00-161d-11e9-9b0a-810fc5b125a5', 'payWay', '支付方式', 'zfbH5', '支付宝h5支付', '3f2a7eb8-161d-11e9-9b0a-810fc5b125a5', '2', '2', '2019-01-12 11:51:11', '0', null), ('531a29be-161d-11e9-9b0a-810fc5b125a5', 'payWay', '支付方式', 'wxScanCode', '微信扫码支付', '3f2a7eb8-161d-11e9-9b0a-810fc5b125a5', '2', '3', '2019-01-12 11:51:21', '0', null), ('599c5266-165a-11e9-9b0a-810fc5b125a5', 'notifyStatus', '回调状态', 'notifyed', '已通知', '4034594a-165a-11e9-9b0a-810fc5b125a5', '2', '2', '2019-01-12 19:08:11', '0', null), ('5ae5e322-161d-11e9-9b0a-810fc5b125a5', 'payWay', '支付方式', 'wxH5', '微信H5支付', '3f2a7eb8-161d-11e9-9b0a-810fc5b125a5', '2', '4', '2019-01-12 11:51:34', '0', null), ('76d5b626-17d9-11e9-8af9-acab529a107f', 'notifyStatus', '回调状态', 'success', '通知成功', '4034594a-165a-11e9-9b0a-810fc5b125a5', '2', '3', '2019-01-12 19:08:11', '0', null), ('83d1fb1e-ec66-11e6-9f79-67b34f704711', 'formType', '表单类型', 'richText', '富文本', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '8', '2017-02-06 20:19:32', '0', null), ('9021487e-161d-11e9-9b0a-810fc5b125a5', 'payStatus', '支付状态', null, null, '001', '1', '18', '2019-01-12 11:53:03', '0', null), ('95ae6e34-161d-11e9-9b0a-810fc5b125a5', 'payStatus', '支付状态', 'waitPay', '待支付', '9021487e-161d-11e9-9b0a-810fc5b125a5', '2', '1', '2019-01-12 11:53:13', '0', null), ('9ebfba1e-161d-11e9-9b0a-810fc5b125a5', 'payStatus', '支付状态', 'payed', '已支付', '9021487e-161d-11e9-9b0a-810fc5b125a5', '2', '2', '2019-01-12 11:53:28', '0', null), ('a51fa7ac-161d-11e9-9b0a-810fc5b125a5', 'payStatus', '支付状态', 'payFail', '支付失败', '9021487e-161d-11e9-9b0a-810fc5b125a5', '2', '3', '2019-01-12 11:53:39', '0', null), ('bbc7628a-158a-11e9-adea-1631b6fccd0d', 'payChannelType', '支付通道', null, null, '001', '1', '16', '2019-01-11 18:22:01', '1', '2019-01-11 18:23:29'), ('d7bbf992-158a-11e9-adea-1631b6fccd0d', 'payChannelType', '支付通道', 'youFuGlobal', '优赋全球', 'bbc7628a-158a-11e9-adea-1631b6fccd0d', '2', '1', '2019-01-11 18:22:48', '1', '2019-01-11 18:23:29'), ('e714f7ce-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', null, null, '001', '1', '15', '2017-01-07 23:35:22', '0', null), ('ef44cc12-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'text', '文本框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '1', '2017-01-07 23:35:36', '0', null), ('fdd5905e-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'datetime', '日期', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '2', '2017-01-07 23:36:00', '0', null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_merchant`
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant`;
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

-- ----------------------------
--  Records of `tb_merchant`
-- ----------------------------
BEGIN;
INSERT INTO `tb_merchant` VALUES ('cab9e132-1611-11e9-9b0a-810fc5b12535', '10011', '大头001', '18617369762', 'asd1', 'asd', '/resources/upload//merchant/2019-01/5472fdd7-78d3-4943-a46c-9c2b1b6bbd62.jpg', '/resources/upload//merchant/2019-01/7bfd9c2b-097e-48ce-99d2-00dafaee5623.jpg', '/resources/upload//merchant/2019-01/457d82d6-a2e6-45d3-9932-d5f682ed3ad9.jpg', 'asd23123xc33', '3125484sd5czx6aw', 'waitCheck', null, '0', '2019-01-12 10:28:48'), ('cab9e132-1611-11e9-9b0a-810fc5b125a5', '10086', '大头001', '18617369765', 'asd', 'asd', '/resources/upload//merchant/2019-01/5472fdd7-78d3-4943-a46c-9c2b1b6bbd62.jpg', '/resources/upload//merchant/2019-01/7bfd9c2b-097e-48ce-99d2-00dafaee5623.jpg', '/resources/upload//merchant/2019-01/457d82d6-a2e6-45d3-9932-d5f682ed3ad9.jpg', 'asd23123xc', '6625484sd5czx6aw', 'waitCheck', null, '0', '2019-01-12 10:28:48');
COMMIT;

-- ----------------------------
--  Table structure for `tb_merchant_rate`
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant_rate`;
CREATE TABLE `tb_merchant_rate` (
  `id` char(36) COLLATE utf8mb4_bin NOT NULL,
  `merchant_id` char(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商家ID',
  `channel_id` char(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道ID',
  `rate` decimal(10,3) DEFAULT '0.000' COMMENT '商家费率',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商家费率';

-- ----------------------------
--  Table structure for `tb_operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log` (
  `id` char(36) COLLATE utf8_bin NOT NULL,
  `user_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '操作用户id',
  `user_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作用户名',
  `ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '操作用户ip',
  `module` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作模块',
  `operation` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '执行操作',
  `description` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（0:失败 1:成功）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作日志表';

-- ----------------------------
--  Records of `tb_operation_log`
-- ----------------------------
BEGIN;
INSERT INTO `tb_operation_log` VALUES ('0116672a-15a7-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 21:44:23'), ('03e6e488-1585-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '角色管理', '授予角色权限', 'com.pay.platform.modules.sysmgr.role.controller.RoleController.grantPermission()', '1', '2019-01-11 17:41:05'), ('0435a5be-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '新增权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.addPermission()', '1', '2019-01-12 10:23:15'), ('09b76112-165c-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '新增权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.addPermission()', '1', '2019-01-12 19:20:16'), ('0c6a60f0-15a6-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '修改通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.updatePayChannel()', '1', '2019-01-11 21:37:32'), ('0c6b4654-1660-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:48:59'), ('0c9b2428-1660-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:48:59'), ('0cfd5aac-165c-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '角色管理', '授予角色权限', 'com.pay.platform.modules.sysmgr.role.controller.RoleController.grantPermission()', '1', '2019-01-12 19:20:22'), ('11ec085a-165d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:27:39'), ('1202b8ca-165d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:27:40'), ('122ee260-165d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:27:40'), ('1393e1e6-165d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:27:42'), ('13cbb6ac-165d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:27:43'), ('13cfdba2-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '新增权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.addPermission()', '1', '2019-01-12 10:23:41'), ('17cf288e-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '角色管理', '授予角色权限', 'com.pay.platform.modules.sysmgr.role.controller.RoleController.grantPermission()', '1', '2019-01-12 10:23:48'), ('1dee2a70-1586-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:48:58'), ('209a4cb6-165a-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '修改字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.updateDictionary()', '1', '2019-01-12 19:06:36'), ('226e4a84-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 11:49:59'), ('239f7d16-1586-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:49:07'), ('27e32ed8-165b-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:13:57'), ('2ee94da6-1585-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '修改权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.updatePermission()', '1', '2019-01-11 17:42:17'), ('32f6297c-1612-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '商家管理', '删除商家', 'com.pay.platform.modules.merchant.controller.MerchantController.deleteMerchantByLogic()', '1', '2019-01-12 10:31:43'), ('3cc51d3c-1612-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '商家管理', '删除商家', 'com.pay.platform.modules.merchant.controller.MerchantController.deleteMerchantByLogic()', '1', '2019-01-12 10:31:59'), ('3f2be848-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:50:48'), ('4034fcf6-165a-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 19:07:29'), ('40dd1e42-158b-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '新增通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.addPayChannel()', '1', '2019-01-11 18:25:44'), ('41b3b186-15a5-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '新增通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.addPayChannel()', '1', '2019-01-11 21:31:52'), ('4313b0ba-1589-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 18:11:29'), ('45af187c-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:43:58'), ('461cd378-165a-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 19:07:39'), ('475c9e4c-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:44:00'), ('4795a528-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:51:02'), ('49ca2956-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:44:05'), ('4b038e02-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:44:07'), ('4d3d43aa-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:51:11'), ('4f75a548-1584-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:36:02'), ('531aed68-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:51:21'), ('599d38f2-165a-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 19:08:11'), ('5ae697e0-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:51:34'), ('65a94f1e-165a-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:08:31'), ('69bb637e-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '删除字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.deleteDictionary()', '1', '2019-01-11 14:44:58'), ('69f163ce-158b-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '删除通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.deletePayChannelByLogic()', '1', '2019-01-11 18:26:53'), ('6b1439e4-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '删除字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.deleteDictionary()', '1', '2019-01-11 14:45:00'), ('6c5cce68-1582-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:22:31'), ('6c92f35a-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '删除字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.deleteDictionary()', '1', '2019-01-11 14:45:03'), ('7872dff0-158a-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 18:20:08'), ('7e6a6d1e-1586-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:51:40'), ('8c4297f2-15a6-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '新增通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.addPayChannel()', '1', '2019-01-11 21:41:07'), ('8deb15ea-1586-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '角色管理', '授予角色权限', 'com.pay.platform.modules.sysmgr.role.controller.RoleController.grantPermission()', '1', '2019-01-11 17:52:06'), ('902219fc-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:53:03'), ('95af26ee-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:53:13'), ('98e87ba8-15a5-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '删除通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.deletePayChannelByLogic()', '1', '2019-01-11 21:34:19'), ('9bdcfef6-15a5-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '修改通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.updatePayChannel()', '1', '2019-01-11 21:34:24'), ('9ec0ab0e-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:53:28'), ('a5207b32-161d-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-12 11:53:39'), ('af0e013c-15a5-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '修改通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.updatePayChannel()', '1', '2019-01-11 21:34:56'), ('b23b280c-15a6-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '删除通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.deletePayChannelByLogic()', '1', '2019-01-11 21:42:11'), ('b264d9be-1613-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '商家管理', '修改商家', 'com.pay.platform.modules.merchant.controller.MerchantController.updateMerchant()', '1', '2019-01-12 10:42:26'), ('b3ce21aa-156b-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '角色管理', '删除角色', 'com.pay.platform.modules.sysmgr.role.controller.RoleController.deleteRole()', '1', '2019-01-11 14:39:53'), ('b8423522-1585-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:46:07'), ('bbc81824-158a-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-11 18:22:01'), ('bd33b8fa-165a-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:10:58'), ('bea8ed1e-1613-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '文件上传', '删除图片', 'com.pay.platform.modules.common.controller.FileUploadController.virtualDeleteFile()', '1', '2019-01-12 10:42:47'), ('c1079398-165a-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 19:11:05'), ('c1cf796a-158a-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '修改字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.updateDictionary()', '1', '2019-01-11 18:22:11'), ('c37eec6c-1613-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '文件上传', '多图片上传', 'com.pay.platform.modules.common.controller.FileUploadController.imageUpload()', '1', '2019-01-12 10:42:55'), ('c4302856-1613-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '商家管理', '修改商家', 'com.pay.platform.modules.merchant.controller.MerchantController.updateMerchant()', '1', '2019-01-12 10:42:56'), ('c6f0dfa0-1581-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:17:54'), ('c724d29c-1581-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-11 17:17:54'), ('cabb5f30-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '商家管理', '新增商家', 'com.pay.platform.modules.merchant.controller.MerchantController.addMerchant()', '1', '2019-01-12 10:28:48'), ('ccbddf90-15a5-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '修改通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.updatePayChannel()', '1', '2019-01-11 21:35:46'), ('d5f25756-165b-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '新增权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.addPermission()', '1', '2019-01-12 19:18:49'), ('d62cfa72-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '文件上传', '多图片上传', 'com.pay.platform.modules.common.controller.FileUploadController.imageUpload()', '1', '2019-01-12 10:29:07'), ('d6a39dda-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '文件上传', '多图片上传', 'com.pay.platform.modules.common.controller.FileUploadController.imageUpload()', '1', '2019-01-12 10:29:08'), ('d7242b3a-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '文件上传', '多图片上传', 'com.pay.platform.modules.common.controller.FileUploadController.imageUpload()', '1', '2019-01-12 10:29:09'), ('d7bcae82-158a-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '新增字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.addDictionary()', '1', '2019-01-11 18:22:48'), ('d7d89e4e-1611-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '商家管理', '修改商家', 'com.pay.platform.modules.merchant.controller.MerchantController.updateMerchant()', '1', '2019-01-12 10:29:10'), ('d9f365fc-165b-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '修改权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.updatePermission()', '1', '2019-01-12 19:18:56'), ('dc3c6524-158a-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '修改字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.updateDictionary()', '1', '2019-01-11 18:22:55'), ('e2fa2c8a-1584-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '新增权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.addPermission()', '1', '2019-01-11 17:40:09'), ('e4d8fcb2-15a6-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '修改通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.updatePayChannel()', '1', '2019-01-11 21:43:35'), ('e4e4c0aa-165b-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '修改权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.updatePermission()', '1', '2019-01-12 19:19:14'), ('ee235820-156b-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '删除权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.deletePermission()', '1', '2019-01-11 14:41:31'), ('f040c2c2-158a-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '删除字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.deleteDictionary()', '1', '2019-01-11 18:23:29'), ('f9abe5be-15a6-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '通道管理', '修改通道', 'com.pay.platform.modules.payChannel.controller.PayChannelController.updatePayChannel()', '1', '2019-01-11 21:44:10'), ('fbf67ee2-160f-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '根据字典类型查询', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.queryDictionaryListByDictType()', '1', '2019-01-12 10:15:51'), ('fcd24278-165b-11e9-9b0a-810fc5b125a5', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '修改权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.updatePermission()', '1', '2019-01-12 19:19:55'), ('ffb6895e-1584-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '新增权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.addPermission()', '1', '2019-01-11 17:40:58');
COMMIT;

-- ----------------------------
--  Table structure for `tb_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` char(36) COLLATE utf8mb4_bin NOT NULL,
  `merchant_order_no` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商户订单号',
  `platform_order_no` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '平台订单号',
  `pay_code` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '第三方支付单号',
  `goods_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品名称',
  `order_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额(元)',
  `rate` decimal(10,3) DEFAULT NULL COMMENT '费率',
  `handling_fee` decimal(10,2) DEFAULT NULL COMMENT '手续费(元)',
  `merchant_amount` decimal(10,2) DEFAULT NULL COMMENT '商户金额(元)',
  `merchant_id` char(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商家ID',
  `merchant_no` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商家编号',
  `channel_id` char(36) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道ID',
  `pay_way` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付方式(zfbScanCode:支付宝扫码支付 zfbH5:支付宝h5支付 wxScanCode:微信扫码支付 wxH5:微信H5支付) ',
  `pay_status` varchar(50) COLLATE utf8mb4_bin DEFAULT 'waitPay' COMMENT '支付状态(waitPay:待支付 payed:已支付 payFail:支付失败)',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `notify_url` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商户回调地址',
  `notify_status` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '回调商户状态(notNotify:未通知 notifyed:已回调: 但未收到商家响应 success:已回调: 并收到商家响应)',
  `notify_num` int(11) DEFAULT '0' COMMENT '通知次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `merchant_order_no` (`merchant_order_no`),
  UNIQUE KEY `platform_order_no` (`platform_order_no`),
  UNIQUE KEY `pay_code` (`pay_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单';

-- ----------------------------
--  Records of `tb_order`
-- ----------------------------
BEGIN;
INSERT INTO `tb_order` VALUES ('bb00d448-165d-11e9-9b0a-810fc5b125a5', 'aaaa', 'bbb', 'ccc', '测试商品', '1000.00', '0.004', '4.00', '996.00', 'ddd', '10086', 'aasdasdas', 'zfbH5', 'waitPay', '2019-01-12 19:32:23', 'http://test001.frpgz1.idcfengye.com/openApi/testMerchantNotify', 'notNotify', '0', '2019-01-12 19:32:23'), ('d1b35882-165d-11e9-9b0a-810fc5b125a5', 'aaaa11', 'bbb222', 'ccc333', '测试商品', '1000.00', '0.004', '4.00', '996.00', 'ddd', '10086', 'aasdasdas', 'zfbScanCode', 'payed', '2019-01-12 19:33:01', 'http://test001.frpgz1.idcfengye.com/openApi/testMerchantNotify', 'success', '2', '2019-01-12 19:33:01'), ('d7f395a4-165d-11e9-9b0a-810fc5b125a5', 'aaaa113', 'bbb2223', 'ccc3333', '测试商品', '1000.00', '0.004', '4.00', '996.00', 'ddd', '10011', 'aasdasdas', 'wxScanCode', 'payed', '2019-01-12 19:33:12', 'http://test001.frpgz1.idcfengye.com/openApi/testMerchantNotify', 'notifyed', '8', '2019-01-12 19:33:12'), ('dcde8c5e-165d-11e9-9b0a-810fc5b125a5', 'aaaa1134', 'bbb22234', 'ccc33334', '测试商品', '1000.00', '0.004', '4.00', '996.00', 'ddd', '10086', 'aasdasdas', 'wxH5', 'payed', '2019-01-12 19:33:20', 'http://test001.frpgz1.idcfengye.com/openApi/testMerchantNotify', 'success', '2', '2019-01-12 19:33:20');
COMMIT;

-- ----------------------------
--  Table structure for `tb_organization`
-- ----------------------------
DROP TABLE IF EXISTS `tb_organization`;
CREATE TABLE `tb_organization` (
  `id` char(36) COLLATE utf8_bin NOT NULL COMMENT '机构id',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '机构名称',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '机构描述',
  `parent_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点id',
  `seq` int(11) DEFAULT NULL COMMENT '排序字段',
  `creator_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_del` int(11) DEFAULT '0' COMMENT '是否删除',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `contact_person` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `level` tinyint(4) DEFAULT NULL COMMENT '级别',
  `address` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '通信地址',
  `post_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='组织架构';

-- ----------------------------
--  Records of `tb_organization`
-- ----------------------------
BEGIN;
INSERT INTO `tb_organization` VALUES ('001', '组织机构', null, null, '1', null, '2016-10-22 22:32:25', '0', null, null, null, null, null, '0', null, null), ('582a15fe-9866-11e6-a3ae-9f1aa6f0c0f0', '董事会', '阿萨德哦按时', '001', null, null, '2016-10-22 22:46:41', '0', null, 'asiodj123', 'zknxjkfn@123', '123123', 'iqwioej213', '1', null, null), ('6255ff20-9866-11e6-a3ae-9f1aa6f0c0f0', '股东会', 'qiwehin', '582a15fe-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-10-22 22:46:58', '0', null, 'qnknc', '213534njkn', 'asdsd', 'kjnjknknsf', '2', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_pay_channel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_channel`;
CREATE TABLE `tb_pay_channel` (
  `id` char(36) COLLATE utf8mb4_bin NOT NULL,
  `channel_code` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道编码',
  `channel_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通道名称',
  `cost_rate` decimal(10,3) DEFAULT '0.000' COMMENT '成本费率',
  `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `channel_code` (`channel_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='支付通道';

-- ----------------------------
--  Records of `tb_pay_channel`
-- ----------------------------
BEGIN;
INSERT INTO `tb_pay_channel` VALUES ('41af8552-15a5-11e9-9b0a-810fc5b125a5', 'YouFuGlobal', '优赋全球', '0.016', '0', '2019-01-11 21:31:52'), ('8c40bba8-15a6-11e9-9b0a-810fc5b125a5', 'asd', '123', '0.002', '1', '2019-01-11 21:41:07');
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_permission`;
CREATE TABLE `tb_sys_permission` (
  `id` char(36) COLLATE utf8_bin NOT NULL COMMENT '资源id',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `permission_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限路径',
  `link_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '链接地址（首页面）',
  `parent_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点id',
  `seq` int(11) DEFAULT NULL COMMENT '排序字段',
  `level` tinyint(4) DEFAULT NULL COMMENT '级别（0: 根   1：目录  2：链接）',
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `creator_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除（0:不删除  1:删除）',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表';

-- ----------------------------
--  Records of `tb_sys_permission`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_permission` VALUES ('001', '权限列表', null, null, null, null, '0', null, null, null, '0', '2016-10-07 13:50:50'), ('0090f2de-8c3d-11e6-a458-8c10aa25bc40', '角色管理', '/sysmgr/role', '/view/sysmgr/role/role_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '1', '2', null, '1', '2016-10-07 11:20:31', '0', '2016-10-15 16:54:30'), ('04333dce-1611-11e9-9b0a-810fc5b125a5', '商家管理', null, null, '001', '9', '1', 'fa fa-users', '1', '2019-01-12 10:23:15', '0', null), ('09b6a39e-165c-11e9-9b0a-810fc5b125a5', '订单列表', '/order', '/view/order/order_list.jsp', 'd5f1881c-165b-11e9-9b0a-810fc5b125a5', '1', '2', null, '1', '2019-01-12 19:20:16', '0', null), ('0d973cd8-9848-11e6-acd3-1b1104e15e33', '日志管理', '/sysmgr/log', '/view/sysmgr/log/operation_log_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '5', '2', null, '1', '2016-10-22 19:09:51', '0', null), ('1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '系统管理', null, null, '001', '0', '1', 'fa fa-cog', '1', '2016-10-06 08:30:21', '0', '2016-10-07 13:50:50'), ('13ce9954-1611-11e9-9b0a-810fc5b125a5', '商家管理', '/merchant', '/view/merchant/merchant_list.jsp', '04333dce-1611-11e9-9b0a-810fc5b125a5', '1', '2', null, '1', '2019-01-12 10:23:41', '0', null), ('18e87b64-95f6-11e6-bdc5-5a61d81af47d', '组织机构', '/sysmgr/organization', '/view/sysmgr/organization/organization_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '3', '2', null, '1', '2016-10-19 20:18:09', '0', null), ('2082d6c6-8b5c-11e6-a458-8c10aa25bc40', '用户管理', '/sysmgr/user', '/view/sysmgr/user/user_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '0', '2', null, '1', '2016-10-06 14:52:25', '0', '2016-10-15 17:03:44'), ('8f21b758-e675-11e6-ba81-51f5dc7098ac', '树菜单生成', '/generate', '/view/generate/generate_tree.jsp', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '1', '2', null, '1', '2017-01-30 06:52:06', '0', null), ('9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '代码生成', null, null, '001', '7', '1', 'fa fa-wrench', '1', '2016-12-04 09:04:34', '0', null), ('c347c2be-b9bd-11e6-8824-d69d90f82bef', '单表生成', '/generate', '/view/generate/generate_single_table.jsp', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '2', '2', null, '1', '2016-12-04 09:05:35', '0', null), ('c655547e-8c3d-11e6-a458-8c10aa25bc40', '权限管理', '/sysmgr/permission', '/view/sysmgr/permission/permission_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '2', '2', null, '1', '2016-10-07 11:26:02', '0', null), ('d5f1881c-165b-11e9-9b0a-810fc5b125a5', '订单管理', null, null, '001', '10', '1', 'fa fa-book', '1', '2019-01-12 19:18:49', '0', null), ('e2f90224-1584-11e9-adea-1631b6fccd0d', '通道管理', null, null, '001', '8', '1', 'fa fa-university', '1', '2019-01-11 17:40:09', '0', null), ('e4dd3a52-95f6-11e6-bdc5-5a61d81af47d', '字典管理', '/sysmgr/dictionary', '/view/sysmgr/dictionary/dictionary_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '4', '2', null, '1', '2016-10-19 20:23:51', '0', null), ('ffb5a106-1584-11e9-adea-1631b6fccd0d', '通道列表', '/payChannel', '/view/payChannel/payChannel_list.jsp', 'e2f90224-1584-11e9-adea-1631b6fccd0d', '1', '2', null, '1', '2019-01-11 17:40:58', '0', null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role`;
CREATE TABLE `tb_sys_role` (
  `id` char(36) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色码',
  `creator_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
--  Records of `tb_sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_role` VALUES ('001', '超级管理员', 'ROLE_ADMIN', '001', '2016-10-07 22:42:28', '0', '2016-10-13 19:58:17');
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_role_permission_ref`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role_permission_ref`;
CREATE TABLE `tb_sys_role_permission_ref` (
  `id` char(36) COLLATE utf8_bin NOT NULL,
  `role_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '角色id',
  `permission_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色和权限关联表';

-- ----------------------------
--  Records of `tb_sys_role_permission_ref`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_role_permission_ref` VALUES ('0cfc9be4-165c-11e9-9b0a-810fc5b125a5', '001', '001'), ('0cfc9e50-165c-11e9-9b0a-810fc5b125a5', '001', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('0cfc9eb4-165c-11e9-9b0a-810fc5b125a5', '001', '2082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('0cfc9ee6-165c-11e9-9b0a-810fc5b125a5', '001', '0090f2de-8c3d-11e6-a458-8c10aa25bc40'), ('0cfc9f18-165c-11e9-9b0a-810fc5b125a5', '001', 'c655547e-8c3d-11e6-a458-8c10aa25bc40'), ('0cfc9f4a-165c-11e9-9b0a-810fc5b125a5', '001', '18e87b64-95f6-11e6-bdc5-5a61d81af47d'), ('0cfc9fb8-165c-11e9-9b0a-810fc5b125a5', '001', 'e4dd3a52-95f6-11e6-bdc5-5a61d81af47d'), ('0cfc9ff4-165c-11e9-9b0a-810fc5b125a5', '001', '0d973cd8-9848-11e6-acd3-1b1104e15e33'), ('0cfca01c-165c-11e9-9b0a-810fc5b125a5', '001', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef'), ('0cfca044-165c-11e9-9b0a-810fc5b125a5', '001', '8f21b758-e675-11e6-ba81-51f5dc7098ac'), ('0cfca06c-165c-11e9-9b0a-810fc5b125a5', '001', 'c347c2be-b9bd-11e6-8824-d69d90f82bef'), ('0cfca09e-165c-11e9-9b0a-810fc5b125a5', '001', 'e2f90224-1584-11e9-adea-1631b6fccd0d'), ('0cfca0c6-165c-11e9-9b0a-810fc5b125a5', '001', 'ffb5a106-1584-11e9-adea-1631b6fccd0d'), ('0cfca0ee-165c-11e9-9b0a-810fc5b125a5', '001', '04333dce-1611-11e9-9b0a-810fc5b125a5'), ('0cfca116-165c-11e9-9b0a-810fc5b125a5', '001', '13ce9954-1611-11e9-9b0a-810fc5b125a5'), ('0cfca13e-165c-11e9-9b0a-810fc5b125a5', '001', 'd5f1881c-165b-11e9-9b0a-810fc5b125a5'), ('0cfca166-165c-11e9-9b0a-810fc5b125a5', '001', '09b6a39e-165c-11e9-9b0a-810fc5b125a5');
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user`;
CREATE TABLE `tb_sys_user` (
  `id` char(36) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `account` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `creator_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除（0:未删除 1:删除）',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `org_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构id',
  `extend_img_url` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展图片路径参数,仅做多图片上传测试用,无任何业务意义',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统用户表';

-- ----------------------------
--  Records of `tb_sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_user` VALUES ('77ed7170-92b6-11e6-b1df-688b80e1e8e6', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '18617369765', '啊涛', '/resources/img/user1-128x128.jpg', null, null, null, '2016-10-15 17:05:07', '0', null, '001', null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_user_role_ref`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user_role_ref`;
CREATE TABLE `tb_sys_user_role_ref` (
  `id` char(36) COLLATE utf8_bin NOT NULL,
  `user_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
  `role_id` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户和角色关联表';

-- ----------------------------
--  Records of `tb_sys_user_role_ref`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_user_role_ref` VALUES ('65e9d830-9372-11e6-a76a-de1ef7809981', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '001');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
