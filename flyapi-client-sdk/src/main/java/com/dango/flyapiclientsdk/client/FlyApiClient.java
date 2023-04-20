package com.dango.flyapiclientsdk.client;

import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.dango.flyapicommon.exception.BusinessException;
import com.dango.flyapicommon.exception.ErrorCode;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;


import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 *
 * @author dango
 */

@Slf4j
public class FlyApiClient {

    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }

    //todo
    // 这个是开发环境 线上环境要改

    private static final String GATEWAY_HOST = "http://localhost:8090";

    private String accessKey;

    private String secretKey;

    public FlyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
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


    public String invoke(String Url, String requestBody) {
        try {
            JsonParser.parseString(requestBody);
        } catch (JsonSyntaxException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "不满足Json格式,请修改");
        }

        // 创建一个 Gson 实例
        Gson gson = new Gson();


        // 使用 TypeToken 指定 HashMap 的键和值的类型
        TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>() {
        };

        // 将 JSON 字符串转换为 HashMap
        HashMap<String, String> resultMap = gson.fromJson(requestBody, typeToken.getType());
        UrlQuery urlQuery = new UrlQuery();
        // 打印转换后的 HashMap
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            urlQuery.add(entry.getKey(), entry.getValue());
        }

        log.info("请求参数：{}", urlQuery);
        String path = GATEWAY_HOST + Url + "?" + urlQuery;
        log.info("请求地址：{}", path);
        HttpResponse httpResponse = HttpRequest.post(path)
                .addHeaders(getHeaderMap("dango"))
                .execute();
        String body = httpResponse.body();
        return body;
    }
}
