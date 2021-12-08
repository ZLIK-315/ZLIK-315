package com.markerhub.sys.entity;

import com.markerhub.vo.BaseCity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDept extends BaseCity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    private String serial;

    /**
     * 部门名称
     */
    private String name;


}
