package com.dango.project.common;

/**
 * 错误码
 *
 * 这个枚举类定义了 API 接口可能返回的所有错误码。每个错误码包含两个属性：状态码和消息。状态码是一个整数，表示这个错误的编号；消息是一个字符串，表示这个错误的具体信息。
 *
 * 错误码是用于描述接口返回状态的一种规范，通常情况下，状态码应该是和 HTTP 状态码保持一致的。
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param code 状态码
     * @param message 消息
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取消息
     *
     * @return 消息
     */
    public String getMessage() {
        return message;
    }

}
