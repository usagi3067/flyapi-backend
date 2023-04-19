package com.dango.project.model.dto.interfaceinfo;

import com.dango.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author dango
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

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
     * 请求类型
     */
    private String method;

    /**
     * 接口地址
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
     * 使用示例
     */
    private String demo;

    /**
     * 创建人
     */
    private Long userId;

}