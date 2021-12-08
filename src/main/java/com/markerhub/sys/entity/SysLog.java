package com.markerhub.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhang Bowen
 * @date 2021-11-30 10:40
 */
@Data
public class SysLog implements Serializable {
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 操作
     */
    private String operation;
    /**
     * 方法名
     */
    private String method;
    /**
     * 参数
     */
    private String params;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 创建时间
     * */
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime created;

}
