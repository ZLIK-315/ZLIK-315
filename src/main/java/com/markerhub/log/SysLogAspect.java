package com.markerhub.log;

import com.alibaba.fastjson.JSON;
import com.markerhub.sys.service.SysLogService;
import com.markerhub.sys.entity.SysLog;
import com.markerhub.utils.HttpContextUtils;
import com.markerhub.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * 系统日志：切面处理类
 * @author zhang Bowen
 * @date 2021-11-30 10:48
 */

@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 定义切点 @Pointcut
     * 在注解的位置切入代码
     */
    @Pointcut ("@annotation( com.markerhub.log.SysLogConfig )")
    public void logPoinCut() {
    }


    /**
     * 切面 配置通知
     * @param joinPoint
     */
    @AfterReturning ("logPoinCut()")
    public void saveSysLog( JoinPoint joinPoint) {
        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        SysLogConfig myLog = method.getAnnotation(SysLogConfig.class);
        if (myLog != null) {
            String value = myLog.value();
            //保存获取的操作
            sysLog.setOperation(value);
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        sysLog.setParams(params);

        sysLog.setCreated ( LocalDateTime.now ());
        //获取用户名
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
//        System.out.println (userDetails.getUsername ());
//        sysLog.setUsername(userDetails.getUsername ());

        //获取用户ip地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        Principal userPrincipal = request.getUserPrincipal ();
        sysLog.setUsername(userPrincipal.getName ());
        sysLog.setIp( IPUtils.getIpAddr(request));

        //调用service保存SysLog实体类到数据库
        System.out.println (sysLog);
        sysLogService.save(sysLog);
    }

}
