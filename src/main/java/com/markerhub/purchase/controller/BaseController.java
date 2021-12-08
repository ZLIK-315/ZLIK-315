package com.markerhub.purchase.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.purchase.service.TbVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhang Bowen
 * @date 2021-12-06 21:08
 */
public class BaseController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    TbVendorService tbVendorService;

    /**
     * 获取页码
     *
     * @return
     */
    public Page getPage () {
        int currentPage = ServletRequestUtils.getIntParameter ( req, "current", 1 );
        int pageSize = ServletRequestUtils.getIntParameter ( req, "size", 5 );

        return new Page ( currentPage, pageSize );
    }
}
