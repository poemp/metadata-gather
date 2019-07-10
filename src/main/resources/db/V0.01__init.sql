-- 元数据采集信息
drop table if exists `dsg_gather_info`;
create table `dsg_gather_info`
(
    `id`                varchar(50) not null comment 'id',
    `name`              varchar(500) not null comment '连接的名字',
    `ip`                varchar(500) default null comment '连接ip',
    `port`              varchar(500) default null comment '连接的端口',
    `user`              varchar(500) default null comment '数据库连接的用户名字',
    `password`          varchar(500) default null comment '数据库连接的密码',
    `type`              varchar(500) default null comment '数据库类型 mysql或者是 orcale',
    `remark`            varchar(500) default null comment '描述',
    `delete`            boolean default null comment '是否删除',
    `create_time`       timestamp NOT NULL  ,
    `update_time`       timestamp NOT NULL  ,
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='元数据采集信息';



--  采集到库信息
drop table if exists `dsg_gather_db`;
create table `dsg_gather_db`
(
    `id`                varchar(50) not null comment 'id',
    `gather_id`         varchar(500) not null comment '元数据采集信息',
    `schema`            varchar(500) default null comment 'schema',
    `description`       varchar(225) DEFAULT NULL comment '描述',
    `create_time`       timestamp NOT NULL  ,
    `update_time`       timestamp NOT NULL  ,
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='采集到库信息';




--  采集表信息
drop table if exists `dsg_gather_table`;
create table `dsg_gather_table`
(
    `id`                    varchar(50) not null comment 'id',
    `gather_db_id`          varchar(500) not null comment '元数据数据库信息',
    `table`                 varchar(500) default null comment '数据库表',
    `table_name`            varchar(500) default null comment '数据库表名字',
    `comment`               varchar(1000) DEFAULT NULL comment '描述',
    `create_time`           timestamp NOT NULL  ,
    `update_time`           timestamp NOT NULL  ,
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='采集表信息';


--  采集表字端信息
drop table if exists `dsg_gather_table_fields`;
create table `dsg_gather_table_fields`
(
    `id`                    varchar(50) not null comment 'id',
    `gather_table_id`       varchar(500) default null comment '表',
    `field_name`            varchar(225) DEFAULT NULL comment '字段',
    `data_type`             varchar(225) DEFAULT NULL comment '数据类型',
    `column_name`           varchar(225) DEFAULT NULL comment '描述',
    `default_value`         varchar(225) DEFAULT NULL comment '默认值',
    `data_type_length`      varchar(225) DEFAULT NULL comment '数据长度',
    `create_time`           timestamp NOT NULL  ,
    `update_time`           timestamp NOT NULL  ,
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='采集表字端信息';


ALTER TABLE `dsg_gather_info`
    ADD COLUMN `service_name` varchar(255) NULL COMMENT 'oracle 服务名字' AFTER `type`;