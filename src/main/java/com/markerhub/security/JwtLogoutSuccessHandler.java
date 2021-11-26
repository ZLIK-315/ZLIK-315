package com.markerhub.security;

import cn.hutool.json.JSONUtil;
import com.markerhub.common.lang.Result;
import com.markerhub.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出处理
 * @author zhang Bowen
 * @date 2021-11-04 23:42
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public void onLogoutSuccess ( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException {

        if ( authentication != null ) {
            new SecurityContextLogoutHandler ().logout ( request,response,authentication );
        }
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();

        //生成JWT把它放到请求头里面
        response.setHeader ( jwtUtil.getHeader (),"" );

        Result result = Result.success("");
        outputStream.write( JSONUtil.toJsonStr(result).getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }
}
