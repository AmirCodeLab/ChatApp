package com.amar.chat.main_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amar.chat.main_feature.presentation._navigation.Routes
import com.amar.chat.main_feature.presentation.calls_screen.CallScreen
import com.amar.chat.main_feature.presentation.home_screen.HomeScreen
import com.amar.chat.main_feature.presentation.register_screen.RegisterScreen
import com.amar.chat.main_feature.presentation.splash_screen.SplashScreen
import com.amar.chat.main_feature.presentation.update_screen.UpdateScreen
import com.amar.chat.ui.theme.ChatAppFireBaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppFireBaseTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        startDestination = Routes.SplashScreen,
                        navController = navController,
                    ) {

                        composable<Routes.SplashScreen> {
                            SplashScreen(navController)
                        }

                        composable<Routes.RegisterScreen> {
                            RegisterScreen {
                                navController.navigate(Routes.HomeScreen) {
                                    popUpTo<Routes.RegisterScreen> { inclusive = true }
                                }
                            }
                        }

                        composable<Routes.HomeScreen> {
                            HomeScreen()
                        }

                        composable<Routes.UpdateScreen> {
                            UpdateScreen()
                        }

                        composable<Routes.CallsScreen> {
                            CallScreen()
                        }

                    }
                }
            }
        }
    }
}