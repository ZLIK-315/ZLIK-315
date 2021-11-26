package com.markerhub.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.sys.entity.SysDept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-11-24
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController{

    @GetMapping("/list")
    public Result list(String name) {
        Page<SysDept> pageData = sysDeptService.page ( getPage (), new QueryWrapper< SysDept > ()
                .like ( StrUtil.isNotBlank ( name ), "name", name ) );
        return Result.success ( pageData );
    }
}

