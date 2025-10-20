package com.amar.chat.main_feature.presentation.register_screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amar.chat.R
import com.amar.chat.main_feature.presentation._states.PhoneAuthState
import com.amar.chat.main_feature.presentation._view_model.PhoneAuthViewModel
import com.amar.chat.main_feature.presentation.register_screen.components.RegisterButton
import com.amar.chat.main_feature.presentation.register_screen.components.RegisterField
import com.amar.chat.utils.Utils
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ContextCastToActivity")
@Composable
@Preview(showSystemUi = true)
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: PhoneAuthViewModel = koinViewModel(),
    onNavigate: (String) -> Unit = {}
) {

    val number = remember { mutableStateOf("+92") }
    val context = LocalContext.current as Activity

    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(92.dp),
            painter = painterResource(id = R.drawable.img_splash),
            contentDescription = "Splash Screen",
        )

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Register here",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Chat App needs your phone number to continue",
            fontSize = 14.sp,
        )

        Spacer(Modifier.height(58.dp))

        RegisterField(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth(),
            number = number.value,
            onValueChange = {
                if (it.length <= 13) number.value = it
            }
        )

        Spacer(Modifier.height(22.dp))

        RegisterButton(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth()
        ) {
            if (number.value.length < 13) {
                Utils.showToast(context, "Enter valid number")
                return@RegisterButton
            }
            viewModel.sendOtp(context, number.value)
        }

        Spacer(Modifier.height(22.dp))

        when (authState) {
            PhoneAuthState.Idle -> {}
            PhoneAuthState.Loading -> {
                CircularProgressIndicator()
            }

            is PhoneAuthState.Error -> {
                Text("Error: ${(authState as PhoneAuthState.Error).message}")
            }

            is PhoneAuthState.OtpSent -> {
                val verificationId = (authState as PhoneAuthState.OtpSent).verificationId
                onNavigate(verificationId)
            }
            else -> {}
        }

    }

}