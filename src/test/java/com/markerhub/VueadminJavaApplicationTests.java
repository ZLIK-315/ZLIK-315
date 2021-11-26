package com.markerhub;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.markerhub.sys.entity.SysDept;
import com.markerhub.sys.entity.SysUser;
import com.markerhub.sys.service.SysDeptService;
import com.markerhub.sys.service.SysMenuService;
import com.markerhub.sys.service.SysUserService;
import com.markerhub.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@SpringBootTest
class VueadminJavaApplicationTests {
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    SysDeptService sysDeptService;


    @Test
    public void test1(){
        System.out.println (sysMenuService.list ());
    }
    @Test
    public void test3(){
        System.out.println (sysUserService.getUserAuthorityInfo ( 1L ));
    }
    @Test
    public void test4(){
        System.out.println (sysUserService);
    }
    @Test
    @RequestMapping("/pass")
    public void test2(){
        //加密
        String encode = bCryptPasswordEncoder.encode ( "111111" );
        boolean matches = bCryptPasswordEncoder.matches ( encode, "111111" );
        System.out.println ("matches      " + matches);
        System.out.println (encode);
    }

    @Test
    public void test5(Integer deptId){
        deptId = 1;
        List< Object > one = sysDeptService.listObjs ( new QueryWrapper< SysDept > ().eq ( "id", deptId ) );
        System.out.println (one);
    }

}
