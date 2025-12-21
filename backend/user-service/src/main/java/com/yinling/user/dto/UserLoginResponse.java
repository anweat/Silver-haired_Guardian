package com.yinling.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {

    private Long userId;

    private String phone;

    private String nickname;

    private String avatar;

    private Integer userType;

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;  // Token过期时间（秒）
}
