package com.markerhub.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.markerhub.sys.entity.vo.BaseEntity;
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
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    private String serial;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 所属地区
     */
    private String area;


}
