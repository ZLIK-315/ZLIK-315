package com.markerhub.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.sys.entity.SysMenu;
import com.markerhub.sys.entity.SysRole;
import com.markerhub.sys.entity.SysUser;
import com.markerhub.sys.mapper.SysUserMapper;
import com.markerhub.sys.service.SysMenuService;
import com.markerhub.sys.service.SysRoleService;
import com.markerhub.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public SysUser getByUsername ( String username ) {
        return getOne ( new QueryWrapper<SysUser> (  ).eq ( "username" ,username) );
    }

    @Override
    public String getUserAuthorityInfo (Long id) {
        SysUser sysUser = sysUserMapper.selectById ( id );

        String authority = "";
        //判断是否有权限缓存  有就从缓存中获取权限  没有就从数据库中获取
        if ( redisUtil.hasKey ( "GrantedAuthority" +sysUser.getUsername ()) ) {
            authority  = (String) redisUtil.get ( "GrantedAuthority" + sysUser.getUsername () );
        }else {
            //获取角色
            List< SysRole > roles = sysRoleService.list ( new QueryWrapper< SysRole > ()
                    .inSql ( "id", "select role_id from sys_user_role where user_id =" + id ) );
            if ( roles.size () > 0 ) {
                String roleCodes = roles.stream ().map ( r -> "ROLE_" + r.getCode () ).collect ( Collectors.joining ( "," ) );
                authority = roleCodes.concat ( "," );
            }
            //获取菜单编码
            List<Long> menuIds = sysUserMapper.getNavMenuIds(id);
            if (menuIds.size () > 0) {
                List< SysMenu > menus = sysMenuService.listByIds ( menuIds );
                String perms = menus.stream ().map ( m -> m.getPerms () ).collect ( Collectors.joining ( "," ) );
                authority = authority.concat ( perms );
            }
            //给用户权限添加缓存   时间秒为单位   设置一个小时
            redisUtil.set ( "GrantedAuthority" + sysUser.getUsername (), authority, 60*60);

        }
        return authority;
    }

    @Override
    public void clearUserAuthorityInfo ( String username ) {
        redisUtil.del ( "GrantedAuthority" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId ( Long roleId ) {
       List< SysUser > sysUsers = this.list ( new QueryWrapper<SysUser> (  )
               .inSql ( "id" ,"select user_id from sys_user_role where role_id =" + roleId) );
       sysUsers.forEach ( u -> {
           this.clearUserAuthorityInfo ( u.getUsername () );
       } );
    }

    @Override
    public void clearUserAuthorityInfoByMenuId ( Long menuId ) {
        List< SysUser > sysUsers = sysUserMapper.listByMenuId ( menuId );
        sysUsers.forEach ( u -> {
            this.clearUserAuthorityInfo ( u.getUsername () );
        } );
    }
}
