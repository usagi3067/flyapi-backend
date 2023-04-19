package com.dango.flyapiclientsdk.client;

import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.dango.flyapiclientsdk.model.User;
import lombok.extern.slf4j.Slf4j;


import java.util.HashMap;
import java.util.Map;

import static com.dango.flyapiclientsdk.utils.SignUtils.genSign;

/**
 * 调用第三方接口的客户端
 *
 * @author dango
 */

@Slf4j
public class FlyApiClient {

    //todo
    // 这个是开发环境 线上环境要改

    private static final String GATEWAY_HOST = "http://localhost:8090";
    private static final String MUSIC_HOST = "http://localhost:";

    private String accessKey;

    private String secretKey;

    public FlyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        // 一定不能直接发送
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
/*
    public String getInterfaceInfo(InterfaceInfo interfaceInfo, String userRequestParams) {
        // 创建一个 Gson 实例
        Gson gson = new Gson();

        // 使用 TypeToken 指定 HashMap 的键和值的类型
        TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>() {
        };

        // 将 JSON 字符串转换为 HashMap
        HashMap<String, String> resultMap = gson.fromJson(userRequestParams, typeToken.getType());
        UrlQuery urlQuery = new UrlQuery();
        // 打印转换后的 HashMap
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            urlQuery.add(entry.getKey(), entry.getValue());
        }

        HttpResponse httpResponse = HttpRequest.post(interfaceInfo.getUrl() + "?" + urlQuery.toString())
                .execute();


        return resultMap.toString();
    }*/


    public String getInterfaceInfo(String Url, String userRequestParams) {
        // 创建一个 Gson 实例
        Gson gson = new Gson();

        // 使用 TypeToken 指定 HashMap 的键和值的类型
        TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>() {
        };

        // 将 JSON 字符串转换为 HashMap
        HashMap<String, String> resultMap = gson.fromJson(userRequestParams, typeToken.getType());
        UrlQuery urlQuery = new UrlQuery();
        // 打印转换后的 HashMap
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            urlQuery.add(entry.getKey(), entry.getValue());
        }

        log.info("请求参数：{}", urlQuery);
        String path = GATEWAY_HOST + Url + "?" + urlQuery;
        log.info("请求地址：{}", path);
//        HttpResponse httpResponse = HttpRequest.post(Url + "?" + urlQuery.toString())
//                .execute();
        HttpResponse httpResponse = HttpRequest.post(path)
                .addHeaders(getHeaderMap("aa"))
                .execute();
        String body = httpResponse.body();


        return body;
    }
}
