package com.dango.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * 这个类表示一个删除请求对象。它只包含一个 `id` 属性，表示要删除的对象的 ID。
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
