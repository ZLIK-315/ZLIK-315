package com.markerhub.security;

import cn.hutool.json.JSONUtil;
import com.markerhub.common.lang.Result;
import com.markerhub.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhang Bowen
 * @date 2021-10-25 17:31
 * 认证成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        //生成JWT把它放到请求头里面
        String jwt = jwtUtil.generateToken ( authentication.getName () );
        httpServletResponse.setHeader ( jwtUtil.getHeader (),jwt );

        Result result = Result.success("???");
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }
}
