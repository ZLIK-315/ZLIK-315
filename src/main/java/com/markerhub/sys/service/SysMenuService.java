package com.markerhub.sys.service;

import com.markerhub.common.dto.SysMenuDto;
import com.markerhub.sys.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 获取当前用户导航栏信息
     * @return
     */
    List< SysMenuDto> getCurrentUserNav ();

    List<SysMenu> tree ();
}
