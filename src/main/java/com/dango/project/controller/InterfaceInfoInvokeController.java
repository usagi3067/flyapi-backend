package com.dango.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dango.flyapiclientsdk.client.FlyApiClient;
import com.dango.flyapicommon.model.entity.InterfaceInfo;
import com.dango.flyapicommon.model.entity.User;
import com.dango.flyapicommon.model.entity.UserInterfaceInfo;
import com.dango.project.annotation.AuthCheck;
import com.dango.project.common.*;
import com.dango.project.constant.CommonConstant;
import com.dango.project.exception.BusinessException;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.dango.project.model.enums.InterfaceInfoStatusEnum;
import com.dango.project.model.vo.InterfaceInfoInvokeVO;
import com.dango.project.service.InterfaceInfoService;
import com.dango.project.service.UserInterfaceInfoService;
import com.dango.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口管理
 *
 * @author dango
 */
@RestController
@RequestMapping("/interfaceInfoInvoke")
@Slf4j
public class InterfaceInfoInvokeController {

    @Resource
    private InterfaceInfoService interfaceInfoService;


    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private FlyApiClient flyApiClient;

    // region 增删改查


    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfoInvokeVO> getInterfaceInfoById(Long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        // 将interfaceInfo信息复制到interfaceInfoInvokeVO
        InterfaceInfoInvokeVO interfaceInfoInvokeVO = new InterfaceInfoInvokeVO();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoInvokeVO);
        // 通过request获取当前用户信息
        User user = userService.getLoginUser(request);

        // 通过用户id 和 接口id 查询用户接口信息
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.queryUserInterfaceInfo(interfaceInfo.getId(), user.getId());
        // 判空
        if (userInterfaceInfo == null) {
            interfaceInfoInvokeVO.setLeftNum(0);
            return ResultUtils.success(interfaceInfoInvokeVO);
        }
        // 获取用户接口信息中的剩余调用次数
        interfaceInfoInvokeVO.setLeftNum(userInterfaceInfo.getLeftNum());

        return ResultUtils.success(interfaceInfoInvokeVO);
    }

}
