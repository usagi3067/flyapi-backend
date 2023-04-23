package com.dango.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * 这个类用于封装 API 接口返回的结果。它包含三个属性：
 *
 * - code：表示接口返回的状态码；
 * - data：表示接口返回的数据对象；
 * - message：表示接口返回的消息，可以是错误消息或者其它消息。
 *
 * @param <T> data 属性的类型参数
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    /**
     * 构造函数，用于创建一个带有状态码、数据和消息的通用返回对象。
     *
     * @param code 状态码
     * @param data 数据对象
     * @param message 消息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 构造函数，用于创建一个带有状态码和数据的通用返回对象，消息为空字符串。
     *
     * @param code 状态码
     * @param data 数据对象
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 构造函数，用于创建一个从 ErrorCode 对象创建的通用返回对象。
     *
     * @param errorCode 错误码对象
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
