package com.dango.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.project.mapper.UserInterfaceInfoMapper;
import com.dango.project.common.ErrorCode;
import com.dango.project.exception.BusinessException;
import com.dango.project.service.UserInterfaceInfoService;
import com.dango.flyapicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{



    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于 0");
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);

//        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }

    @Override
    public UserInterfaceInfo checkInvokePermission(long interfaceInfoId, long userId) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 根据用户id和接口id查询
        UserInterfaceInfo userInterfaceInfo = this.queryUserInterfaceInfo(interfaceInfoId, userId);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户无调用权限");
        }
        return userInterfaceInfo;
    }

    @Override
    public UserInterfaceInfo addUserInterface(long interfaceInfoId, long userId) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
        userInterfaceInfo.setUserId(userId);
        userInterfaceInfo.setLeftNum(0);
        userInterfaceInfo.setTotalNum(0);
        this.save(userInterfaceInfo);
        return userInterfaceInfo;
    }

    @Override
    public UserInterfaceInfo addUserInterface(long interfaceInfoId, long userId, int leftNum) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
        userInterfaceInfo.setUserId(userId);
        userInterfaceInfo.setLeftNum(leftNum);
        userInterfaceInfo.setTotalNum(0);
        this.save(userInterfaceInfo);
        return userInterfaceInfo;
    }

    @Override
    public UserInterfaceInfo queryUserInterfaceInfo(long interfaceInfoId, long userId) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 根据用户id和接口id查询
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        queryWrapper.eq("userId", userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public void updateByIdWithoutTransaction(UserInterfaceInfo userInterfaceInfo) {
        updateById(userInterfaceInfo);
    }

}




