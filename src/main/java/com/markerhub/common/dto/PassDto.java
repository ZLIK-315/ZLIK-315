package com.markerhub.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zhang Bowen
 * @date 2021-11-25 16:01
 */
@Data
public class PassDto implements Serializable {

    @NotBlank (message = "新密码不能为空")
    private String password;

    @NotBlank(message = "旧密码不能为空")
    private String currentPass;
}
