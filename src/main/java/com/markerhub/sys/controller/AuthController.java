package com.markerhub.sys.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.markerhub.common.lang.Const;
import com.markerhub.common.lang.Result;
import com.markerhub.sys.entity.SysUser;
import com.markerhub.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

/**
 * @author zhang Bowen
 * @date 2021-10-25 12:12
 * 随机生成64位验证码
 */
@RestController
public class AuthController extends BaseController{
    @Autowired
    Producer getDefaultKaptcha;
    @Autowired
    RedisUtil redisUtil;
    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = getDefaultKaptcha.createText();
        //为了测试
        key="aaaa";
        code="1111";
        BufferedImage image = getDefaultKaptcha.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

        String str = "data:image/jpeg;base64,";

        String base64Img = str + Base64Encoder.encode(outputStream.toByteArray());
        redisUtil.hset(Const.CAPTCHA_KEY,key,code, 120L );
        System.out.println ("key"+key+"  "+"code"+code);
        return Result.success(
                MapUtil.builder()
                .put("token",key)
                .put("captchaImg",base64Img)
                .build()
        );
    }

    /**
     * 获取用户信息接口
     * @param principal
     * @return
     */
    @GetMapping("/sys/userInfo")
    public Result userInfo( Principal principal ){
        SysUser sysUser = sysUserService.getByUsername ( principal.getName () );

        return Result.success ( MapUtil.builder ()
        .put ( "id", sysUser.getId () )
        .put ( "username" , sysUser.getUsername ())
        .put ( "avatar" , sysUser.getAvatar () )
        .put ( "create" , sysUser.getCreated () )
        .map ());
    }

}
