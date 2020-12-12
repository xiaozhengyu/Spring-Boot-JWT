package com.xzy.sbjt.service;

import com.xzy.sbjt.common.utils.msg.Message;
import com.xzy.sbjt.entity.UserEntity;

import java.util.Optional;

/**
 * @author xzy
 * @date 2020-12-12 13:44
 * 说明：
 */
public interface UserService {
    /**
     * 查询用户信息
     *
     * @param useId - 用户ID
     * @return - 用户信息
     */
    Optional<UserEntity> findById(String useId);

    /**
     * 用户登陆
     *
     * @param loginUser - 账号密码
     * @return 如果登陆成功则返回token，否则返回相应的错误信息。
     */
    Message login(UserEntity loginUser);
}
