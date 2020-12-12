package com.xzy.sbjt.controller;

import com.xzy.sbjt.common.annocation.CheckToken;
import com.xzy.sbjt.common.annocation.IgnoreToken;
import com.xzy.sbjt.common.utils.msg.Message;
import com.xzy.sbjt.entity.UserEntity;
import com.xzy.sbjt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xzy
 * @date 2020-12-12 16:36
 * 说明：
 */
@RestController
@RequestMapping("user/")
@Api(tags = "用户管理")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    @ApiOperation("用户登陆")
    @IgnoreToken
    public Message login(@RequestBody UserEntity loginUser) {
        return userService.login(loginUser);
    }

    @GetMapping("test_token")
    @ApiOperation("测试系统对token的校验")
    @CheckToken
    public Message testToken() {
        return Message.ok();
    }
}
