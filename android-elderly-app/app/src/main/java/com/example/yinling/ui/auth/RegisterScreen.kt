package com.example.yinling.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yinling.ui.theme.YinlingTheme

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit = {},
) {
    var phoneNumber by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var agreeToTerms by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .align(androidx.compose.ui.Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large,
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Title
            Text(
                text = "创建账户",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally),
            )
            Text(
                text = "加入银龄守候大家庭",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Phone field
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("手机号") },
                placeholder = { Text("请输入11位手机号") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                enabled = !isLoading,
                textStyle = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nickname field
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("昵称") },
                placeholder = { Text("请输入昵称") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                enabled = !isLoading,
                textStyle = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("密码") },
                placeholder = { Text("至少6位字符") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) {
                                androidx.compose.material.icons.Icons.Default.Visibility
                            } else {
                                androidx.compose.material.icons.Icons.Default.VisibilityOff
                            },
                            contentDescription = null,
                        )
                    }
                },
                enabled = !isLoading,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm password field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("确认密码") },
                placeholder = { Text("再次输入密码") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                        Icon(
                            imageVector = if (showConfirmPassword) {
                                androidx.compose.material.icons.Icons.Default.Visibility
                            } else {
                                androidx.compose.material.icons.Icons.Default.VisibilityOff
                            },
                            contentDescription = null,
                        )
                    }
                },
                enabled = !isLoading,
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Terms checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(androidx.compose.ui.Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Start,
            ) {
                Checkbox(
                    checked = agreeToTerms,
                    onCheckedChange = { agreeToTerms = it },
                    enabled = !isLoading,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "我同意服务条款和隐私政策",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(androidx.compose.ui.Alignment.CenterVertically),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Error message
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                )
            }

            // Register button
            Button(
                onClick = {
                    isLoading = true
                    // TODO: 实现注册逻辑
                    isLoading = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading,
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                } else {
                    Text("注 册", style = MaterialTheme.typography.labelLarge)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(androidx.compose.ui.Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "已有账号？", style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = { /* Navigate to login */ }) {
                    Text(text = "立即登录", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
