package com.markerhub.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.sys.mapper.SysUserRoleMapper;
import com.markerhub.sys.service.*;
import com.markerhub.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang Bowen
 * @date 2021-10-25 12:14
 */
public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    SysDeptService sysDeptService;

    /**
     * 获取页码
     * @return
     */
    public Page getPage() {
        int currentPage = ServletRequestUtils.getIntParameter ( req,"currentPage",1 );
        int pageSize = ServletRequestUtils.getIntParameter ( req,"pageSize",10 );

        return new Page(currentPage , pageSize);
    }

}
