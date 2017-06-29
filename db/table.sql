CREATE DATABASE IF NOT EXISTS jadmin DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS `jadmin_project`;
CREATE TABLE `jadmin_project` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '项目名称',
  `code` varchar(20) NOT NULL COMMENT '项目编码',
  `isEnable` INT(1) NOT NULL COMMENT '是否有效，1-是；0-否',
  `secretKey` varchar(50) NOT NULL COMMENT '密钥',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY IDX_UQE_CODE (code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';


DROP TABLE IF EXISTS `jadmin_user`;
CREATE TABLE `jadmin_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `projectCode` varchar(20) NOT NULL COMMENT '项目编码',
  `userName` varchar(50) NOT NULL COMMENT '用户名',
  `passWord` varchar(50) NOT NULL COMMENT '密码',
  `realName` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(30) DEFAULT NULL COMMENT '联系手机号',
  `menuIds` text NOT NULL COMMENT '绑定的菜单id串，逗号分隔',
  `isEnable` INT(1) NOT NULL COMMENT '是否有效，1-是；0-否',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY IDX_UQE_PROJECT_USERNAME (projectCode, userName) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


DROP TABLE IF EXISTS `jadmin_menu`;
CREATE TABLE `jadmin_menu` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `projectCode` varchar(20) NOT NULL COMMENT '项目编码',
  `name` varchar(20) NOT NULL COMMENT '菜单名称',
  `code` varchar(30) NOT NULL COMMENT '菜单编码，每个项目唯一',
  `type` INT(1) NOT NULL COMMENT '菜单类型，0-菜单，1-按钮',
  `url` varchar(255) NOT NULL COMMENT '菜单路径，每个项目唯一',
  `level` int(2) NOT NULL COMMENT '菜单级别，层次，如1、2、3级菜单',
  `sort` int(3) NOT NULL COMMENT '排序',
  `isEnable` INT(1) NOT NULL COMMENT '是否有效，1-是；0-否',
  `pid` BIGINT(20) NOT NULL COMMENT '父菜单id',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY IDX_UQE_PROJECT_CODE (projectCode, code) USING BTREE,
  UNIQUE KEY IDX_UQE_PROJECT_URL (projectCode, url) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

