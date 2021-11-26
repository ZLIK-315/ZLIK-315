package com.markerhub.config;


import com.markerhub.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.security.krb5.Config;

/**
 * security拦截配置
 * @author zhang Bowen
 * @date 2021-10-25 13:58
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private CaptchaFilter captchaFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
       return new JwtAuthenticationFilter (authenticationManager ());

    };

    //不需要拦截的地址
    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
    };

    /**
     * 密码加密方式
     * @return
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder (  );
    }


    @Override
    protected void configure( HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                //登录配置
        .formLogin()
                //登录失败处理
                .failureHandler(loginFailureHandler)
                //登录成功处理
                .successHandler(loginSuccessHandler)
                //禁用session
                .and ()
                //退出登录
                .logout ()
                .logoutSuccessHandler ( jwtLogoutSuccessHandler )
        .and()
                .sessionManagement()
                //配置session生成规则   不生成session
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS)
                //配置拦截规则
        .and()
                .authorizeRequests()
                //与白名单匹配
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()
                //异常处理器
        .and ()
                .exceptionHandling ()
                .authenticationEntryPoint ( jwtAuthenticationEntryPoint )
                .accessDeniedHandler ( jwtAccessDeniedHandler )
                //配置自定义的过滤器
        .and()
                //验证码过滤器需要在用户名密码过滤器之前
                .addFilter ( jwtAuthenticationFilter () )
        .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure( AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService ( userDetailService );
    }
}
