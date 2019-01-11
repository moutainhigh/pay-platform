/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : baseframe

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 01/05/2018 14:21:51 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary`;
CREATE TABLE `tb_dictionary` (
  `id` char(36) COLLATE utf8_bin NOT NULL,
  `DICT_TYPE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '字典类型',
  `DICT_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '字典描述',
  `ITEM_CODE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类码',
  `ITEM_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类描述',
  `PARENT_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点id',
  `LEVEL` tinyint(4) DEFAULT NULL COMMENT '级别',
  `SEQ` int(11) DEFAULT NULL COMMENT '排序字段',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_DEL` tinyint(4) DEFAULT '0' COMMENT '是否删除(0:否 1:是)',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典表';

-- ----------------------------
--  Records of `tb_dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `tb_dictionary` VALUES ('001', '', '字典列表', null, null, null, '0', '1', '2016-10-20 22:51:15', '0', null), ('03fe5164-d4ef-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'singleImage', '单图片', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '3', '2017-01-07 23:36:11', '0', null), ('081ee934-d4ef-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'multiImage', '多图片', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '4', '2017-01-07 23:36:17', '0', null), ('0ed05ac0-b752-11e6-8059-e7d98718e227', 'asd', 'asd', null, null, '001', '1', '10', '2016-12-01 07:09:34', '1', '2017-02-05 15:18:15'), ('15847360-b752-11e6-8059-e7d98718e227', 'asd', 'asd', 'asxchttp://localhost:8080/baseframe/console/indexh', '123http://localhost:8080/baseframe/console/indexht', '0ed05ac0-b752-11e6-8059-e7d98718e227', '2', '1', '2016-12-01 07:09:45', '1', '2017-02-05 15:18:15'), ('194c7aec-b752-11e6-8059-e7d98718e227', 'asd', 'asd', 'http://localhost:8080/baseframe/console/index', 'asdasd', '0ed05ac0-b752-11e6-8059-e7d98718e227', '2', '2', '2016-12-01 07:09:52', '1', '2017-02-05 15:18:15'), ('1f1e571a-96e1-11e6-8511-4d341ef78098', 'testStatus', '测试状态', 'asd', '22', 'e119757c-96da-11e6-8511-4d341ef78098', '2', '4', '2016-10-21 00:20:31', '0', '2016-10-28 23:45:56'), ('22da831c-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'radio', '单选按钮', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '5', '2017-02-05 13:51:28', '0', null), ('278386e8-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'checkbox', '复选框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '6', '2017-02-05 13:51:35', '0', null), ('28fe9132-b752-11e6-8059-e7d98718e227', 'asd', 'asd', 'xd', 'asd23', '0ed05ac0-b752-11e6-8059-e7d98718e227', '2', '3', '2016-12-01 07:10:18', '1', '2017-02-05 15:18:15'), ('2b7f86d2-b752-11e6-8059-e7d98718e227', 'asd', 'asd', '123', 'xzd', '0ed05ac0-b752-11e6-8059-e7d98718e227', '2', '4', '2016-12-01 07:10:22', '1', '2017-02-05 15:18:15'), ('2b9f4e10-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'select', '下拉框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '7', '2017-02-05 13:51:42', '0', null), ('3f3ceb30-f8ff-11e6-b7c6-a687b5f5ac7d', '1测试-001', '测试-0011', null, null, '001', '1', '16', '2017-02-22 21:03:04', '1', '2017-02-22 21:03:35'), ('487c6e9e-b752-11e6-8059-e7d98718e227', '123', 'asdas', 'asasd', 'http://localhost:8080/baseframe/console/indexhttp:', '4ea90494-ae2e-11e6-a27f-24ec3544b5c1', '2', '2', '2016-12-01 07:11:11', '1', '2017-02-05 15:18:18'), ('4ea90494-ae2e-11e6-a27f-24ec3544b5c1', '123', 'asdas', null, null, '001', '1', '9', '2016-11-19 16:00:59', '1', '2017-02-05 15:18:18'), ('51def59c-ae2e-11e6-a27f-24ec3544b5c1', '123', 'a阿斯顿', '123', 'asd', '4ea90494-ae2e-11e6-a27f-24ec3544b5c1', '2', '1', '2016-11-19 16:01:04', '1', '2017-02-05 15:18:18'), ('545b3b3e-f8ff-11e6-b7c6-a687b5f5ac7d', '3', '3', null, null, '001', '1', '17', '2017-02-22 21:03:39', '1', '2017-02-22 21:03:42'), ('56fe295a-96da-11e6-8511-4d341ef78098', 'payStatus', '支付状态', null, null, '001', '1', '3', '2016-10-20 23:31:58', '0', '2016-10-22 15:10:18'), ('63ddc684-96e0-11e6-8511-4d341ef78098', 'orderStatus', '订单状态', '1', '支付成功', '56fe295a-96da-11e6-8511-4d341ef78098', '2', '1', '2016-10-21 00:15:17', '0', '2016-10-22 15:10:18'), ('67321c1c-b9d7-11e6-8824-d69d90f82bef', 'refundStatus', '退款状态', 'qwe', '1', '7cb71c00-b75b-11e6-8059-e7d98718e227', '2', '2', '2016-12-04 12:09:08', '0', null), ('67c34abc-96e0-11e6-8511-4d341ef78098', 'orderStatus', '订单状态', '221', '支付失败21', '56fe295a-96da-11e6-8511-4d341ef78098', '2', '2', '2016-10-21 00:15:23', '1', '2017-02-05 15:18:32'), ('6eafdfd2-b753-11e6-8059-e7d98718e227', 'payStatus', '支付状态1', '3', '待支付', '56fe295a-96da-11e6-8511-4d341ef78098', '2', '4', '2016-12-01 07:19:24', '0', null), ('79558d9c-b753-11e6-8059-e7d98718e227', 'checkStatus', '审核状态', '2', '审核通过', 'e119757c-96da-11e6-8511-4d341ef78098', '2', '5', '2016-12-01 07:19:42', '0', null), ('7cb71c00-b75b-11e6-8059-e7d98718e227', 'refundStatus', '退款状态', null, null, '001', '1', '11', '2016-12-01 08:17:04', '0', null), ('7e8e5c80-b753-11e6-8059-e7d98718e227', 'checkStatus', '审核状态', '3', '审核不通过', 'e119757c-96da-11e6-8511-4d341ef78098', '2', '6', '2016-12-01 07:19:51', '0', null), ('83d1fb1e-ec66-11e6-9f79-67b34f704711', 'formType', '表单类型', 'richText', '富文本', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '8', '2017-02-06 20:19:32', '0', null), ('885f5bd0-b75b-11e6-8059-e7d98718e227', '123', 'asdas123', null, null, '001', '1', '12', '2016-12-01 08:17:23', '1', '2017-02-05 15:18:25'), ('88f15a6c-eb77-11e6-9b20-4157f2b6b97f', 'payStatus', '支付状态', '4', '未支付', '56fe295a-96da-11e6-8511-4d341ef78098', '2', '5', '2017-02-05 15:48:51', '0', null), ('8a7e4bba-b75b-11e6-8059-e7d98718e227', '123', 'asdas123', 'axd', '123', '885f5bd0-b75b-11e6-8059-e7d98718e227', '2', '1', '2016-12-01 08:17:27', '1', '2017-02-05 15:18:25'), ('8d8d9590-b75b-11e6-8059-e7d98718e227', '123', 'asdas123', 'asd', '21312', '885f5bd0-b75b-11e6-8059-e7d98718e227', '2', '2', '2016-12-01 08:17:32', '1', '2017-02-05 15:18:25'), ('8e7d2686-b75c-11e6-8059-e7d98718e227', 'asd', '123', null, null, '001', '1', '13', '2016-12-01 08:24:43', '1', '2017-02-05 15:18:23'), ('93d1501c-b75c-11e6-8059-e7d98718e227', 'asd23', '1232', null, null, '001', '1', '14', '2016-12-01 08:24:52', '1', '2017-02-05 15:18:21'), ('95ffadda-b75b-11e6-8059-e7d98718e227', 'refundStatus', '退款状态', '2', '退款成功', '7cb71c00-b75b-11e6-8059-e7d98718e227', '2', '1', '2016-12-01 08:17:46', '0', null), ('ab7fdc9c-b752-11e6-8059-e7d98718e227', 'payStatus', '支付状态1', '2', '支付失败', '56fe295a-96da-11e6-8511-4d341ef78098', '2', '3', '2016-12-01 07:13:57', '0', null), ('b4f5c816-96e3-11e6-8511-4d341ef78098', '1', '待审核', null, null, 'e119757c-96da-11e6-8511-4d341ef78098', '1', '2', '2016-10-21 00:39:01', '0', '2016-12-01 07:42:41'), ('e119757c-96da-11e6-8511-4d341ef78098', 'checkStatus', '审核状态', null, null, '001', '1', '8', '2016-10-20 23:35:50', '0', null), ('e714f7ce-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', null, null, '001', '1', '15', '2017-01-07 23:35:22', '0', null), ('e9468dba-96e0-11e6-8511-4d341ef78098', 'testStatus', '测试状态', null, null, '001', '1', '6', '2016-10-21 00:19:01', '1', '2017-02-05 15:18:30'), ('eedeb7f0-9827-11e6-acd3-1b1104e15e33', 'xxStatus33', 'XXX状态33', null, null, '001', '1', '7', '2016-10-22 15:19:56', '1', '2017-02-05 15:18:28'), ('ef44cc12-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'text', '文本框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '1', '2017-01-07 23:35:36', '0', null), ('f72c9c1a-9827-11e6-acd3-1b1104e15e33', 'xxStatus', 'XXX状态', '1122', 'xx分类22', 'eedeb7f0-9827-11e6-acd3-1b1104e15e33', '2', '1', '2016-10-22 15:20:09', '1', '2017-02-05 15:18:28'), ('fdd5905e-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'datetime', '日期', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '2', '2017-01-07 23:36:00', '0', null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_organization`
-- ----------------------------
DROP TABLE IF EXISTS `tb_organization`;
CREATE TABLE `tb_organization` (
  `ID` char(36) COLLATE utf8_bin NOT NULL COMMENT '机构ID',
  `NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '机构名称',
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '机构描述',
  `PARENT_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点ID',
  `SEQ` int(11) DEFAULT NULL COMMENT '排序字段',
  `CREATOR_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_DEL` int(11) DEFAULT '0' COMMENT '是否删除',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `CONTACT_PERSON` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `TEL` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `FAX` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '传真',
  `EMAIL` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `LEVEL` tinyint(4) DEFAULT NULL COMMENT '级别',
  `ADDRESS` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '通信地址',
  `POST_CODE` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮编',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='组织架构';

-- ----------------------------
--  Records of `tb_organization`
-- ----------------------------
BEGIN;
INSERT INTO `tb_organization` VALUES ('001', '组织机构', null, null, '1', null, '2016-10-22 22:32:25', '0', null, null, null, null, null, '0', null, null), ('0ce4a9c4-9866-11e6-a3ae-9f1aa6f0c0f0', '常务副总', '负责常人事物 - 333', 'd96c9f84-9865-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-10-22 22:44:35', '0', '2016-10-22 23:44:44', '张总 - 333', '13829918382 - 333', '1231 - 333', '213nnk23 - 333', '2', null, null), ('3212bca4-9866-11e6-a3ae-9f1aa6f0c0f0', '财务审计部', '负责日常财务事-0122 asd sad 阿萨德AASf阿桑f阿诗丹顿as as 飒飒阿萨德阿桑as的阿萨德as 大声道a的阿迪啊', '0ce4a9c4-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-10-22 22:45:37', '0', '2016-10-22 23:44:44', '李嘉诚-01', '13829188823-01', 'ni123sdf-01', 'zxkchi@132.com-01', '3', null, null), ('367868fc-ba26-11e6-bb56-5eae4e7c831e', '哈哈', '1sdfasdf', '0ce4a9c4-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-12-04 21:33:16', '0', null, '郑金涛', '13211121122', '23', 'qwe', '3', '123', '123'), ('4cd11ea0-9866-11e6-a3ae-9f1aa6f0c0f0', '市场部门', '负责市场扩张', '0ce4a9c4-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-10-22 22:46:22', '0', '2016-10-22 23:44:44', '李总123.123', '13285912331', 'qwe123', '三2323@132.com', '3', '关电视搜到傲视', 'asd123'), ('582a15fe-9866-11e6-a3ae-9f1aa6f0c0f0', '董事会', '阿萨德哦按时', '001', null, null, '2016-10-22 22:46:41', '0', null, 'asiodj123', 'zknxjkfn@123', '123123', 'iqwioej213', '1', null, null), ('6255ff20-9866-11e6-a3ae-9f1aa6f0c0f0', '股东会', 'qiwehin', '582a15fe-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-10-22 22:46:58', '0', null, 'qnknc', '213534njkn', 'asdsd', 'kjnjknknsf', '2', null, null), ('749e188c-ae2f-11e6-a27f-24ec3544b5c1', 'root12', '阿道夫阿萨德发生大', 'd96c9f84-9865-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-11-19 16:09:12', '1', '2016-11-19 16:09:24', '张总 - 333', '13829918382', '1231 - 333', 'zhengjintao_168@163.com', '2', null, null), ('8c779730-c97d-11e6-a6f7-03ab52980756', '阿萨德', '123', '001', null, null, '2016-12-24 10:06:14', '0', null, '123', '1312', '1231', '213123', '1', '123', '12312'), ('90c7ab5e-c97d-11e6-a6f7-03ab52980756', 'asd3', '123', '001', null, null, '2016-12-24 10:06:21', '0', null, '1233', '12333', '123', '12333', '1', '1233', '123'), ('b106368c-f8fe-11e6-b7c6-a687b5f5ac7d', '21', '123', '001', null, null, '2017-02-22 20:59:05', '1', '2017-02-22 20:59:12', '123', '123', '123', '123', '1', '123', '123'), ('b406f3e0-c98c-11e6-a6f7-03ab52980756', 'dsf adsf asdf a 大师傅', '', '3212bca4-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-12-24 11:54:43', '1', '2016-12-24 11:55:02', '', '', '', '', '4', '', ''), ('d2fb2564-c98c-11e6-a6f7-03ab52980756', '大师傅大师傅阿桑', '', '3212bca4-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-12-24 11:55:35', '1', '2016-12-24 11:55:41', '', '', '', '', '4', '', ''), ('d96c9f84-9865-11e6-a3ae-9f1aa6f0c0f0', '总经办', '总经理', '001', null, null, '2016-10-22 22:43:08', '0', null, '郑总', '18617369765', 'aaa@163.com', 'zhengjintao_168@163.com', '1', null, null), ('dacab80a-c98b-11e6-a6f7-03ab52980756', 'asd - 888', '123123 - 888', '0ce4a9c4-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-12-24 11:48:38', '0', null, '12312 - 888', '123 - 888', '123 - 888', '3123 - 888', '3', '12312312 - 888', '123 - 888'), ('de33bf96-c98b-11e6-a6f7-03ab52980756', 'asdas', 'dasd', '3212bca4-9866-11e6-a3ae-9f1aa6f0c0f0', null, null, '2016-12-24 11:48:44', '1', '2016-12-24 11:53:45', 'asdas', 'asd', 'as', 'asd', '4', 'asd', 'asd'), ('e1eb2d4a-c98b-11e6-a6f7-03ab52980756', 'd', '312312', 'de33bf96-c98b-11e6-a6f7-03ab52980756', null, null, '2016-12-24 11:48:50', '1', '2016-12-24 11:53:45', 'asdasdas', '123', '12312', '123', '5', 'd', 'ads'), ('e666d73e-c98b-11e6-a6f7-03ab52980756', '大师傅大师傅', '仿阿萨德阿桑', 'e1eb2d4a-c98b-11e6-a6f7-03ab52980756', null, null, '2016-12-24 11:48:58', '1', '2016-12-24 11:51:46', '撒的发撒旦', '大师傅d', '的算法第三方', '阿萨德a阿桑阿桑\'s', '6', 'ds仿大师傅俺都是法第三方a\'s\'d', 'f俺都是f发送阿桑');
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_permission`;
CREATE TABLE `tb_sys_permission` (
  `ID` char(36) COLLATE utf8_bin NOT NULL COMMENT '资源ID',
  `NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `PERMISSION_URL` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限路径',
  `LINK_URL` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '链接地址（首页面）',
  `PARENT_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '父节点ID',
  `SEQ` int(11) DEFAULT NULL COMMENT '排序字段',
  `LEVEL` tinyint(4) DEFAULT NULL COMMENT '级别（0: 根   1：目录  2：链接）',
  `ICON` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `CREATOR_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_DEL` tinyint(4) DEFAULT '0' COMMENT '是否删除（0:不删除  1:删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表';

-- ----------------------------
--  Records of `tb_sys_permission`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_permission` VALUES ('001', '权限列表', null, null, null, null, '0', null, null, null, '0', '2016-10-07 13:50:50'), ('0090f2de-8c3d-11e6-a458-8c10aa25bc40', '角色管理', '/sysmgr/role', '/view/sysmgr/role/role_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '1', '2', null, '1', '2016-10-07 11:20:31', '0', '2016-10-15 16:54:30'), ('0d973cd8-9848-11e6-acd3-1b1104e15e33', '日志管理', '/sysmgr/log', '/view/sysmgr/log/operation_log_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '5', '2', null, '1', '2016-10-22 19:09:51', '0', null), ('1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '系统管理', null, null, '001', '0', '1', 'fa fa-cog', '1', '2016-10-06 08:30:21', '0', '2016-10-07 13:50:50'), ('18e87b64-95f6-11e6-bdc5-5a61d81af47d', '组织机构', '/sysmgr/organization', '/view/sysmgr/organization/organization_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '3', '2', null, '1', '2016-10-19 20:18:09', '0', null), ('2082d6c6-8b5c-11e6-a458-8c10aa25bc40', '用户管理', '/sysmgr/user', '/view/sysmgr/user/user_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '0', '2', null, '1', '2016-10-06 14:52:25', '0', '2016-10-15 17:03:44'), ('26874332-2033-11e7-892c-7baffad95003', '门店管理', null, null, '001', '5', '1', 'fa fa-cog', '1', '2017-04-13 18:22:51', '1', '2018-01-05 14:17:07'), ('28d6b6de-e68d-11e6-ba81-51f5dc7098ac', '商品类型管理', '/test/goodsType', '/view/test/goodsType/goodsType_list.jsp', 'af82763e-d4ca-11e6-9c06-529f25dde3db', '2', '2', null, '1', '2017-01-30 09:41:03', '0', null), ('2dd27eec-ac05-11e6-8664-1a66b0444383', '系统监控-002', '/sysmgr/listener-002', '/view/sysmgr/listener/listener_list.jsp-002', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '6', '2', null, '1', '2016-11-16 22:01:32', '1', '2016-11-16 22:05:10'), ('345dddd6-2033-11e7-892c-7baffad95003', '商品管理', '/goods', '/view/store/goods', '26874332-2033-11e7-892c-7baffad95003', '1', '2', null, '1', '2017-04-13 18:23:15', '1', '2018-01-05 14:17:07'), ('4e2e0fc0-92b6-11e6-b1df-688b80e1e8e6', '11', null, null, '001', '2', '1', '11', '1', '2016-10-15 17:03:57', '1', '2016-10-15 17:04:10'), ('51038f40-92b6-11e6-b1df-688b80e1e8e6', '22', '22', '22', '4e2e0fc0-92b6-11e6-b1df-688b80e1e8e6', '1', '2', null, '1', '2016-10-15 17:04:02', '1', '2016-10-15 17:04:10'), ('8f21b758-e675-11e6-ba81-51f5dc7098ac', '树菜单生成', '/generate', '/view/generate/generate_tree.jsp', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '1', '2', null, '1', '2017-01-30 06:52:06', '0', null), ('96104fa0-b9bd-11e6-8824-d69d90f82bef', '社区管理', null, null, '001', '3', '1', 'asd', '1', '2016-12-04 09:04:19', '1', '2016-12-04 09:04:28'), ('9d3fbe6c-8c3d-11e6-a458-8c10aa25bc40', '通知公告', null, null, '001', '1', '1', 'fa fa-bell', '1', '2016-10-07 11:24:54', '1', '2017-01-07 11:18:40'), ('9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '代码生成', null, null, '001', '7', '1', 'fa fa-wrench', '1', '2016-12-04 09:04:34', '0', null), ('a0f73386-dbf8-11e6-bbb0-395913546a19', '商品管理', '/test/goods', '/view/test/goods/goods_list.jsp', 'af82763e-d4ca-11e6-9c06-529f25dde3db', '1', '2', null, '1', '2017-01-16 22:32:38', '0', null), ('a9595db2-f8fc-11e6-b7c6-a687b5f5ac7d', 'asd222', null, null, '001', '6', '1', '123', '1', '2017-02-22 20:44:33', '1', '2017-02-22 20:45:24'), ('af82763e-d4ca-11e6-9c06-529f25dde3db', '测试模块', null, null, '001', '4', '1', 'fa fa-wrench', '1', '2017-01-07 19:16:07', '0', null), ('b3f33936-8c3d-11e6-a458-8c10aa25bc40', '站内信-01', '/message/aa-01', '/message/aa-01', '9d3fbe6c-8c3d-11e6-a458-8c10aa25bc40', '1', '2', null, '1', '2016-10-07 11:25:32', '1', '2017-01-07 11:18:40'), ('c347c2be-b9bd-11e6-8824-d69d90f82bef', '单表生成', '/generate', '/view/generate/generate_single_table.jsp', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '2', '2', null, '1', '2016-12-04 09:05:35', '0', null), ('c655547e-8c3d-11e6-a458-8c10aa25bc40', '权限管理', '/sysmgr/permission', '/view/sysmgr/permission/permission_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '2', '2', null, '1', '2016-10-07 11:26:02', '0', null), ('cb0b7474-ae2c-11e6-a27f-24ec3544b5c1', 'root', '/sysmgr/role', '/view/sysmgr/role/role_list.jsp', '9d3fbe6c-8c3d-11e6-a458-8c10aa25bc40', '3', '2', null, '1', '2016-11-19 15:50:08', '1', '2017-01-07 11:18:40'), ('cb782e46-f8fc-11e6-b7c6-a687b5f5ac7d', 'asd', null, null, '001', '9', '1', 'fa fa-wrench', '1', '2017-02-22 20:45:31', '1', '2018-01-05 14:17:02'), ('d51a021c-f8fc-11e6-b7c6-a687b5f5ac7d', 'asd22211', '33123', '33123', 'cb782e46-f8fc-11e6-b7c6-a687b5f5ac7d', '1', '2', null, '1', '2017-02-22 20:45:47', '1', '2018-01-05 14:17:02'), ('e4dd3a52-95f6-11e6-bdc5-5a61d81af47d', '字典管理', '/sysmgr/dictionary', '/view/sysmgr/dictionary/dictionary_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '4', '2', null, '1', '2016-10-19 20:23:51', '0', null), ('f1138330-f8fc-11e6-b7c6-a687b5f5ac7d', '测试目录', null, null, '001', '8', '1', 'fa fa-wrench', '1', '2017-02-22 20:46:34', '1', '2017-02-22 20:48:43'), ('f7a7ead2-8d5a-11e6-96a1-28aef3b7bb84', 'asd as22', 'asd22', 'asd22', '9d3fbe6c-8c3d-11e6-a458-8c10aa25bc40', '2', '2', null, '1', '2016-10-08 21:27:32', '1', '2017-01-07 11:18:40');
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role`;
CREATE TABLE `tb_sys_role` (
  `ID` char(36) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
  `NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `CODE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色码',
  `CREATOR_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_DEL` tinyint(4) DEFAULT NULL COMMENT '是否删除',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
--  Records of `tb_sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_role` VALUES ('0001', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:40'), ('001', '超级管理员', 'ROLE_ADMIN', '001', '2016-10-07 22:42:28', '0', '2016-10-13 19:58:17'), ('002', '管理员23', 'ROLE_USER', '001', '2016-10-08 21:40:33', '1', '2016-12-04 10:19:43'), ('021', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:40'), ('023', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:40'), ('214bf044-b6ec-11e6-8249-c8ddfd5ac8df', 'asdsadsafsdagdfa', 'asdsadgasdfasdfa', '001', '2016-11-30 18:59:56', '1', '2016-11-30 19:00:11'), ('23b0cda0-b6ec-11e6-8249-c8ddfd5ac8df', '123', 'zxc', '001', '2016-11-30 19:00:00', '1', '2016-11-30 19:00:08'), ('25b74994-b6ec-11e6-8249-c8ddfd5ac8df', '231', 'asd', '001', '2016-11-30 19:00:04', '1', '2016-11-30 19:00:11'), ('2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', 'zxc-001', 'zxc-001', '001', '2016-11-30 19:00:16', '1', '2016-12-04 10:19:41'), ('57a92e26-b75b-11e6-8059-e7d98718e227', 'asd', '123', '001', '2016-12-01 08:16:02', '1', '2016-12-01 08:16:04'), ('5ab5410e-b75b-11e6-8059-e7d98718e227', '12311', 'xzc', '001', '2016-12-01 08:16:07', '1', '2016-12-04 10:19:39'), ('5ee8bace-b6ea-11e6-8249-c8ddfd5ac8df', '2asd', 'sad2http://localhost:8080/base', '001', '2016-11-30 18:47:21', '1', '2016-11-30 18:47:52'), ('60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', 'asd-1', 'zxc-1', '001', '2016-11-13 16:58:43', '1', '2016-11-30 18:47:59'), ('6b4d5f70-92b6-11e6-b1df-688b80e1e8e6', '123', '123', '001', '2016-10-15 17:04:46', '1', '2016-10-16 09:43:43'), ('6fb2f80c-b9d7-11e6-8824-d69d90f82bef', 'asd12', '123', '001', '2016-12-04 12:09:22', '1', '2016-12-04 12:09:24'), ('722b483c-b9d7-11e6-8824-d69d90f82bef', '123123', 'asd123', '001', '2016-12-04 12:09:26', '1', '2017-01-30 10:03:15'), ('73898d8c-b6ea-11e6-8249-c8ddfd5ac8df', 'das', 'zxc', '001', '2016-11-30 18:47:55', '1', '2016-11-30 18:47:59'), ('7eb42e52-b75c-11e6-8059-e7d98718e227', 'asd123', 'asd', '001', '2016-12-01 08:24:17', '1', '2016-12-01 08:24:23'), ('a1d59b8a-ae2b-11e6-a27f-24ec3544b5c1', 'asd', 'asd', '001', '2016-11-19 15:41:50', '1', '2016-11-19 15:41:53'), ('a8858f6a-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:40'), ('a885d524-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:40'), ('a885ecb2-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:40'), ('a886017a-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a88632ee-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a886517a-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:26'), ('a8866624-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a8867740-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a8868b5e-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a8869ca2-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a886b12e-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a886c18c-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a886d078-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a886de7e-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a886ed24-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 17:04:29'), ('a886fb8e-921c-11e6-b30c-6b1a42fa22bb', 'xxx33', 'aaa33', null, null, '1', '2016-10-15 20:08:00'), ('a8870688-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-15 20:08:00'), ('a88713da-921c-11e6-b30c-6b1a42fa22bb', '33xxx', 'aaa44', null, null, '1', '2016-10-16 09:43:23'), ('a887249c-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a88730ae-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a8873ce8-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a8874922-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a887557a-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a88760ba-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a8876cb8-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a8877834-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:23'), ('a8878400-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a8878f36-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a8879a58-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887a5ac-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887b128-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887bcc2-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887c80c-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887d360-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887e0bc-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887ebfc-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:25'), ('a887f980-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:27'), ('a888056a-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:27'), ('a88811fe-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:27'), ('a8881cc6-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:27'), ('a8882b94-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:27'), ('a8883e90-921c-11e6-b30c-6b1a42fa22bb', 'xxx', 'aaa', null, null, '1', '2016-10-16 09:43:27'), ('b207e8d4-f8f7-11e6-b7c6-a687b5f5ac7d', '测试角色', '001', '001', '2017-02-22 20:09:00', '1', '2017-02-22 20:09:18'), ('b8366ae6-f8f7-11e6-b7c6-a687b5f5ac7d', '测试角色02', '测试角色02', '001', '2017-02-22 20:09:11', '1', '2017-02-22 20:09:15'), ('dd8f56e2-8edd-11e6-b577-12b5599b0132', '社区管理员 2 12', 'ROLE_COMMUNITY2', '001', '2016-10-10 19:37:03', '0', '2016-10-13 19:58:17');
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_role_permission_ref`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role_permission_ref`;
CREATE TABLE `tb_sys_role_permission_ref` (
  `ID` char(36) COLLATE utf8_bin NOT NULL,
  `ROLE_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '角色ID',
  `PERMISSION_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色和权限关联表';

-- ----------------------------
--  Records of `tb_sys_role_permission_ref`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_role_permission_ref` VALUES ('346e2d90-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', '001'), ('346e2fa2-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('346e2ff2-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', '2082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('346e301a-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', '0090f2de-8c3d-11e6-a458-8c10aa25bc40'), ('346e3038-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', 'c655547e-8c3d-11e6-a458-8c10aa25bc40'), ('346e309c-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', '18e87b64-95f6-11e6-bdc5-5a61d81af47d'), ('346e30c4-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', 'e4dd3a52-95f6-11e6-bdc5-5a61d81af47d'), ('346e30e2-b6ec-11e6-8249-c8ddfd5ac8df', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df', '0d973cd8-9848-11e6-acd3-1b1104e15e33'), ('40f812e6-2033-11e7-892c-7baffad95003', '001', '001'), ('40f8150c-2033-11e7-892c-7baffad95003', '001', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('40f81566-2033-11e7-892c-7baffad95003', '001', '2082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('40f81598-2033-11e7-892c-7baffad95003', '001', '0090f2de-8c3d-11e6-a458-8c10aa25bc40'), ('40f815ca-2033-11e7-892c-7baffad95003', '001', 'c655547e-8c3d-11e6-a458-8c10aa25bc40'), ('40f81606-2033-11e7-892c-7baffad95003', '001', '18e87b64-95f6-11e6-bdc5-5a61d81af47d'), ('40f81638-2033-11e7-892c-7baffad95003', '001', 'e4dd3a52-95f6-11e6-bdc5-5a61d81af47d'), ('40f81660-2033-11e7-892c-7baffad95003', '001', '0d973cd8-9848-11e6-acd3-1b1104e15e33'), ('40f81688-2033-11e7-892c-7baffad95003', '001', 'af82763e-d4ca-11e6-9c06-529f25dde3db'), ('40f816ec-2033-11e7-892c-7baffad95003', '001', 'a0f73386-dbf8-11e6-bbb0-395913546a19'), ('40f8171e-2033-11e7-892c-7baffad95003', '001', '28d6b6de-e68d-11e6-ba81-51f5dc7098ac'), ('40f81746-2033-11e7-892c-7baffad95003', '001', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef'), ('40f8176e-2033-11e7-892c-7baffad95003', '001', 'c347c2be-b9bd-11e6-8824-d69d90f82bef'), ('40f81796-2033-11e7-892c-7baffad95003', '001', '8f21b758-e675-11e6-ba81-51f5dc7098ac'), ('40f817be-2033-11e7-892c-7baffad95003', '001', '26874332-2033-11e7-892c-7baffad95003'), ('40f817f0-2033-11e7-892c-7baffad95003', '001', '345dddd6-2033-11e7-892c-7baffad95003'), ('617f2504-b75b-11e6-8059-e7d98718e227', '5ab5410e-b75b-11e6-8059-e7d98718e227', '001'), ('617f2734-b75b-11e6-8059-e7d98718e227', '5ab5410e-b75b-11e6-8059-e7d98718e227', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('617f27a2-b75b-11e6-8059-e7d98718e227', '5ab5410e-b75b-11e6-8059-e7d98718e227', '18e87b64-95f6-11e6-bdc5-5a61d81af47d'), ('617f27c0-b75b-11e6-8059-e7d98718e227', '5ab5410e-b75b-11e6-8059-e7d98718e227', 'e4dd3a52-95f6-11e6-bdc5-5a61d81af47d'), ('793d6100-b9d7-11e6-8824-d69d90f82bef', '722b483c-b9d7-11e6-8824-d69d90f82bef', '001'), ('793d6416-b9d7-11e6-8824-d69d90f82bef', '722b483c-b9d7-11e6-8824-d69d90f82bef', '9d3fbe6c-8c3d-11e6-a458-8c10aa25bc40'), ('793d6466-b9d7-11e6-8824-d69d90f82bef', '722b483c-b9d7-11e6-8824-d69d90f82bef', 'b3f33936-8c3d-11e6-a458-8c10aa25bc40'), ('81ba64d6-b75c-11e6-8059-e7d98718e227', '7eb42e52-b75c-11e6-8059-e7d98718e227', '001'), ('81ba6616-b75c-11e6-8059-e7d98718e227', '7eb42e52-b75c-11e6-8059-e7d98718e227', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('81ba66ac-b75c-11e6-8059-e7d98718e227', '7eb42e52-b75c-11e6-8059-e7d98718e227', '0d973cd8-9848-11e6-acd3-1b1104e15e33'), ('890cebfe-b6ea-11e6-8249-c8ddfd5ac8df', '002', '001'), ('890ced52-b6ea-11e6-8249-c8ddfd5ac8df', '002', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('890ced8e-b6ea-11e6-8249-c8ddfd5ac8df', '002', '0090f2de-8c3d-11e6-a458-8c10aa25bc40'), ('890cedca-b6ea-11e6-8249-c8ddfd5ac8df', '002', 'c655547e-8c3d-11e6-a458-8c10aa25bc40'), ('890cee06-b6ea-11e6-8249-c8ddfd5ac8df', '002', 'e4dd3a52-95f6-11e6-bdc5-5a61d81af47d'), ('890cee2e-b6ea-11e6-8249-c8ddfd5ac8df', '002', '0d973cd8-9848-11e6-acd3-1b1104e15e33'), ('c258152c-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', '001'), ('c25817ac-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('c2581810-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', '2082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('c258184c-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', '0090f2de-8c3d-11e6-a458-8c10aa25bc40'), ('c25818d8-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', 'c655547e-8c3d-11e6-a458-8c10aa25bc40'), ('c258191e-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', '18e87b64-95f6-11e6-bdc5-5a61d81af47d'), ('c2581950-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', 'e4dd3a52-95f6-11e6-bdc5-5a61d81af47d'), ('c2581982-ae2b-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1', '0d973cd8-9848-11e6-acd3-1b1104e15e33'), ('cea27086-f8f7-11e6-b7c6-a687b5f5ac7d', 'dd8f56e2-8edd-11e6-b577-12b5599b0132', '001'), ('cea272d4-f8f7-11e6-b7c6-a687b5f5ac7d', 'dd8f56e2-8edd-11e6-b577-12b5599b0132', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('cea2731a-f8f7-11e6-b7c6-a687b5f5ac7d', 'dd8f56e2-8edd-11e6-b577-12b5599b0132', '2082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('cea27356-f8f7-11e6-b7c6-a687b5f5ac7d', 'dd8f56e2-8edd-11e6-b577-12b5599b0132', '0090f2de-8c3d-11e6-a458-8c10aa25bc40'), ('cea27388-f8f7-11e6-b7c6-a687b5f5ac7d', 'dd8f56e2-8edd-11e6-b577-12b5599b0132', 'af82763e-d4ca-11e6-9c06-529f25dde3db'), ('cea273ba-f8f7-11e6-b7c6-a687b5f5ac7d', 'dd8f56e2-8edd-11e6-b577-12b5599b0132', 'a0f73386-dbf8-11e6-bbb0-395913546a19'), ('cea273e2-f8f7-11e6-b7c6-a687b5f5ac7d', 'dd8f56e2-8edd-11e6-b577-12b5599b0132', '28d6b6de-e68d-11e6-ba81-51f5dc7098ac');
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user`;
CREATE TABLE `tb_sys_user` (
  `ID` char(36) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `ACCOUNT` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `PASSWORD` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `PHONE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `NICKNAME` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `AVATAR` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `GENDER` tinyint(4) DEFAULT NULL COMMENT '性别',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后登录时间',
  `CREATOR_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_DEL` tinyint(4) DEFAULT '0' COMMENT '是否删除（0:未删除 1:删除）',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `ORG_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构ID',
  `EXTEND_IMG_URL` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展图片路径参数,仅做多图片上传测试用,无任何业务意义',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统用户表';

-- ----------------------------
--  Records of `tb_sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_user` VALUES ('1cf1ae44-cf42-11e6-bf08-1daa0106196d', 'gni47x', 'e10adc3949ba59abbe56e057f20f883e', '13651211311', 'asd', '/upload/user/2016-12/d9eafd0a-4505-4cef-9e58-d539ae1ec24e.jpg', null, null, null, '2016-12-31 18:15:54', '1', '2016-12-31 19:24:33', 'd96c9f84-9865-11e6-a3ae-9f1aa6f0c0f0', ''), ('314dab22-c5f6-11e6-973a-697510ee7ca2', 'test_avatar_002', 'e10adc3949ba59abbe56e057f20f883e', '18617221111', '测试头像-002', '/upload/user/2016-12/74003e1c-0950-4933-b647-3e8d38955bd5.jpg', null, null, null, '2016-12-19 22:19:46', '0', null, '367868fc-ba26-11e6-bb56-5eae4e7c831e', null), ('566a9fc6-c07b-11e6-a184-a3a75edd3e9c', 'naksdn123', '96e79218965eb72c92a549dd5a330112', '13214999499', 'asdver4', '/resources/upload/user/2016-12/efb55080-de5f-40de-b6bb-f7dfd6a07d24.jpg', null, null, null, '2016-12-12 22:57:44', '0', null, '3212bca4-9866-11e6-a3ae-9f1aa6f0c0f0', ''), ('59a89a2a-c4f0-11e6-9278-6663c098b7cc', 'test_multi_img', 'e10adc3949ba59abbe56e057f20f883e', '13694511211', '测试多图上传', '/resources/upload/user/2016-12/5ec22177-1ff9-4157-8e79-056b4ddcd43e.jpg', null, null, null, '2016-12-18 15:05:25', '0', null, '3212bca4-9866-11e6-a3ae-9f1aa6f0c0f0', '/resources/upload/multiImg/2016-12/2186f4ba-b9a6-479d-a879-2f7eb198c7e9.jpg,/resources/upload/multiImg/2016-12/a6914903-ab82-4e06-8cc7-f5d0841bf91a.jpg,/resources/upload/multiImg/2016-12/bd1d331e-5281-4344-89a7-7c8852f71cd0.jpg,/resources/upload/multiImg/2016-12/b1d745cc-781c-4bd1-9323-cc4233aff20b.jpg,/resources/upload/multiImg/2016-12/f76af1d6-f8c2-47b2-b17a-0764e98094a1.jpg'), ('721f69a8-f8f8-11e6-b7c6-a687b5f5ac7d', 'pageScope_test', 'e10adc3949ba59abbe56e057f20f883e', '18312331111', 'pageScope测试用户', '/upload/user/2017-02/e4f656e2-2c78-45ee-a7d4-7b9ef6335e56.jpg', null, null, null, '2017-02-22 20:14:23', '1', '2017-02-22 20:16:41', '367868fc-ba26-11e6-bb56-5eae4e7c831e', null), ('77ed7170-92b6-11e6-b1df-688b80e1e8e6', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '18617369765', '涛222133', '/resources/upload/user/2018-01/d29c9351-7181-498f-9dc6-c4b0c9133265.jpg', null, null, null, '2016-10-15 17:05:07', '0', null, '001', null), ('8c80d258-c079-11e6-a184-a3a75edd3e9c', 'test - 002', 'e10adc3949ba59abbe56e057f20f883e', '13255544444', 'test - 002', '', null, null, null, '2016-12-12 22:44:56', '0', null, '', null), ('9395a412-f8f8-11e6-b7c6-a687b5f5ac7d', '1234561asd', 'e10adc3949ba59abbe56e057f20f883e', '13211113222', 'asd123', '/upload/user/2017-02/502cd013-24e1-4c20-8c33-0b3ae06c77d4.jpg', null, null, null, '2017-02-22 20:15:19', '1', '2017-02-22 20:16:37', '4cd11ea0-9866-11e6-a3ae-9f1aa6f0c0f0', null), ('bdf2f8f6-9375-11e6-a76a-de1ef7809981', 'test001', 'e10adc3949ba59abbe56e057f20f883e', '18617360001', 'test - 001', '/resources/upload/user/2016-12/78379fbc-1d91-48f5-a84d-88567f8a9c91.jpg', null, null, null, '2016-10-16 15:54:18', '0', null, 'd96c9f84-9865-11e6-a3ae-9f1aa6f0c0f0', null), ('c8b00274-b68a-11e6-8b02-5b6f1c3fe7c9', 'test-001111111asdas3', null, '13265133213', '测试-003', '', null, null, null, '2016-11-30 07:23:07', '0', null, '367868fc-ba26-11e6-bb56-5eae4e7c831e', null), ('d4479b08-c079-11e6-a184-a3a75edd3e9c', '123xzv', '96e79218965eb72c92a549dd5a330112', '13211132111', 'asd123', '', null, null, null, '2016-12-12 22:46:56', '0', null, '', null);
COMMIT;

-- ----------------------------
--  Table structure for `tb_sys_user_role_ref`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user_role_ref`;
CREATE TABLE `tb_sys_user_role_ref` (
  `ID` char(36) COLLATE utf8_bin NOT NULL,
  `USER_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户和角色关联表';

-- ----------------------------
--  Records of `tb_sys_user_role_ref`
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_user_role_ref` VALUES ('2d00beae-b9c8-11e6-8824-d69d90f82bef', 'bdf2f8f6-9375-11e6-a76a-de1ef7809981', 'dd8f56e2-8edd-11e6-b577-12b5599b0132'), ('32659a7c-9849-11e6-acd3-1b1104e15e33', '2f333fd4-9377-11e6-a76a-de1ef7809981', 'dd8f56e2-8edd-11e6-b577-12b5599b0132'), ('36fb59d8-b6ed-11e6-8249-c8ddfd5ac8df', '1c2872b2-b6ed-11e6-8249-c8ddfd5ac8df', 'dd8f56e2-8edd-11e6-b577-12b5599b0132'), ('433d7dba-bae0-11e6-a95a-0bcee71ede00', 'be86f81a-b9d3-11e6-8824-d69d90f82bef', 'dd8f56e2-8edd-11e6-b577-12b5599b0132'), ('5002ad78-b75b-11e6-8059-e7d98718e227', '4a6a5fe6-b75b-11e6-8059-e7d98718e227', '2d2add08-b6ec-11e6-8249-c8ddfd5ac8df'), ('5002afc6-b75b-11e6-8059-e7d98718e227', '4a6a5fe6-b75b-11e6-8059-e7d98718e227', 'dd8f56e2-8edd-11e6-b577-12b5599b0132'), ('65e9d830-9372-11e6-a76a-de1ef7809981', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '001'), ('8f0530c0-ae2a-11e6-a27f-24ec3544b5c1', 'b2e782ba-ae28-11e6-a27f-24ec3544b5c1', '60c8b6dc-a97f-11e6-bc55-dbe4e4c83fe1'), ('d2152a58-9342-11e6-a76a-de1ef7809981', 'a5bdac98-92a5-11e6-b1df-688b80e1e8e6', '001'), ('d2152c42-9342-11e6-a76a-de1ef7809981', 'a5bdac98-92a5-11e6-b1df-688b80e1e8e6', 'dd8f56e2-8edd-11e6-b577-12b5599b0132'), ('d44e3364-9342-11e6-a76a-de1ef7809981', '63905c28-92b2-11e6-b1df-688b80e1e8e6', 'dd8f56e2-8edd-11e6-b577-12b5599b0132');
COMMIT;


-- ----------------------------
--  Table structure for `tb_api_access_token`
-- ----------------------------
DROP TABLE IF EXISTS `tb_api_access_token`;
CREATE TABLE `tb_api_access_token` (
  `ID` char(36) COLLATE utf8_bin NOT NULL,
  `USER_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '请求用户id',
  `ACCESS_TOKEN` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '访问令牌',
  `LAST_ACCESS_TIME` datetime DEFAULT NULL COMMENT '最后一次访问时间',
  `VALIDITY` int(11) DEFAULT NULL COMMENT 'token的有效期,单位分钟,再最后一次访问的基础上加上此时间进行验证',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='接口访问token';

-- ----------------------------
--  Table structure for `tb_operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log` (
  `ID` char(36) COLLATE utf8_bin NOT NULL,
  `USER_ID` char(36) COLLATE utf8_bin DEFAULT NULL COMMENT '操作用户id',
  `USER_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作用户名',
  `IP` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '操作用户ip',
  `MODULE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作模块',
  `OPERATION` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '执行操作',
  `DESCRIPTION` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态（0:失败 1:成功）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作日志表';


SET FOREIGN_KEY_CHECKS = 1;
