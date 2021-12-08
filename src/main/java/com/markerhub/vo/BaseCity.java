package com.markerhub.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhang Bowen
 * @date 2021-11-28 22:55
 */
@Data
public class BaseCity implements Serializable {
    @TableId (value = "id", type = IdType.AUTO)
    private Long id;
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime created;
    @JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updated;
    private Integer state;
    private String province;
    private String city;
    private String area;
}
