use flyapi;


-- 接口信息
create table if not exists flyapi.`interface_info`
(
    `id`             bigint                             not null auto_increment comment '主键' primary key,
    `name`           varchar(256)                       not null comment '名称',
    `description`    varchar(256)                       null comment '描述',
    `url`            varchar(512)                       not null comment '接口地址',
    `requestParams`  text                               not null comment '请求参数',
    `requestHeader`  text                               null comment '请求头',
    `responseHeader` text                               null comment '响应头',
    `status`         int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    `method`         varchar(256)                       not null comment '请求类型',
    `userId`         bigint                             not null comment '创建人',
    `createTime`     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';


-- 用户调用接口关系表
create table if not exists flyapi.`user_interface_info`
(
    `id`              bigint                             not null auto_increment comment '主键' primary key,
    `userId`          bigint                             not null comment '调用用户 id',
    `interfaceInfoId` bigint                             not null comment '接口 id',
    `totalNum`        int      default 0                 not null comment '总调用次数',
    `leftNum`         int      default 0                 not null comment '剩余调用次数',
    `status`          int      default 0                 not null comment '0-正常，1-禁用',
    `createTime`      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';


use flyapi;
-- auto-generated definition
-- 接口信息
create table if not exists flyapi.interface_info
(
    id             bigint auto_increment comment '主键'    primary key,
    name           varchar(256)                       not null comment '名称',
    description    varchar(256)                       null comment '描述',
    bashUrl        varchar(512)                       not null comment '接口ip地址',
    port           int                                not null comment '接口端口号',
    path           varchar(1024)                      not null comment '接口路径',
    requestBody    text                               not null comment '请求体',
    requestHeader  text                               null comment '请求头',
    responseHeader text                               null comment '响应头',
    status         int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    method         varchar(256)                       not null comment '请求类型',
    userId         bigint                             not null comment '创建人',
    demo           varchar(512)                       not null comment '使用示例',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
    )
    comment '接口信息';
INSERT INTO flyapi.interface_info (id, name, description, bashUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (1, '网易云歌曲信息接口', '通过传入参数{id:number}查询该编号对应的歌曲信息', 'http://localhost', 3000, '/api/wyy/song/url', '{id:number}', null, null, 1, 'POST', 1, '{"id":33894312}', '2023-04-18 23:12:38', '2023-04-19 15:53:59', 0);

INSERT INTO flyapi.interface_info (id, name, description, bashUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (2, '随机土味情话接口', '通过传入参数{format:string}获取土味情话', 'https://api.uomg.com', 0, '/api/twqh/api/rand.qinghua', '{format:string}', null, null, 1, 'POST', 1, '{"format":"json"}', '2023-04-18 23:12:38', '2023-04-19 15:53:59', 0);
