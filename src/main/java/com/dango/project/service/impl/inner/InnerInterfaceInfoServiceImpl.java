package com.dango.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dango.project.mapper.InterfaceInfoMapper;
import com.dango.project.common.ErrorCode;
import com.dango.project.exception.BusinessException;
import com.dango.flyapicommon.model.entity.InterfaceInfo;
import com.dango.flyapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("path", path);
        queryWrapper.eq("method", method);
        InterfaceInfo one = interfaceInfoMapper.selectOne(queryWrapper);
        return one;
//        if (StringUtils.isAnyBlank(url, method)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("url", url);
//        queryWrapper.eq("method", method);
//        InterfaceInfo one = interfaceInfoMapper.selectOne(queryWrapper);
//        return one;
        //return interfaceInfoMapper.selectOne(queryWrapper);
    }

}
