package com.dango.project.aop;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dango.project.annotation.AuthCheck;
import com.dango.project.common.ErrorCode;
import com.dango.project.exception.BusinessException;
import com.dango.project.service.UserService;
import com.dango.flyapicommon.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限校验 AOP
 *
 * 使用 @AuthCheck 注解标注需要进行权限校验的方法。
 *
 * 如果方法没有任何 @AuthCheck 注解，则不会进行权限校验。
 *
 * @see com.dango.project.annotation.AuthCheck
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取允许任意访问的角色列表
        List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        // 获取必须要有的角色
        String mustRole = authCheck.mustRole();
        // 获取当前请求对象
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取当前登录用户
        User user = userService.getLoginUser(request);
        // 如果允许任意访问的角色列表不为空，则只需要满足其中任意一个角色即可通过权限校验
        if (CollectionUtils.isNotEmpty(anyRole)) {
            String userRole = user.getUserRole();
            if (!anyRole.contains(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        // 如果必须要有的角色不为空，则必须满足该角色才能通过权限校验
        if (StringUtils.isNotBlank(mustRole)) {
            String userRole = user.getUserRole();
            if (!mustRole.equals(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
