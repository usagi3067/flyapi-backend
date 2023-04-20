package com.dango.project.controller;

import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dango.flyapicommon.model.entity.UserInterfaceInfo;
import com.dango.project.common.*;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.dango.project.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.dango.project.model.enums.InterfaceInfoStatusEnum;
import com.dango.project.model.vo.InterfaceInfoInvokeVO;
import com.dango.project.service.InterfaceInfoService;
import com.dango.project.service.UserInterfaceInfoService;
import com.dango.project.service.UserService;
import com.google.gson.Gson;
import com.dango.project.annotation.AuthCheck;
import com.dango.project.common.*;
import com.dango.project.constant.CommonConstant;
import com.dango.project.exception.BusinessException;
import com.dango.flyapiclientsdk.client.FlyApiClient;
import com.dango.flyapicommon.model.entity.InterfaceInfo;
import com.dango.flyapicommon.model.entity.User;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口管理
 *
 * @author dango
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;


    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @Transactional
    @AuthCheck(mustRole = "admin")
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        try {
            JsonParser.parseString(interfaceInfoAddRequest.getDemo());
        } catch (JsonSyntaxException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "示例不满足Json格式,请修改");
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();

        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();

        // 给管理员无线调用次数
        userInterfaceInfoService.addUserInterface(newInterfaceInfoId, loginUser.getId(), 999999);
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        try {
            JsonParser.parseString(interfaceInfoUpdateRequest.getDemo());
        } catch (JsonSyntaxException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "示例不满足Json格式,请修改");
        }


        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    private static final String GATEWAY_HOST = "http://localhost:8090";

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/getVO")
    public BaseResponse<InterfaceInfoInvokeVO> getInterfaceInfoVOById(Long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        // 将interfaceInfo信息复制到interfaceInfoInvokeVO
        InterfaceInfoInvokeVO interfaceInfoInvokeVO = new InterfaceInfoInvokeVO();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoInvokeVO);

        // 将GATEWAY_HOST和interfaceInfo对象的path拼接为url，传入interfaceInfoInvokeVO
        // 网关的baseUrl+接口的path
        interfaceInfoInvokeVO.setUrl(GATEWAY_HOST + interfaceInfo.getPath());
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


    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    // todo 需整合
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        String name = interfaceInfoQuery.getName();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        interfaceInfoQuery.setName(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * 发布
     *
     * @param idRequest
     * @param request
     * @return
     */
    @Transactional
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        // 1. 参数校验
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long interfaceId = idRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(interfaceId);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        String path = oldInterfaceInfo.getPath();
        String requestBody = oldInterfaceInfo.getDemo();
        try {
            JsonParser.parseString(requestBody);
        } catch (JsonSyntaxException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "示例不满足Json格式,请修改");
        }


        // 2. 设置管理员和接口关系表， 默认调用次数无限, 此处简单设置为999999
        User loginAdmin = userService.getLoginUser(request);

        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.queryUserInterfaceInfo(interfaceId, loginAdmin.getId());
        if (userInterfaceInfo == null) {
            userInterfaceInfoService.addUserInterface(interfaceId, loginAdmin.getId(), 999999);
        } else if (userInterfaceInfo.getLeftNum() <= 0) {
           userInterfaceInfo.setLeftNum(999999);
              userInterfaceInfoService.updateByIdWithoutTransaction(userInterfaceInfo);
        }

        // 3. 调用网关接口, 上线前测试接口是否可用
        String accessKey = loginAdmin.getAccessKey();
        String secretKey = loginAdmin.getSecretKey();
        FlyApiClient flyApiClient = new FlyApiClient(accessKey, secretKey);
        try {
            String json = flyApiClient.invoke(path, requestBody);
            JsonParser.parseString(json);
        } catch (JsonSyntaxException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "请求的资源不存在");
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "请检查接口详情是否有误");
        }

        // 4. 上线接口
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(interfaceId);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                      HttpServletRequest request) {
        // 1. 参数校验
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();

        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 2. 下线接口
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }


    /**
     * 平台在线调用
     *
     * @param interfaceInfoInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                    HttpServletRequest request) {
        // 1. 参数校验
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = interfaceInfoInvokeRequest.getId();

        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (interfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }

        // 2. 获取用户ak sk 调用接口前先鉴权
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        FlyApiClient tempClient = new FlyApiClient(accessKey, secretKey);

        // 3. 根据用户选择的接口路径和请求体调用接口
        String path = interfaceInfo.getPath();
        String requestBody = interfaceInfoInvokeRequest.getRequestBody();
        // 4. 使用jdk签名算法调用接口， 返回调用结果
        String result = tempClient.invoke(path, requestBody);

        return ResultUtils.success(result);
    }

}
