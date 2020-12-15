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
    /**
     * JWT系统配置
     */
    private final JwtProperty jwtProperty;
    /**
     * JWT加密算法
     */
    private final Algorithm algorithm;
    /**
     * JWT解密器
     */
    private final JWTVerifier jwtVerifier;
    /**
     * JWT生成器
     */
    private final JWTCreator.Builder tokenBuilder = JWT.create();

    @Autowired
    public TokenUtils(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;

        // 必须使用相同的加密算法对token进行加密和解密，当前使用的是HMAC256加密算法。
        algorithm = Algorithm.HMAC256(jwtProperty.getSecret());
        jwtVerifier = JWT.require(algorithm).build();
    }

    /**
     * 生成token
     *
     * @param userEntity - 用户信息
     * @return - 基于用户信息生成的token
     */
    public String generateToken(UserEntity userEntity) {

        // 1.设置JWT包含的信息：签发者、接收者、过期时间等
        tokenBuilder.withAudience(userEntity.getId());

        // 2.创建一个JWT，并使用指定加密算法进行签名
        return tokenBuilder.sign(algorithm);
    }
}
