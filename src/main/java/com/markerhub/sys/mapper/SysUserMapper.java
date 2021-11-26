package com.markerhub.sys.mapper;

import com.markerhub.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取菜单id
     * @param id
     * @return
     */
    List<Long> getNavMenuIds( Long id);

    /**
     *
     * @param menuId
     * @return
     */
    List<SysUser> listByMenuId ( Long menuId );
}
