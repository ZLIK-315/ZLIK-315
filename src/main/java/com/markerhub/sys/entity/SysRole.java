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
 * 职位表
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 职位名称
     */
    @NotBlank (message = "职位名称不能为空")
    private String name;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 职位编码
     */
    @NotBlank (message = "职位编码不能为空")
    private String code;

    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<> (  );

}
