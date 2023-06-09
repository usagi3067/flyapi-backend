package com.dango.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.flyapicommon.model.entity.UserInterfaceInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

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


    /**
     * 添加用户接口
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    UserInterfaceInfo addUserInterface(long interfaceInfoId, long userId);


    /**
     * 添加用户接口，指定剩余次数
     * @param interfaceInfoId
     * @param userId
     * @param leftNum
     * @return
     */
    public UserInterfaceInfo addUserInterface(long interfaceInfoId, long userId, int leftNum);

    /**
     * 查询用户接口关系是否存在
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    UserInterfaceInfo queryUserInterfaceInfo(long interfaceInfoId, long userId);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void updateByIdWithoutTransaction(UserInterfaceInfo userInterfaceInfo);
}
