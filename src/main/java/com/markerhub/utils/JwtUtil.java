package com.markerhub.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhang Bowen
 * @date 2021-10-26 14:11
 */
@Data
@Component
@ConfigurationProperties (prefix = "markerhub.jwt")
public class JwtUtil {

    private long expire;
    private String secret;
    private String header;

    //生成Jwt
    public String generateToken(String username) {
        Date nowDate = new Date ();
        Date expireDate = new Date ( nowDate.getTime () + 1000 * expire );
        return Jwts.builder ()
                //设置头部信息
                .setHeaderParam ( "typ" ,"JWT")
                //主体
                .setSubject ( username )
                //创建时间
                .setIssuedAt ( nowDate )
                //设置7天过期
                .setExpiration ( expireDate )
                //加入算法   密钥
                .signWith ( SignatureAlgorithm.HS512, secret)
                .compact ();

    }
    //解析Jwt
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts
                    //解析器
                    .parser ()
                    .setSigningKey ( secret )
                    .parseClaimsJws ( jwt )
                    .getBody ();
        }catch ( Exception e ){
            return null;
        }
    }
    //jwt是否过期
    public boolean isTokenExpired(Claims claims) {
        //这个过期时间是否在这个时间之前
        return claims.getExpiration ().before ( new Date ());
    }
}
