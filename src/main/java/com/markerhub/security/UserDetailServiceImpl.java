package com.markerhub.security;

import com.markerhub.sys.entity.SysUser;
import com.markerhub.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang Bowen
 * @date 2021-10-28 20:54
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    SysUserService sysUserService;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername ( username );
        if ( sysUser == null || sysUser.getState () == 0){
            throw new UsernameNotFoundException("用户名密码不正确");
        }

        return new AccountUser (sysUser.getId (),sysUser.getUsername (),sysUser.getPassword (),getUserAuthority(sysUser.getId ()));
    }

    /**
     * 获取用户权限信息（角色，菜单）
     * @param id
     * @return
     */
    public List< GrantedAuthority > getUserAuthority(Long id){
        // 角色（ROLE_admin) 、 菜单操作权限 sys:user:list
        // ROLE_admin,sys:user:list.....
        String authority = sysUserService.getUserAuthorityInfo (id);
        // 从逗号分隔的字符串创建一个GrantedAuthority对象数组
        // 把进行格式拆分   封装成List< GrantedAuthority >
        return AuthorityUtils.commaSeparatedStringToAuthorityList ( authority );
    }
}
