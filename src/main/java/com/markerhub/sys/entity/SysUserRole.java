package com.markerhub.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole {

    private static final long serialVersionUID = 1L;
    @TableId (value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;

    private Long roleId;


}
