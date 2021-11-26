package com.markerhub.sys.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhang Bowen
 * @date 2021-10-23 20:47
 */
@Data
public class BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;

    private Integer state;


}
