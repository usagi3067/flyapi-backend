use flyapi;

-- 用户信息
create table if not exists flyapi.user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    accessKey    varchar(512)                           not null comment 'accessKey',
    secretKey    varchar(512)                           not null comment 'secretKey',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
)
    comment '用户';



-- 接口信息
create table if not exists flyapi.interface_info
(
    id             bigint auto_increment comment '主键'
        primary key,
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




-- 用户调用接口关系表
create table flyapi.user_interface_info
(
    id              bigint auto_increment comment '主键'
        primary key,
    userId          bigint                             not null comment '调用用户 id',
    interfaceInfoId bigint                             not null comment '接口 id',
    totalNum        int      default 0                 not null comment '总调用次数',
    leftNum         int      default 0                 not null comment '剩余调用次数',
    status          int      default 0                 not null comment '0-正常，1-禁用',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)',
    constraint user_interface_unique
        unique (userId, interfaceInfoId)
)
    comment '用户调用接口关系';


-- 插入接口信息

INSERT INTO flyapi.interface_info (id, name, description, baseUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (1, '网易云歌曲信息接口', '通过传入参数{id:number}查询该编号对应的歌曲信息', 'http://localhost', 3000, '/api/wyy/song/url', '{id:number}', '', '', 1, 'POST', 1, '{"id":33894312}', '2023-04-18 23:12:38', '2023-04-20 15:23:35', 0);
INSERT INTO flyapi.interface_info (id, name, description, baseUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (2, '随机土味情话接口', '通过传入参数{format:string}获取土味情话', 'https://api.uomg.com', 0, '/api/twqh/api/rand.qinghua', '{format:string}', null, null, 1, 'GET', 1, '{"format":"json"}', '2023-04-18 23:12:38', '2023-04-20 11:28:54', 1);
INSERT INTO flyapi.interface_info (id, name, description, baseUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (3, '网易云获取歌词接口', '通过传入参数{id:number}查询该编号对应的歌曲歌词', 'http://localhost', 3000, '/api/wyy/lyric', '{id:number}', null, null, 1, 'POST', 1, '{"id":33894312}', '2023-04-18 23:12:38', '2023-04-20 21:55:40', 0);
INSERT INTO flyapi.interface_info (id, name, description, baseUrl, port, path, requestBody, requestHeader, responseHeader, status, method, userId, demo, createTime, updateTime, isDelete) VALUES (4, '123', '123', 'https://api.btstu.cn', 123, '/api/djt/yan/api.php', '123', '123', '123', 1, 'POST', 1, '{"charset":"utf-8","encode":"json"}', '2023-04-19 23:47:11', '2023-04-20 23:57:29', 0);

-- 插入用户信息
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (1, 'dango', 'dango', 'https://wallpaperaccess.com/full/417550.jpg', null, 'admin', '9d6541b97bbfb4cf352fc68c5e66d88f', '8562b97c385be27e20de78059e390042', '7d0d799cce7138034947ca63c8b435a8', '2023-04-12 00:05:48', '2023-04-21 13:17:24', 1);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (2, 'qiaqia', 'qiaqia', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '99089bf695a6240fc68ab181f8764898', '4c99997bc29afe847f8f71daf5abace8', '2023-04-19 20:22:08', '2023-04-19 20:22:08', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (3, 'tongtong', 'tongtong', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '47a0b7d636d9001461756d77b9d1f244', '699af4579ae9d7989172de4359ddaefa', '2023-04-19 20:22:28', '2023-04-19 20:55:52', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (4, 'xiaoxiao', 'xiaoxiao', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '7fd0db6d43d29ac96a3661ff6332b45d', '0cbd8647db5c43ede2e7e52c556d7721', '2023-04-19 20:35:10', '2023-04-19 20:35:10', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (5, 'hahaha', 'hahaha', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '9d6541b97bbfb4cf352fc68c5e66d88f', '28618691ed24967c9a92391d5318af0e', '4eb4231ead40023781ab4dbe25f48f0f', '2023-04-19 20:52:41', '2023-04-19 20:55:49', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (6, '15521474798', '15521474798', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '2e56494ddff52a1611655db94634161f', 'f64f717cf1e1f7c32c78de1f46afd9f3', 'f0d454fd100f20ad5590a3c17d5da732', '2023-04-16 17:22:38', '2023-04-16 17:22:38', 0);
INSERT INTO flyapi.user (id, userName, userAccount, userAvatar, gender, userRole, userPassword, accessKey, secretKey, createTime, updateTime, isDelete) VALUES (7, 'xiaoqiatong', 'xiaoqiatong', 'https://tupian.qqw21.com/article/UploadPic/2020-10/202010272223486473.jpg', null, 'user', '8eccdca38bdc51c9b3bb53ab17fe42f0', '1b08fe4fc785f3e7ac5ad227929bb4da', '87e794e9fb8444c760c0b39d8912af71', '2023-04-16 19:10:12', '2023-04-16 19:11:27', 0);

-- 插入用户接口信息
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (1, 1, 1, 46, 10954, 0, '2023-04-18 12:15:54', '2023-04-21 13:17:23', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (2, 1, 2, 6, 994, 0, '2023-04-19 00:55:19', '2023-04-19 20:56:28', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (3, 2, 1, 1000, 2000, 0, '2023-04-19 20:23:32', '2023-04-20 11:22:57', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (4, 3, 2, 32, 1968, 0, '2023-04-19 20:33:02', '2023-04-19 20:33:55', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (5, 5, 3, 20, 980, 0, '2023-04-19 20:53:25', '2023-04-19 20:55:09', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (6, 1, 3, 81, 919, 0, '2023-04-19 20:53:25', '2023-04-21 11:20:50', 0);
INSERT INTO flyapi.user_interface_info (id, userId, interfaceInfoId, totalNum, leftNum, status, createTime, updateTime, isDelete) VALUES (16, 1, 4, 151, 999, 0, '2023-04-20 02:20:26', '2023-04-21 00:16:40', 0);
