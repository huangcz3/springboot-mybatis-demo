package com.neo.aop;

import com.neo.entity.UserEntity;

import com.neo.exception.BusinessException;
import com.neo.util.response.ResultEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 27351
 * @Date: 2018/7/17 18:08
 * @Description:
 */
@Aspect
@Component
public class CheckLoginAspect {

    private static final Logger logger = LoggerFactory.getLogger(CheckLoginAspect.class);


    /**
     * 切到CheckLogin注解的位置
     */
    @Pointcut("@annotation(com.neo.aop.CheckLogin)")
    public void loginCut() {

    }

    /**
     * 判断是否登陆
     *
     * @param joinPoint
     */
    @Before("loginCut()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        UserEntity userInfoDto = (UserEntity) request.getSession().getAttribute("user");
        if (userInfoDto == null) {
            throw new BusinessException(ResultEnum.NOT_LOGIN.getCode(), ResultEnum.NOT_LOGIN.getMsg());
        }
    }
}
