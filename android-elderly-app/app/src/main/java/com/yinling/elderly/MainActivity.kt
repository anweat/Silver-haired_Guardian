package com.yinling.elderly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.yinling.elderly.ui.AppNavigation
import com.yinling.elderly.ui.theme.YinlingTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * 主Activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YinlingTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
