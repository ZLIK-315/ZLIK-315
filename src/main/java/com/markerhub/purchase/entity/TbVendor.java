package com.markerhub.purchase.entity;

import java.io.Serializable;

import com.markerhub.vo.BaseCity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Bowen Zhang
 * @since 2021-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbVendor extends BaseCity {

    private static final long serialVersionUID = 1L;
    /**
     * 厂商名称
     */
    private String name;
    /**
     * 负责人
     */
    private String username;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 描述
     */
    private String description;

}
