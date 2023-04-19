package com.dango.project.model.vo;

import com.dango.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author dango
 */
@Data
public class InterfaceInfoInvokeVO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址(由baseUrl、port、path拼接)
     */
    private String url;

    /**
     * 请求体
     * [
     *   {"name": "username", "type": "string"}
     * ]
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
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    
    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 使用示例
     */
    private String demo;

}