package com.markerhub.security;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.markerhub.common.lang.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义禁止访问处理器
 * @author zhang Bowen
 * @date 2021-10-28 20:06
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle ( HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException ) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus ( HttpServletResponse.SC_FORBIDDEN );
        ServletOutputStream outputStream = response.getOutputStream();
        Result result = Result.fail(accessDeniedException.getMessage ());
        outputStream.write( JSONUtil.toJsonStr(result).getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }
}
