package com.xzy.sbjt.common.config;

import com.xzy.sbjt.common.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author xzy
 * @date 2020-12-12 16:30
 * 说明：注册拦截器
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    private final AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public InterceptorConfig(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(authenticationInterceptor)
                // 拦截的路径
                .addPathPatterns("/**")
                // 放行的路径
                .excludePathPatterns("/user/login");
        
        super.addInterceptors(registry);
    }
}
