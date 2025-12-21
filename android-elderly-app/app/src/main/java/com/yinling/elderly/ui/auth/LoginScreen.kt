package com.yinling.elderly.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yinling.elderly.viewmodel.AuthViewModel

/**
 * 登录屏幕
 */
@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 标题
            Text(
                text = "银龄守候",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            // 手机号输入框
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("手机号", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                textStyle = androidx.compose.material3.LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            // 密码输入框
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("密码", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = androidx.compose.material3.LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            // 错误消息
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            // 登录按钮
            Button(
                onClick = {
                    isLoading = true
                    viewModel.login(phone, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = !isLoading && phone.isNotEmpty() && password.isNotEmpty()
            ) {
                Text(
                    text = if (isLoading) "登录中..." else "登录",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 注册链接
            Text(
                text = "没有账号？去注册",
                color = Color(0xFF4CAF50),
                fontSize = 16.sp,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}

@Composable
fun clickable(onClick: () -> Unit): Modifier {
    return Modifier.clickable(onClick = onClick)
}
