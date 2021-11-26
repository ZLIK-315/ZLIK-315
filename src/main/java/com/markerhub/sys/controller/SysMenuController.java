package com.markerhub.sys.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.SysMenuDto;
import com.markerhub.common.lang.Result;
import com.markerhub.sys.entity.SysMenu;
import com.markerhub.sys.entity.SysRoleMenu;
import com.markerhub.sys.entity.SysUser;
import com.markerhub.sys.mapper.SysMenuMapper;
import com.markerhub.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController{


    @GetMapping("/nav")
    public Result nav( Principal principal ) {
        SysUser sysUser = sysUserService.getByUsername ( principal.getName () );

        //获取权限信息
        // ROLE_admin,sys:user:list....
        String authorityInfo = sysUserService.getUserAuthorityInfo ( sysUser.getId () );
        //把 ，隔开的权限信息转换为数组
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray ( authorityInfo, "," );
        //获取导航栏信息
        List< SysMenuDto > navs = sysMenuService.getCurrentUserNav();


        return Result.success( MapUtil.builder ()
        .put ( "authoritys", authorityInfoArray )
        .put ( "nav" , navs)
        .map ());
    }
    @GetMapping("/fuzzy/{name}")
    @PreAuthorize ( "hasAuthority('sys:menu:fuzzy')" )
    public Result nameInfo(@PathVariable("name") String name) {
        List< SysMenu > menus = sysMenuService.list ( new QueryWrapper< SysMenu > ().like ( "name", name ) );
        return Result.success ( menus );
    }
    @GetMapping("/info/{id}")
    @PreAuthorize ( "hasAuthority('sys:menu:list')" )
    public Result info(@PathVariable Long id ) {
        return Result.success ( sysMenuService.getById ( id ) );
    }

    @GetMapping("/list")
    @PreAuthorize ( "hasAuthority('sys:menu:list')" )
    public Result list() {
        List< SysMenu > menus =  sysMenuService.tree();
        return Result.success ( menus );
    }

    @PostMapping("/save")
    @PreAuthorize ( "hasAuthority('sys:menu:save')" )
    public Result save(@Validated @RequestBody SysMenu sysMenu){
        sysMenu.setCreated ( LocalDateTime.now () );
        sysMenuService.save ( sysMenu );
        return Result.success ( sysMenu );
    }

    @PostMapping("/update")
    @PreAuthorize ( "hasAuthority('sys:menu:update')" )
    public Result update(@Validated @RequestBody SysMenu sysMenu){
        sysMenu.setUpdated ( LocalDateTime.now () );
        sysMenuService.updateById (sysMenu);
        //清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId ( sysMenu.getId () );
        return Result.success ( sysMenu );
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize ( "hasAuthority('sys:menu:delete')" )
    public Result delete(@PathVariable("id") Long id){
        int count = sysMenuService.count ( new QueryWrapper< SysMenu > ().eq ( "parent_id", id ) );
        if ( count > 0 ) {
            return Result.fail ( "请先删除子菜单" );
        }
        sysUserService.clearUserAuthorityInfoByMenuId ( id );

        sysMenuService.removeById ( id );
        //同步删除关联中间表
        sysRoleMenuService.remove ( new QueryWrapper< SysRoleMenu > (  ).eq ( "menu_id" , id) );
        return Result.success ( "" );
    }
}

