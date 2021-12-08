package com.markerhub.log;

import java.lang.annotation.*;

/**
 * @author zhang Bowen
 * @date 2021-11-30 10:46
 */

@Target ( ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention ( RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface SysLogConfig {
    String value() default "";
}
