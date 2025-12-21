package com.yinling.elderly.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 认证ViewModel - 处理登录和注册
 */
@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    fun login(phone: String, password: String) {
        // 调用登录API
    }

    fun register(phone: String, nickname: String, password: String) {
        // 调用注册API
    }

    fun logout() {
        // 登出处理
    }

    fun refreshToken() {
        // 刷新Token
    }
}
