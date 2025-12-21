package com.yinling.elderly.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 主界面ViewModel
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    
    // 主界面逻辑
    fun onVoiceInteractionClick() {
        // 处理语音交互点击
    }
    
    fun onHealthManagementClick() {
        // 处理健康管理点击
    }
    
    fun onMessagingClick() {
        // 处理消息通讯点击
    }
    
    fun onFamilyBindingClick() {
        // 处理家庭绑定点击
    }
}
