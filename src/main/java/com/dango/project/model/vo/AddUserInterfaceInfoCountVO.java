package com.dango.project.model.vo;

import lombok.Data;

/**
 * 添加用户接口调用次数
 */
@Data
public class AddUserInterfaceInfoCountVO {
    private Long interfaceInfoId;
    private Integer count;
}
