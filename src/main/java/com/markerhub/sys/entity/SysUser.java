package com.markerhub.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.markerhub.vo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户头像
     */
    private String avatar;

    @TableField(exist = false)
    private List<SysDept> sysDepts = new ArrayList<> (  );
    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<> (  );

}
