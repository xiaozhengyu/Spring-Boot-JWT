package com.xzy.sbjt.common.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.xzy.sbjt.entity.UserEntity;
import com.xzy.sbjt.property.JwtProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xzy
 * @date 2020-12-12 13:54
 * 说明：
 */
@Component
@Data
public class TokenUtils {
    private final JwtProperty jwtProperty;
    /**
     * 解密器
     */
    private final JWTVerifier jwtVerifier;
    /**
     * HMAC256加密算法
     */
    private final Algorithm HMAC256Algorithm;

    @Autowired
    public TokenUtils(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
        // 使用相同的密钥创建解码器以及加密算法
        jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperty.getSecret())).build();
        HMAC256Algorithm = Algorithm.HMAC256(jwtProperty.getSecret());
    }

    /**
     * 生成token
     *
     * @param userEntity - 用户信息
     * @return - 基于用户信息生成的token
     */
    public String generateToken(UserEntity userEntity) {
        // 1.获取JWT生成工具
        JWTCreator.Builder tokenBuilder = JWT.create();

        // 2.设置JWT包含的信息：签发者、接收者、过期时间等
        tokenBuilder.withAudience(userEntity.getId());

        // 3.使用指定算法对token进行签名，最终返回签名后的token。
        return tokenBuilder.sign(HMAC256Algorithm);
    }
}
