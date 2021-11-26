package com.markerhub.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.sys.entity.SysDept;
import com.markerhub.sys.entity.SysRole;
import com.markerhub.sys.mapper.SysDeptMapper;
import com.markerhub.sys.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-11-24
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public List< SysDept > listDeptsByUserId ( Long id ) {
        return list (new QueryWrapper< SysDept > (  ).eq ( "id",id ));
    }
}
