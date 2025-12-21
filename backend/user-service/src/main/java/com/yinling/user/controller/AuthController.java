package com.yinling.user.controller;

import com.yinling.common.result.Result;
import com.yinling.user.dto.UserLoginRequest;
import com.yinling.user.dto.UserLoginResponse;
import com.yinling.user.dto.UserRegisterRequest;
import com.yinling.user.entity.User;
import com.yinling.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户认证Controller
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody UserRegisterRequest request) {
        log.info("用户注册请求: {}", request.getPhone());
        User user = userService.register(request);
        return Result.success(user);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        log.info("用户登录请求: {}", request.getPhone());
        UserLoginResponse response = userService.login(request);
        return Result.success(response);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/user/{userId}")
    public Result<User> getUserInfo(@PathVariable Long userId) {
        log.info("获取用户信息: {}", userId);
        User user = userService.getUserById(userId);
        if (user == null) {
            return Result.notFound("用户不存在");
        }
        return Result.success(user);
    }

    /**
     * 验证Token
     */
    @PostMapping("/verify-token")
    public Result<Long> verifyToken(@RequestHeader("Authorization") String token) {
        log.info("验证Token");
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Long userId = userService.verifyToken(token);
        return Result.success(userId);
    }
}
