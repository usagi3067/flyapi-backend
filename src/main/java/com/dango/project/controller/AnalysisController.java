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
 * <p>
 * 这个类定义了用于分析的接口。这个类中定义了两个接口，分别是：
 * - 获得被调用次数最多的前三个接口
 * - 获得被调用次数最多的前三个用户
 * <p>
 * 这个类使用了 `@RestController` 注解和 `@RequestMapping` 注解，表示它是一个 SpringMVC 的控制器，并且所有的请求都以 `/analysis` 开头。
 * <p>
 * 这个类中使用了 `@AuthCheck` 注解，表示这些接口需要进行权限校验。只有具有管理员角色的用户才能够访问这些接口。
 * <p>
 * 这个类中使用了 `@Resource` 注解，表示它们需要使用依赖注入的方式获取到一些 bean，包括 `UserInterfaceInfoMapper`、`InterfaceInfoService` 和 `UserService`。
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
     *
     * @return 前三个被调用次数最多的接口信息
     * <p>
     * 这个方法使用了 `@GetMapping` 注解，表示它是一个 GET 请求处理方法，并且请求路径是 `/analysis/top/interface/invoke`。
     * 这个方法中首先通过调用 `userInterfaceInfoMapper.listTopInvokeInterfaceInfo` 方法获取被调用次数最多的前三个接口的信息，
     * 然后通过接口 ID 查询对应的接口信息，并将被调用次数设置到接口信息对象中。最后，将接口信息转换为 VO 类，
     * 并返回给客户端。
     */
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        // 下面的代码检索前三个被调用的用户界面的信息
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        // 通过它们各自的接口信息ID将用户界面信息对象分组
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        // 创建一个查询包装器对象，以通过其ID查询接口信息对象
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
        // 使用查询包装器从数据库中查询接口信息对象
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        // 如果列表为空，则抛出系统错误异常
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 将检索到的接口信息对象映射到其各自的视图对象
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();

            // 将属性从接口信息对象复制到视图对象
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            // 检索调用接口的总次数
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        // 以成功响应的形式返回视图对象
        return ResultUtils.success(interfaceInfoVOList);
    }

    /**
     * @return
     */
// 这是一个弹簧启动控制器方法,它处理对“/ / user /调用”的HTTP GET请求
// 只有具有“admin”角色的用户才能访问它，正如@AuthCheck注释指定的那样
    @GetMapping("/top/user/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<UserVO>> listTopInvokeUser() {

        // 检索向系统发出最多请求的前3个用户
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeUser(3);

        // 将检索到的用户界面信息按用户ID分组
        Map<Long, List<UserInterfaceInfo>> userIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getUserId));

        // 创建查询来检索已标识用户的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIdObjMap.keySet());
        List<User> list = userService.list(queryWrapper);

        // 如果未找到用户，则抛出系统错误
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        // 将检索到的用户信息转换为UserVO对象列表
        // 每个UserVO对象包含用户信息及其向系统发出的请求总数
        List<UserVO> userVOList = list.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            int totalNum = userIdObjMap.get(user.getId()).get(0).getTotalNum();
            userVO.setTotalNum(totalNum);
            return userVO;
        }).collect(Collectors.toList());

        // 返回一个带有UserVO对象列表的成功响应
        return ResultUtils.success(userVOList);
    }

}
