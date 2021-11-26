package com.markerhub.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.sys.entity.SysRole;
import com.markerhub.sys.mapper.SysRoleMapper;
import com.markerhub.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleService sysRoleService;
    @Override
    public List< SysRole > getRoleName ( Long positionId ) {
        return sysRoleService.list ( new QueryWrapper<SysRole>().eq ( "id",positionId ) );
    }

    @Override
    public List< SysRole > listRolesByUserId ( Long id ) {
        List<SysRole> sysRoles = this.list(new QueryWrapper<SysRole>()
                .inSql("id", "select role_id from sys_user_role where user_id = " + id));

        return sysRoles;
    }
}
