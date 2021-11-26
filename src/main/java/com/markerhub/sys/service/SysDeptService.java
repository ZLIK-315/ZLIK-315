package com.markerhub.sys.service;

import com.markerhub.sys.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-11-24
 */
public interface SysDeptService extends IService<SysDept> {
    List< SysDept> listDeptsByUserId ( Long id );
}
