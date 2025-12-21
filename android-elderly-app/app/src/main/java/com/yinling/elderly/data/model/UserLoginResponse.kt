package com.yinling.elderly.data.model

/**
 * 用户登录响应
 */
data class UserLoginResponse(
    val code: Int,
    val message: String,
    val data: LoginData?
)

data class LoginData(
    val userId: Long,
    val phone: String,
    val nickname: String,
    val avatar: String?,
    val userType: Int,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
