package com.dango.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口ip地址
     */
    private String baseUrl;


    /**
     * 接口端口号
     */
    private Integer port;

    /**
     * 接口路径
     */
    private String path;


    /**
     * 请求参数
     */
    private String requestBody;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 使用示例
     */
    private String demo;


}