package com.yinling.elderly.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yinling.elderly.ui.theme.LargeButton
import com.yinling.elderly.viewmodel.MainViewModel

/**
 * 主界面 - 四个大按钮
 */
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToVoice: () -> Unit,
    onNavigateToHealth: () -> Unit,
    onNavigateToMessage: () -> Unit,
    onNavigateFamilyBinding: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 标题
            Text(
                text = "银龄守候",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // 语音交互按钮
            LargeButton(
                label = "🎤\n语音交互",
                onClick = onNavigateToVoice
            )

            // 健康管理按钮
            LargeButton(
                label = "💊\n健康管理",
                onClick = onNavigateToHealth
            )

            // 消息通讯按钮
            LargeButton(
                label = "💬\n消息通讯",
                onClick = onNavigateToMessage
            )

            // 家庭绑定按钮
            LargeButton(
                label = "👨‍👩‍👧\n家庭管理",
                onClick = onNavigateFamilyBinding
            )
        }
    }
}
