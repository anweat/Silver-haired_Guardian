package com.yinling.user.service;

import com.yinling.user.dto.UserLoginRequest;
import com.yinling.user.dto.UserLoginResponse;
import com.yinling.user.dto.UserRegisterRequest;
import com.yinling.user.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     */
    User register(UserRegisterRequest request);

    /**
     * 用户登录
     */
    UserLoginResponse login(UserLoginRequest request);

    /**
     * 根据ID查询用户
     */
    User getUserById(Long userId);

    /**
     * 根据手机号查询用户
     */
    User getUserByPhone(String phone);

    /**
     * 验证密码
     */
    boolean verifyPassword(String password, String encodedPassword);

    /**
     * 生成JWT Token
     */
    String generateToken(Long userId);

    /**
     * 生成刷新Token
     */
    String generateRefreshToken(Long userId);

    /**
     * 验证Token
     */
    Long verifyToken(String token);
}
