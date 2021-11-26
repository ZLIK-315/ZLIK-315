package com.markerhub.security;

import com.markerhub.common.exception.CaptchaException;
import com.markerhub.common.lang.Const;
import com.markerhub.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhang Bowen
 * @date 2021-10-25 17:44
 * 验证码过滤器
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if ("/login".equals(url) && request.getMethod().equals("POST")) {
            try {
                //校验验证码是否正确
                validata(request);
            }catch (CaptchaException e){
                // 如果不正确就跳转到失败认证处理器
                loginFailureHandler.onAuthenticationFailure(request,response,e);
            }
        }
        //如果正确过滤链接着往下面走
        filterChain.doFilter(request,response);
    }

    //校验的逻辑
    private void validata(HttpServletRequest request) {
        //首先要拿到这个前端传过来的code，key
        String code = request.getParameter("code");
        String key = request.getParameter("token");


        //先判断这两个值是否为空
        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            throw new CaptchaException("验证码错误");
        }
        //判断传过来的code和redis中的是不是一样的
        if (!code.equals(redisUtil.hget (Const.CAPTCHA_KEY, key))) {
            throw new CaptchaException("验证码错误");
        }
        //只能验证一次不能重复使用
        redisUtil.hdel (Const.CAPTCHA_KEY,key);
    }
}
