package com.markerhub.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import com.markerhub.sys.entity.vo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单ID
     */
    @NotNull( message = "上级菜单不能为空")
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank( message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单地址
     */
    private String path;

    /**
     * 授权(sys:user:list)
     */
    @NotBlank( message = "菜单授权码不能为空")
    private String perms;

    private String component;

    /**
     * 类型 0:目录 1:菜单 2:按钮
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    @TableField("orderNum")
    private Integer orderNum;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<> (  );

}
