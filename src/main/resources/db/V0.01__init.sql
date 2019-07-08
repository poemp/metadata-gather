# 标签
drop table if exists `dsg_urule_target`;
create table `dsg_urule_target`
(
    `id`          varchar(50) not null comment 'id',
    `code`        varchar(500) default null comment '编码 数字字符下划线',
    `name`        varchar(500) default null comment '标签名字',
    `type`        varchar(500) default null comment '表达式类型',
    `expression`  varchar(500) default null comment '表达式',
    `package`     varchar(500) not null comment '只是包信息',
    `remark`      varchar(500) default null comment '描述',
    `delete`      boolean     default null comment '是否删除',
    `create_time` timestamp NOT NULL  ,
    `update_time` timestamp NOT NULL  ,
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='标签';