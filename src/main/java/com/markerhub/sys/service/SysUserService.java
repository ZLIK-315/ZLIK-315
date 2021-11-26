package com.markerhub.sys.service;

import com.markerhub.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 获取用户名
     * @param username
     * @return
     */
    SysUser getByUsername ( String username );

    /**
     * 获取用户权限
     * @param id
     * @return
     */
    String getUserAuthorityInfo (Long id);

    /**
     * 当username发生改变的时候要清除缓存
     * @param username
     */
    void clearUserAuthorityInfo(String username);

    /**
     * 当role发生改变的时候要清除缓存
     * @param roleId
     */
    void clearUserAuthorityInfoByRoleId(Long roleId);
    /**
     * 当menu发生改变的时候要清除缓存
     * @param menuId
     */
    void clearUserAuthorityInfoByMenuId(Long menuId);



}
