package com.dango.flyapicommon.service;

import com.dango.flyapicommon.model.entity.InterfaceInfo;

/**
 *
 */
public interface InnerInterfaceInfoService {


    /**
     * 从数据库中查询模拟接口是否存在（路由path、请求方法）
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

}
