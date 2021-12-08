package com.markerhub.purchase.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.purchase.entity.TbVendor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-12-05
 */
@RestController
@RequestMapping ( "/purchase/vendor" )
public class TbVendorController extends BaseController {

    @GetMapping ( "/list" )
    public Result list ( String name, String username, String area, String province, String city ) {
        Page page = tbVendorService.page ( getPage (), new QueryWrapper< TbVendor > ()
                .like ( StrUtil.isNotBlank ( name ), "name", name )
                .like ( StrUtil.isNotBlank ( username ), "username", username )
                .like ( StrUtil.isNotBlank ( province ), "province", province )
                .like ( StrUtil.isNotBlank ( city ), "city", city )
                .like ( StrUtil.isNotBlank ( area ), "area", area )
        );
        return Result.success ( page );
    }

    @PostMapping ( "/save" )
    public Result save ( @Validated @RequestBody TbVendor tbVendor, Principal principal ) {
        tbVendor.setCreated ( LocalDateTime.now () );
        tbVendor.setCreator ( principal.getName () );
        tbVendorService.save ( tbVendor );
        return Result.success ( tbVendor );
    }

    @PostMapping ( "/update" )
    public Result update(@Validated @RequestBody TbVendor tbVendor) {
        tbVendor.setUpdated ( LocalDateTime.now() );
        tbVendorService.updateById ( tbVendor );
        return Result.success(tbVendor);
    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable ( "id" ) Long id) {
        return Result.success(tbVendorService.getById ( id ));
    }

}

