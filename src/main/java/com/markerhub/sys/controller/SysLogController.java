package com.markerhub.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.lang.Result;
import com.markerhub.sys.entity.SysLog;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author zhang Bowen
 * @date 2021-11-30 14:57
 */
@RestController
@RequestMapping ("/sys/log")
public class SysLogController extends BaseController{
    private Logger logger = Logger.getLogger( String.valueOf ( this.getClass() ) );

    @GetMapping("/list")
    public Result list( String username, @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime start,@DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end ){
        System.out.println (start+ " ++++++++++++++++++++ " +end);
        List< SysLog > sysLog = sysLogService.list ( new QueryWrapper< SysLog > ()
                .like ( StrUtil.isNotBlank ( username ), "username", username )
                .between ( start != null && end != null, "created", start, end )
        );
        return Result.success ( sysLog );
    }
    @DeleteMapping("/delete")
    public Result delete( @PathVariable("ids") Long[] ids ){
        sysLogService.removeByIds( Arrays.asList ( ids ) );
        return Result.success ( "" );
    }

    /**
     * @Scheduled (cron = "0 0 12 * * ?")
     * 每天中午12点触发
     * 秒 分 时 天 周
     * @return
     */
    @Scheduled (cron = "0 0 12 * * ?")
    public Result delete(){
        LocalDateTime now = LocalDateTime.now ();
        LocalDateTime minusDays = now.minusDays ( 1 );
        sysLogService.remove ( new QueryWrapper<SysLog> (  ).le ( "created", minusDays ) );
        System.out.println ("清空了");
        return Result.success ( "" );
    }
}
