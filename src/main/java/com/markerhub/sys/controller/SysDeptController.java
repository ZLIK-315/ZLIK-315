package com.markerhub.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.sys.entity.SysDept;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-11-24
 */
@RestController
@RequestMapping ( "/sys/dept" )
public class SysDeptController extends BaseController {

    @GetMapping("/listGroupByName")
    public Result listGroupByName(){
        List< SysDept > sysDept = sysDeptService.list ( new QueryWrapper< SysDept > ().groupBy ( "name" ) );
        return Result.success ( sysDept );
    }

    @GetMapping ( "/list" )
    @PreAuthorize ( "hasAuthority('sys:dept:list')" )
    public Result list ( String name, String area, String province, String city ) {
        Page< SysDept > pageData = sysDeptService.page ( getPage (), new QueryWrapper< SysDept > ()
                .like ( StrUtil.isNotBlank ( name ), "name", name )
                .like ( StrUtil.isNotBlank ( province ), "province", province )
                .like ( StrUtil.isNotBlank ( city ), "city", city )
                .like ( StrUtil.isNotBlank ( area ), "area", area )
        );
        return Result.success ( pageData );
    }

    @PostMapping ( "/save" )
    @PreAuthorize ( "hasAuthority('sys:dept:save')" )
    public Result save ( @Validated @RequestBody SysDept sysDept ) {
        System.out.println ( sysDept );
        sysDept.setCreated ( LocalDateTime.now () );
        sysDeptService.save ( sysDept );
        return Result.success ( sysDept );
    }

    @PostMapping ( "/update" )
    @PreAuthorize ( "hasAuthority('sys:dept:update')" )
    public Result update ( @Validated @RequestBody SysDept sysDept ) {
        sysDept.setUpdated ( LocalDateTime.now () );
        sysDeptService.updateById ( sysDept );
        return Result.success ( sysDept );
    }

    @GetMapping ( "/info/{id}" )
    @PreAuthorize ( "hasAuthority('sys:dept:list')" )
    public Result info ( @PathVariable ( "id" ) Long id ) {
        return Result.success ( sysDeptService.getById ( id ) );
    }

    @Transactional
    @DeleteMapping ( "/delete/{ids}" )
    public Result delete ( @PathVariable ( "ids" ) Long[] ids ) {
        sysDeptService.removeByIds ( Arrays.asList ( ids ) );
        return Result.success ( "" );
    }
}

