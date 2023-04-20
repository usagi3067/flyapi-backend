# FlyAPI

## 概述

主页菜单下可查看开放平台下所有的接口， 点击任一接口可查看详情， 支持在线调试， 接口详情下有在线调试示例（可复制在线调试）， 用户可以使用json格式工具搭配使用



## java SDK 快速开发

### Maven 坐标

在项目的pom.xml配置文件中导入依赖

```xml
<dependencies>
	<dependency>
   	 	<groupId>com.dango</groupId>
    	<artifactId>flyapi-client-sdk</artifactId>
   	 	<version>0.0.1</version>
	</dependency>
</dependencies>
```

### 配置管理

在项目的application.yml文件中设置AccessKey和SecretKey

```yml
flyapi:
  client:
    access-key: <your AccessKey>
    secret-key: <your SecretKey>
```



### 接口信息

| 接口名称           | 接口路径          | 请求体          |
| ------------------ | ----------------- | --------------- |
| 网易云获取音乐链接 | /api/wyy/song/url | {"id" : number} |
| ...                | ...               |                 |
| ...                | ...               |                 |



### 请求示例

在使用时创建FlyApiClient对象，并调用getInterfaceInfo方法即可

示例：

```java
@RestController
@RequestMapping("/")
public class InterfaceInfoController {

    /**
     *  注入FlyApiClient
     */
    @Resource
    private FlyApiClient flyApiClient;

    /**
     * 调用接口
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo() {
        String requestBody = "{'id':33894312}";  // 需符合json语法
        String path = "/api/wyy/song/url";
        String info = tempClient.getInterfaceInfo(path, requestBody);
        if (StringUtils.isBlank(info)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口失效");
        }
        log.debug(info);
        return ResultUtils.success(info);
    }

}
```





## 查看接口列表

点击菜单栏的主页，即可查看接口列表和接口的部分信息



## 在线调用接口

### 调用前须知

本项目支持 POST 请求，进入主页，点击接口进入调用页面后，根据接口信息编辑请求体即可轻松调用接口

> 注意：请求体请输入json格式，具体字段数据类型可参考接口信息中的请求体、请求示例

### 网易云获取音乐链接

说明 : 使用歌单详情接口后 , 能得到的音乐的 id, 但不能得到的音乐 url, 调用此接口 , 传入的音乐 id, 可以获取对应的音乐的 url( 不需要登录 )

**请求体 :** `id` : 音乐 id

**接口地址 :** `/song/url`

**请求示例** :

```json
 {
     "id":33894312
 }
```

### wyy_song

调用此接口 , 传入歌曲id, 可以获取歌曲详情

### wyy_music

调用此接口 , 传入歌单id, 可以获取歌单详情





### 注意：

参数格式为json，可在菜单栏提供的json编辑器上编辑





## 退出登录

//这里有一个问题，最上面的模块太长，挡住了头像位置

点击右上角的头像，在弹出的下拉框中点击退出登录







## 查看当前可调用的接口



说明 : 登陆后调用此接口 , 传入用户 id, 可以获取用户详情

**必选参数 :** `uid` : 用户 id

**接口地址 :** `/user/detail`

**调用例子 :** `/user/detail?uid=32953014`

返回数据如下图 :








