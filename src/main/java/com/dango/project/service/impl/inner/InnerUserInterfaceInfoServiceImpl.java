package com.dango.project.service.impl.inner;

import com.dango.flyapicommon.model.entity.UserInterfaceInfo;
import com.dango.project.service.UserInterfaceInfoService;
import com.dango.flyapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    @Override
    public UserInterfaceInfo checkInvokePermission(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.checkInvokePermission(interfaceInfoId, userId);
    }
}
