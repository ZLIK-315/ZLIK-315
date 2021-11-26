package com.markerhub.sys.service;

import com.markerhub.sys.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 职位表 服务类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
public interface SysRoleService extends IService<SysRole> {

    List< SysRole> getRoleName ( Long positionId );

    List< SysRole> listRolesByUserId ( Long id );
}
