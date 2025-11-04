package com.amar.chat.main_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amar.chat.main_feature.presentation._navigation.Routes
import com.amar.chat.main_feature.presentation.calls_screen.CallScreen
import com.amar.chat.main_feature.presentation.chat_screen.ChatScreen
import com.amar.chat.main_feature.presentation.home_screen.HomeScreen
import com.amar.chat.main_feature.presentation.otp_verify_screen.OtpVerificationScreen
import com.amar.chat.main_feature.presentation.profile_screen.ProfileSetScreen
import com.amar.chat.main_feature.presentation.register_screen.RegisterScreen
import com.amar.chat.main_feature.presentation.splash_screen.SplashScreen
import com.amar.chat.main_feature.presentation.update_screen.UpdateScreen
import com.amar.chat.ui.theme.ChatAppFireBaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppFireBaseTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        startDestination = Routes.SplashScreen.route,
                        navController = navController,
                    ) {

                        composable(route = Routes.SplashScreen.route) {
                            SplashScreen(navController)
                        }

                        composable(route = Routes.RegisterScreen.route) {
                            RegisterScreen { verificationId ->
                                navController.navigate("${Routes.OtpVerificationScreen.route}/${verificationId}")
                            }
                        }

                        composable(
                            route = "${Routes.OtpVerificationScreen.route}/{verificationId}",
                            arguments = listOf(navArgument("verificationId") { type = NavType.StringType })
                        ) {
                            val id = it.arguments?.getString("verificationId") ?: ""
                            OtpVerificationScreen(verificationId = id) {
                                navController.navigate(Routes.ProfileSetScreen.route)
                            }
                        }

                        composable(route = Routes.ProfileSetScreen.route) {
                            ProfileSetScreen {
                                navController.navigate(Routes.HomeScreen.route)
                            }
                        }

                        composable(route = Routes.HomeScreen.route) {
                            BackHandler {
                                finish()
                            }
                            HomeScreen { chatId, otherUserId ->
                                navController.navigate("${Routes.ChatScreen.route}/${chatId}/${otherUserId}")
                            }
                        }

                        composable(
                            route = "${Routes.ChatScreen.route}/{chatId}/{otherUserId}",
                            arguments = listOf(
                                navArgument("chatId") { type = NavType.StringType },
                                navArgument("otherUserId") { type = NavType.StringType })
                        ) {
                            val chatId = it.arguments?.getString("chatId") ?: ""
                            val otherUserId = it.arguments?.getString("otherUserId") ?: ""
                            ChatScreen(chatId, otherUserId)
                        }

                        composable(route = Routes.UpdateScreen.route) {
                            UpdateScreen()
                        }

                        composable(route = Routes.CallsScreen.route) {
                            CallScreen()
                        }

                    }
                }
            }
        }
    }
}