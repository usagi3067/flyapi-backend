package com.dango.project.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class UserVO implements Serializable {
    /**
     * id
     */
    private Long id;


    /**
     * 账号
     */
    private String userAccount;


    /**
     * 用户角色: user, admin
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createTime;


    private static final long serialVersionUID = 1L;

    /**
     * 调用次数
     */
    private Integer totalNum;
}