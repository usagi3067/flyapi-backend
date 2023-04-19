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
    baseUrl        varchar(512)                       not null comment '接口ip地址',
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
-- 插入接口信息

INSERT INTO flyapi.interface_info (id, name, description, baseUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (1, '网易云歌曲信息接口', '通过传入参数{id:number}查询该编号对应的歌曲信息', 'http://localhost', 3000, '/api/wyy/song/url', '{id:number}', null, null, 1, 'POST', 1, '{"id":33894312}', '2023-04-18 23:12:38', '2023-04-19 15:53:59', 0);
INSERT INTO flyapi.interface_info (id, name, description, baseUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (2, '随机土味情话接口', '通过传入参数{format:string}获取土味情话', 'https://api.uomg.com', 0, '/api/twqh/api/rand.qinghua', '{format:string}', null, null, 1, 'POST', 1, '{"format":"json"}', '2023-04-18 23:12:38', '2023-04-19 19:51:41', 0);
INSERT INTO flyapi.interface_info (id, name, description, baseUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (3, '网易云获取歌词接口', '通过传入参数{id:number}查询该编号对应的歌曲歌词', 'http://localhost', 3000, '/api/wyy/lyric', '{id:number}', null, null, 1, 'POST', 1, '{"id":33894312}', '2023-04-18 23:12:38', '2023-04-19 15:53:59', 0);

-- 插入用户信息
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (2, 'qiaqia', 'qiaqia', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '99089bf695a6240fc68ab181f8764898', '4c99997bc29afe847f8f71daf5abace8', '2023-04-19 20:22:08', '2023-04-19 20:22:08', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (3, 'tongtong', 'tongtong', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '47a0b7d636d9001461756d77b9d1f244', '699af4579ae9d7989172de4359ddaefa', '2023-04-19 20:22:28', '2023-04-19 20:55:52', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (4, 'xiaoxiao', 'xiaoxiao', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '7fd0db6d43d29ac96a3661ff6332b45d', '0cbd8647db5c43ede2e7e52c556d7721', '2023-04-19 20:35:10', '2023-04-19 20:35:10', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (5, 'hahaha', 'hahaha', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '28618691ed24967c9a92391d5318af0e', '4eb4231ead40023781ab4dbe25f48f0f', '2023-04-19 20:52:41', '2023-04-19 20:55:49', 0);

-- 插入用户接口信息
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (1, 1, 1, 18, 1982, 0, '2023-04-18 12:15:54', '2023-04-19 17:25:57', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (2, 1, 2, 6, 994, 0, '2023-04-19 00:55:19', '2023-04-19 20:56:28', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (3, 2, 1, 19, 2981, 0, '2023-04-19 20:23:32', '2023-04-19 20:54:45', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (4, 3, 2, 32, 1968, 0, '2023-04-19 20:33:02', '2023-04-19 20:33:55', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (5, 5, 3, 20, 980, 0, '2023-04-19 20:53:25', '2023-04-19 20:55:09', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (6, 1, 3, 20, 980, 0, '2023-04-19 20:53:25', '2023-04-19 20:55:09', 0);
