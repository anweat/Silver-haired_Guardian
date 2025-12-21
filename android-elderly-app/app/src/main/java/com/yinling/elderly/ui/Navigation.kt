package com.yinling.elderly.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yinling.elderly.ui.main.MainScreen
import com.yinling.elderly.ui.auth.LoginScreen
import com.yinling.elderly.ui.auth.RegisterScreen

/**
 * 应用导航
 */
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 登录屏幕
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // 注册屏幕
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // 主屏幕
        composable("main") {
            MainScreen(
                onNavigateToVoice = {
                    navController.navigate("voice")
                },
                onNavigateToHealth = {
                    navController.navigate("health")
                },
                onNavigateToMessage = {
                    navController.navigate("message")
                },
                onNavigateFamilyBinding = {
                    navController.navigate("family")
                }
            )
        }

        // 其他屏幕将在此处添加...
        composable("voice") {
            // VoiceScreen()
        }

        composable("health") {
            // HealthScreen()
        }

        composable("message") {
            // MessageScreen()
        }

        composable("family") {
            // FamilyScreen()
        }
    }
}
