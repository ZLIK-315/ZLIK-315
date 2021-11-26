package com.markerhub.sys.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.SysMenuDto;
import com.markerhub.sys.entity.SysMenu;
import com.markerhub.sys.entity.SysUser;
import com.markerhub.sys.mapper.SysMenuMapper;
import com.markerhub.sys.mapper.SysUserMapper;
import com.markerhub.sys.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List< SysMenuDto > getCurrentUserNav () {
        //从security上下文中可以直接获取到当前的用户名
        String username = (String) SecurityContextHolder.getContext ().getAuthentication ().getPrincipal ();
        //使用当前用户名查询到用户信息
        SysUser sysUser = sysUserService.getByUsername ( username );
        //根据用户id可以查到当前用户的菜单id
        List< Long > menuIds = sysUserMapper.getNavMenuIds ( sysUser.getId () );
        //根据菜单id获取权限
        List< SysMenu > menus = this.listByIds ( menuIds );
        //转树状结构
        List< SysMenu > menuTree = buildTreeMenu(menus);
        //实体转dto
        return convert(menuTree);
    }

    @Override
    public List< SysMenu > tree () {
        List< SysMenu > menus = this.list ( new QueryWrapper< SysMenu > ().orderByAsc ( "orderNum" ) );
        return buildTreeMenu ( menus );
    }

    private List< SysMenuDto > convert ( List< SysMenu > menuTree ) {
        List<SysMenuDto> menuDtos = new ArrayList<> (  );

        menuTree.forEach ( m -> {
            SysMenuDto sysMenuDto = new SysMenuDto ();
            sysMenuDto.setId ( m.getId () );
            sysMenuDto.setName ( m.getPerms () );
            sysMenuDto.setTitle ( m.getName () );
            sysMenuDto.setComponent ( m.getComponent () );
            sysMenuDto.setPath ( m.getPath () );
            sysMenuDto.setIcon ( m.getIcon () );
            if ( m.getChildren ().size () > 0 ) {
                //递归查询所有子菜单
                sysMenuDto.setChildren ( convert ( m.getChildren () ) );
            }
            menuDtos.add ( sysMenuDto );
        } );
        return menuDtos;
    }

    private List< SysMenu > buildTreeMenu ( List< SysMenu > menus ) {
        List<SysMenu> finalMenus = new ArrayList<> (  );
        //先各自寻找到各自的子导航
        for (SysMenu menu : menus){
            for (SysMenu e : menus){
                if ( menu.getId ().equals ( e.getParentId () ) ){
                    menu.getChildren ().add ( e );
                }

            }
            //提取父节点
            if ( menu.getParentId () == 0L ) {
                finalMenus.add ( menu );
            }
        }
        System.out.println(JSONUtil.toJsonStr ( finalMenus ));

        return finalMenus;
    }
}
