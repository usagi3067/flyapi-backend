package com.dango.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.flyapicommon.model.entity.InterfaceInfo;

/**
 *
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
