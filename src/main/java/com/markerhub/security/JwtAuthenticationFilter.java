package com.markerhub.security;


import cn.hutool.core.util.StrUtil;
import com.markerhub.sys.entity.SysUser;
import com.markerhub.sys.service.SysUserService;
import com.markerhub.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Jwt认证过滤器
 * @author zhang Bowen
 * @date 2021-10-26 15:55
 */

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    SysUserService sysUserService;

    public JwtAuthenticationFilter ( AuthenticationManager authenticationManager ) {
        super ( authenticationManager );
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader ( jwtUtil.getHeader () );
        //判断请求头中有没有信息
        //没有的话过滤器链接着往下走
        System.out.println (jwt);
        if ( StrUtil.isBlankOrUndefined ( jwt ) ) {
            chain.doFilter ( request, response );
            return;
        }

        Claims claimByToken = jwtUtil.getClaimByToken ( jwt );
        //判断jwt为空的话   jwt就是不合法的
        if ( claimByToken == null ) {
            throw new JwtException ( "token 异常" );
        }
        //判断jwt时间是否过期
        if ( jwtUtil.isTokenExpired ( claimByToken ) ) {
            throw new JwtException ( "token 已过期" );
        }
        String username = claimByToken.getSubject ();
        //获取用户信息

        SysUser sysUser = sysUserService.getByUsername ( username );
        //获取用户的权限信息
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken ( username, null, userDetailService.getUserAuthority ( sysUser.getId () ) );
        SecurityContextHolder.getContext ().setAuthentication ( token );
        chain.doFilter ( request,response );
    }
}
