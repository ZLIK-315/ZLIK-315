package com.markerhub.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zhang Bowen
 * @date 2021-11-30 11:06
 */
public class HttpContextUtils {
    private HttpContextUtils () { }
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull ( RequestContextHolder.getRequestAttributes ())).getRequest();
    }
}
