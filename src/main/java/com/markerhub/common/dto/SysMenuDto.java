package com.markerhub.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang Bowen
 * @date 2021-11-14 10:36
 * 由于前端数据和数据库中的字段不对应，所以添加dto转化
 */
@Data
public class SysMenuDto implements Serializable {
    /**
     *         title: '系统管理',
     *         name: 'SysManage ',
     *         icon: 'el-icon-menu',
     *         path: '',
     *         component:'',
     *         children:[]
     */
    private Long id;
    private String title;
    private String name;
    private String icon;
    private String path;
    private String component;
    private List<SysMenuDto> children= new ArrayList<>();
}
