package com.xzy.sbjt.common.interceptor;

import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xzy.sbjt.common.annocation.CheckToken;
import com.xzy.sbjt.common.annocation.IgnoreToken;
import com.xzy.sbjt.common.utils.jwt.TokenUtils;
import com.xzy.sbjt.entity.UserEntity;
import com.xzy.sbjt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author xzy
 * @date 2020-12-12 14:16
 * 说明：验证token的拦截器
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final TokenUtils tokenUtils;
    private final UserService userService;

    @Autowired
    public AuthenticationInterceptor(TokenUtils tokenUtils, UserService userService) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 对于不是映射到方法的请求，直接予以通过。
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 对于存在IgnoreToken注解的方法，直接予以通过
        if (method.isAnnotationPresent(IgnoreToken.class)) {
            return true;
        }

        // 对于存在CheckToken注解的方法，进行token校验
        if (method.isAnnotationPresent(CheckToken.class)) {
            // 从请求头中取出token
            String token = request.getHeader(tokenUtils.getJwtProperty().getRequestHeaderKey());
            if (token == null) {
                throw new IllegalArgumentException("未传递token，请重新登陆");
            }

            // 对token进行校验、解码
            DecodedJWT decodedToken;
            try {
                // verify方法对token的有效性进行了详细的校验：如token是否失效、签名是否有效、加密算法是否正确等
                decodedToken = tokenUtils.getJwtVerifier().verify(token);
            } catch (JWTVerificationException e) {
                // verify方法针对不同的错误抛出了不同种类的异常，如果需要返回更准确的错误信息，可以对分类异常进行捕获。
                // （这些异常都是JWTVerificationException的子类）
                throw new IllegalArgumentException("无效的token，请重新登陆");
            }

            // 抽取token中包含的接收者信息
            List<String> audienceValueList = decodedToken.getAudience();
            if (audienceValueList == null) {
                throw new IllegalArgumentException("无效的token，请重新登陆");
            }

            // 校验用户信息
            String userId = audienceValueList.get(0);
            Optional<UserEntity> userOptional = userService.findById(userId);
            if (!userOptional.isPresent()) {
                throw new IllegalArgumentException("用户不存在，请重新登陆");
            }

            return true;
        }

        return false;
    }
}
