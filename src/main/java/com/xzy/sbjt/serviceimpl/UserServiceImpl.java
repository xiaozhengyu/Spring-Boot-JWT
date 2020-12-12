package com.xzy.sbjt.serviceimpl;

import com.xzy.sbjt.common.utils.jwt.TokenUtils;
import com.xzy.sbjt.common.utils.msg.Message;
import com.xzy.sbjt.common.utils.msg.MessageBox;
import com.xzy.sbjt.entity.UserEntity;
import com.xzy.sbjt.repository.UserRepository;
import com.xzy.sbjt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.xzy.sbjt.constant.UserConstant.NOT_DELETED;

/**
 * @author xzy
 * @date 2020-12-12 13:44
 * 说明：
 */
@Service
public class UserServiceImpl implements UserService {

    private final TokenUtils tokenUtils;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(TokenUtils tokenUtils, UserRepository userRepository) {
        this.tokenUtils = tokenUtils;
        this.userRepository = userRepository;
    }

    /**
     * 查询用户信息
     *
     * @param useId - 用户ID
     * @return - 用户信息
     */
    @Override
    public Optional<UserEntity> findById(String useId) {
        return userRepository.findByIdAndDeleted(useId, NOT_DELETED);
    }

    /**
     * 用户登陆
     *
     * @param loginUser - 账号密码
     * @return 如果登陆成功则返回token，否则返回相应的错误信息。
     */
    @Override
    public Message login(UserEntity loginUser) {
        Optional<UserEntity> userOptional = findById(loginUser.getId());
        if (!userOptional.isPresent()) {
            return Message.fail("登陆失败，账号不存在或密码输入错误");
        }

        UserEntity userEntity = userOptional.get();
        // 暂时不考虑密码的加密问题
        if (!userEntity.getPassword().equals(loginUser.getPassword())) {
            return Message.fail("登陆失败，账号不存在或密码输入错误");
        }

        return MessageBox.ok(tokenUtils.generateToken(loginUser));
    }
}
