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

 Date: 01/11/2019 17:13:31 PM
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
INSERT INTO `tb_dictionary` VALUES ('001', '', '字典列表', null, null, null, '0', '1', '2016-10-20 22:51:15', '0', null), ('03fe5164-d4ef-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'singleImage', '单图片', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '3', '2017-01-07 23:36:11', '0', null), ('081ee934-d4ef-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'multiImage', '多图片', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '4', '2017-01-07 23:36:17', '0', null), ('22da831c-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'radio', '单选按钮', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '5', '2017-02-05 13:51:28', '0', null), ('278386e8-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'checkbox', '复选框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '6', '2017-02-05 13:51:35', '0', null), ('2b9f4e10-eb67-11e6-9b20-4157f2b6b97f', 'formType', '表单类型', 'select', '下拉框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '7', '2017-02-05 13:51:42', '0', null), ('83d1fb1e-ec66-11e6-9f79-67b34f704711', 'formType', '表单类型', 'richText', '富文本', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '8', '2017-02-06 20:19:32', '0', null), ('e714f7ce-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', null, null, '001', '1', '15', '2017-01-07 23:35:22', '0', null), ('ef44cc12-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'text', '文本框', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '1', '2017-01-07 23:35:36', '0', null), ('fdd5905e-d4ee-11e6-9c06-529f25dde3db', 'formType', '表单类型', 'datetime', '日期', 'e714f7ce-d4ee-11e6-9c06-529f25dde3db', '2', '2', '2017-01-07 23:36:00', '0', null);
COMMIT;

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
INSERT INTO `tb_operation_log` VALUES ('45af187c-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:43:58'), ('475c9e4c-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:44:00'), ('49ca2956-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:44:05'), ('4b038e02-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '组织机构管理', '删除组织机构', 'com.pay.platform.modules.sysmgr.organization.controller.OrganizationController.deleteOrganization()', '1', '2019-01-11 14:44:07'), ('69bb637e-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '删除字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.deleteDictionary()', '1', '2019-01-11 14:44:58'), ('6b1439e4-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '删除字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.deleteDictionary()', '1', '2019-01-11 14:45:00'), ('6c92f35a-156c-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '字典管理', '删除字典', 'com.pay.platform.modules.sysmgr.dictionary.controller.DictionaryController.deleteDictionary()', '1', '2019-01-11 14:45:03'), ('b3ce21aa-156b-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '角色管理', '删除角色', 'com.pay.platform.modules.sysmgr.role.controller.RoleController.deleteRole()', '1', '2019-01-11 14:39:53'), ('ee235820-156b-11e9-adea-1631b6fccd0d', '77ed7170-92b6-11e6-b1df-688b80e1e8e6', '啊涛', '0:0:0:0:0:0:0:1', '权限管理', '删除权限', 'com.pay.platform.modules.sysmgr.permission.controller.PermissionController.deletePermission()', '1', '2019-01-11 14:41:31');
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
INSERT INTO `tb_sys_permission` VALUES ('001', '权限列表', null, null, null, null, '0', null, null, null, '0', '2016-10-07 13:50:50'), ('0090f2de-8c3d-11e6-a458-8c10aa25bc40', '角色管理', '/sysmgr/role', '/view/sysmgr/role/role_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '1', '2', null, '1', '2016-10-07 11:20:31', '0', '2016-10-15 16:54:30'), ('0d973cd8-9848-11e6-acd3-1b1104e15e33', '日志管理', '/sysmgr/log', '/view/sysmgr/log/operation_log_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '5', '2', null, '1', '2016-10-22 19:09:51', '0', null), ('1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '系统管理', null, null, '001', '0', '1', 'fa fa-cog', '1', '2016-10-06 08:30:21', '0', '2016-10-07 13:50:50'), ('18e87b64-95f6-11e6-bdc5-5a61d81af47d', '组织机构', '/sysmgr/organization', '/view/sysmgr/organization/organization_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '3', '2', null, '1', '2016-10-19 20:18:09', '0', null), ('2082d6c6-8b5c-11e6-a458-8c10aa25bc40', '用户管理', '/sysmgr/user', '/view/sysmgr/user/user_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '0', '2', null, '1', '2016-10-06 14:52:25', '0', '2016-10-15 17:03:44'), ('8f21b758-e675-11e6-ba81-51f5dc7098ac', '树菜单生成', '/generate', '/view/generate/generate_tree.jsp', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '1', '2', null, '1', '2017-01-30 06:52:06', '0', null), ('9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '代码生成', null, null, '001', '7', '1', 'fa fa-wrench', '1', '2016-12-04 09:04:34', '0', null), ('c347c2be-b9bd-11e6-8824-d69d90f82bef', '单表生成', '/generate', '/view/generate/generate_single_table.jsp', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef', '2', '2', null, '1', '2016-12-04 09:05:35', '0', null), ('c655547e-8c3d-11e6-a458-8c10aa25bc40', '权限管理', '/sysmgr/permission', '/view/sysmgr/permission/permission_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '2', '2', null, '1', '2016-10-07 11:26:02', '0', null), ('e4dd3a52-95f6-11e6-bdc5-5a61d81af47d', '字典管理', '/sysmgr/dictionary', '/view/sysmgr/dictionary/dictionary_list.jsp', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40', '4', '2', null, '1', '2016-10-19 20:23:51', '0', null);
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
INSERT INTO `tb_sys_role_permission_ref` VALUES ('40f812e6-2033-11e7-892c-7baffad95003', '001', '001'), ('40f8150c-2033-11e7-892c-7baffad95003', '001', '1082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('40f81566-2033-11e7-892c-7baffad95003', '001', '2082d6c6-8b5c-11e6-a458-8c10aa25bc40'), ('40f81598-2033-11e7-892c-7baffad95003', '001', '0090f2de-8c3d-11e6-a458-8c10aa25bc40'), ('40f815ca-2033-11e7-892c-7baffad95003', '001', 'c655547e-8c3d-11e6-a458-8c10aa25bc40'), ('40f81606-2033-11e7-892c-7baffad95003', '001', '18e87b64-95f6-11e6-bdc5-5a61d81af47d'), ('40f81638-2033-11e7-892c-7baffad95003', '001', 'e4dd3a52-95f6-11e6-bdc5-5a61d81af47d'), ('40f81660-2033-11e7-892c-7baffad95003', '001', '0d973cd8-9848-11e6-acd3-1b1104e15e33'), ('40f81746-2033-11e7-892c-7baffad95003', '001', '9e9dbc3e-b9bd-11e6-8824-d69d90f82bef'), ('40f8176e-2033-11e7-892c-7baffad95003', '001', 'c347c2be-b9bd-11e6-8824-d69d90f82bef'), ('40f81796-2033-11e7-892c-7baffad95003', '001', '8f21b758-e675-11e6-ba81-51f5dc7098ac');
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
