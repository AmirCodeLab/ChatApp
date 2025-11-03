package com.amar.chat.main_feature.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amar.chat.R
import com.amar.chat.main_feature.presentation._navigation.Routes
import com.amar.chat.main_feature.presentation.splash_screen.components.LottieAnimationView
import com.amar.chat.utils.SharedPref
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val sharedPref: SharedPref = koinInject()

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(
            if(sharedPref.isLogin) Routes.HomeScreen.route else Routes.RegisterScreen.route
        ) {
            popUpTo(route = Routes.SplashScreen.route) { inclusive = true }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(92.dp),
                painter = painterResource(id = R.drawable.img_splash),
                contentDescription = "Splash Screen",
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Chat with us",
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif
            )
        }

        LottieAnimationView(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 22.dp),
            text = "Amir work place",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

}