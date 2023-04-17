package com.dango.flyapicommon.service;

import com.dango.flyapicommon.model.entity.UserInterfaceInfo;

/**
 *
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 查询用户是否有调用接口的权限
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    UserInterfaceInfo checkInvokePermission(long interfaceInfoId, long userId);
}
