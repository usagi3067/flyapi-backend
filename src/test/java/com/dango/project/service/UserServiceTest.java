package com.dango.project.service;

import cn.hutool.core.net.url.UrlQuery;
import com.dango.flyapicommon.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务测试
 *
 * @author dango
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        String jsonString = "{\"id\":33894312}";

// 创建一个 Gson 实例
        Gson gson = new Gson();

// 使用 TypeToken 指定 HashMap 的键和值的类型
        TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>() {};

// 将 JSON 字符串转换为 HashMap
        HashMap<String, String> resultMap = gson.fromJson(jsonString, typeToken.getType());
        UrlQuery urlQuery = new UrlQuery();
// 打印转换后的 HashMap
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            urlQuery.add(entry.getKey(), entry.getValue());
        }
        System.out.println(urlQuery);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }
}