package com.dango.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dango.flyapicommon.model.entity.User;
import com.dango.project.common.BaseResponse;
import com.dango.project.common.ResultUtils;
import com.dango.project.mapper.UserInterfaceInfoMapper;
import com.dango.project.model.vo.InterfaceInfoVO;
import com.dango.project.model.vo.UserVO;
import com.dango.project.service.InterfaceInfoService;
import com.dango.project.annotation.AuthCheck;
import com.dango.project.common.ErrorCode;
import com.dango.project.exception.BusinessException;
import com.dango.flyapicommon.model.entity.InterfaceInfo;
import com.dango.flyapicommon.model.entity.UserInterfaceInfo;
import com.dango.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析控制器
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    /**
     * 获得被调用次数最多的前三个接口
     * @return
     */
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOList);
    }

    /**
     *
     * @return
     */
    @GetMapping("/top/user/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<UserVO>> listTopInvokeUser() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeUser(3);
        Map<Long, List<UserInterfaceInfo>> userIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getUserId));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIdObjMap.keySet());
        List<User> list = userService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<UserVO> userVOList = list.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            int totalNum = userIdObjMap.get(user.getId()).get(0).getTotalNum();
            userVO.setTotalNum(totalNum);
            return userVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVOList);
    }
}
