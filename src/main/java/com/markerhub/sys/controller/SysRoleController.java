package com.markerhub.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Const;
import com.markerhub.common.lang.Result;
import com.markerhub.log.SysLogConfig;
import com.markerhub.sys.entity.SysDept;
import com.markerhub.sys.entity.SysRole;
import com.markerhub.sys.entity.SysRoleMenu;
import com.markerhub.sys.entity.SysUserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@RestController
@RequestMapping("/sys/roles")
public class SysRoleController extends BaseController{

    @GetMapping("/info/{id}")
    @PreAuthorize ( "hasAuthority('sys:role:list')" )
    public Result info(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById ( id );
        //获取角色相关联的菜单id
        List< SysRoleMenu > roleMenus = sysRoleMenuService.list ( new QueryWrapper< SysRoleMenu > ().eq ( "role_id", id )
        );
        List< Long > menuIds = roleMenus.stream ().map ( p -> p.getMenuId () ).collect ( Collectors.toList () );

        sysRole.setMenuIds ( menuIds );
        return Result.success ( sysRole );
    }
    @SysLogConfig ("查询职位信息")
    @GetMapping("/list")
    @PreAuthorize ( "hasAuthority('sys:role:list')" )
    public Result list(String name) {
        Page<SysRole> pageData = sysRoleService.page ( getPage (),
                new QueryWrapper< SysRole > ()
                        .like ( StrUtil.isNotBlank ( name ), "name", name ) );
        pageData.getRecords ().forEach ( r -> {
            List< SysDept > sysDepts = sysDeptService.listDeptsByUserId ( r.getDeptId () );
            r.setSysDepts ( sysDepts );
        } );
        return Result.success ( pageData );
    }
    @SysLogConfig("添加职位信息")
    @PostMapping("/save")
    @PreAuthorize ( "hasAuthority('sys:role:save')" )
    public Result save(@Validated @RequestBody SysRole sysRole ) {
        sysRole.setCreated ( LocalDateTime.now () );
        sysRole.setState ( Const.STATE_ON );
        sysRoleService.save ( sysRole );
        return Result.success ( sysRole );
    }
    @SysLogConfig("修改职位信息")
    @PostMapping("/update")
    @PreAuthorize ( "hasAuthority('sys:role:update')" )
    public Result update(@Validated @RequestBody SysRole sysRole) {
        sysRole.setUpdated ( LocalDateTime.now () );
        sysRoleService.updateById ( sysRole );
        //更新缓存
        sysUserService.clearUserAuthorityInfoByRoleId ( sysRole.getId () );
        return Result.success ( sysRole );
    }
    @SysLogConfig("删除职位信息")
    @DeleteMapping("/delete/{ids}")
    @PreAuthorize ( "hasAuthority('sys:role:delete')" )
    //添加事务
    @Transactional
    public Result delete(@PathVariable("ids") Long[] ids) {
        sysRoleService.removeByIds ( Arrays.asList (ids) );

        //删除中间表数据
        sysUserRoleService.remove ( new QueryWrapper< SysUserRole > (  ).in ( "role_id", ids ) );
        sysRoleMenuService.remove ( new QueryWrapper< SysRoleMenu > (  ).in ( "role_id", ids ) );

        //缓存同步删除
        Arrays.stream ( ids ).forEach ( id -> {
            //更新缓存
            sysUserService.clearUserAuthorityInfoByRoleId ( id );
        } );

        return Result.success ( "" );
    }
    @SysLogConfig("分配职位信息")
    @PostMapping("/perm/{roleId}")
    @Transactional
    @PreAuthorize ( "hasAuthority('sys:role:perm')" )
    public Result info(@PathVariable("roleId") Long roleId,@RequestBody Long[] menuIds) {

        List<SysRoleMenu> sysRoleMenus = new ArrayList<> (  );

        Arrays.stream ( menuIds ).forEach ( menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu ();
            sysRoleMenu.setMenuId ( menuId );
            sysRoleMenu.setRoleId ( roleId );
            sysRoleMenus.add ( sysRoleMenu );
        } );

        System.out.println ("menuIds" + menuIds);

        //先删除原来的信息再保存修改后的信息
        sysRoleMenuService.remove ( new QueryWrapper<SysRoleMenu> (  ).eq ( "role_id" ,roleId) );
        sysRoleMenuService.saveBatch ( sysRoleMenus );

        sysUserService.clearUserAuthorityInfoByRoleId ( roleId );
        return Result.success ( menuIds );
    }
}

