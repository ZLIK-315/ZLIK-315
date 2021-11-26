package com.markerhub.security;

import cn.hutool.json.JSONUtil;
import com.markerhub.common.lang.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt认证失败处理
 * @author Administrator
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence ( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus ( HttpServletResponse.SC_UNAUTHORIZED );
        ServletOutputStream outputStream = response.getOutputStream();
        Result result = Result.fail("认证失败请先登录");
        outputStream.write( JSONUtil.toJsonStr(result).getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }
}
