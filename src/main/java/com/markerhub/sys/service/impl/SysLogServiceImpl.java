package com.markerhub.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.sys.mapper.SysLogMapper;
import com.markerhub.sys.entity.SysLog;
import com.markerhub.sys.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * @author zhang Bowen
 * @date 2021-11-30 11:43
 */
@Service
public class SysLogServiceImpl  extends ServiceImpl< SysLogMapper, SysLog > implements SysLogService {
}
