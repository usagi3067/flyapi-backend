package com.dango.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dango.flyapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * @Entity com.dango.project.model.entity.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

    List<UserInterfaceInfo> listTopInvokeUser(int limit);
}




