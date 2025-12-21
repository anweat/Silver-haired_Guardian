package com.yinling.elderly.data.remote

import com.yinling.elderly.data.model.UserLoginResponse
import retrofit2.http.*

/**
 * API服务接口
 */
interface ApiService {

    /**
     * 用户登录
     */
    @POST("/auth/login")
    suspend fun login(@Body request: Map<String, String>): UserLoginResponse

    /**
     * 用户注册
     */
    @POST("/auth/register")
    suspend fun register(@Body request: Map<String, String>): Map<String, Any>

    /**
     * 获取用户信息
     */
    @GET("/user/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): Map<String, Any>

    /**
     * 获取健康数据
     */
    @GET("/health/data")
    suspend fun getHealthData(): Map<String, Any>

    /**
     * 获取消息列表
     */
    @GET("/message/list")
    suspend fun getMessageList(): Map<String, Any>
}
