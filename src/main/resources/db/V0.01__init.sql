-- 元数据采集信息
drop table if exists `dsg_gather_info`;
create table `dsg_gather_info`
(
    `id`          varchar(50) not null comment 'id',
    `name`     varchar(500) not null comment '只是包信息',
    `ip`        varchar(500) default null comment '编码 数字字符下划线',
    `port`        varchar(500) default null comment '标签名字',
    `user`        varchar(500) default null comment '表达式类型',
    `password`  varchar(500) default null comment '表达式',
    `type`      varchar(500) default null comment '描述',
    `remark`      varchar(500) default null comment '描述',
    `create_time` timestamp NOT NULL  ,
    `update_time` timestamp NOT NULL  ,
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='元数据采集信息';
