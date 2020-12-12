package com.xzy.sbjt.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xzy
 * @date 2020-12-12 15:57
 * 说明：Jwt相关的配置参数
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
    /**
     * 用于加密、解密token的密钥
     */
    private String secret;

    private String requestHeaderKey;
}
