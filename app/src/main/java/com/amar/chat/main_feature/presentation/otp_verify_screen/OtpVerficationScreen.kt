package com.amar.chat.main_feature.presentation.otp_verify_screen

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
import com.amar.chat.main_feature.presentation.otp_verify_screen.components.OtpVerifyButton
import com.amar.chat.main_feature.presentation.otp_verify_screen.components.OtpVerifyField
import com.amar.chat.utils.Utils
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview(showSystemUi = true)
fun OtpVerificationScreen(
    modifier: Modifier = Modifier,
    verificationId: String = "",
    viewModel: PhoneAuthViewModel = koinViewModel(),
    onNavigate: () -> Unit = {}
) {

    val otp = remember { mutableStateOf("") }
    val context = LocalContext.current

    val state by viewModel.authState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(92.dp),
            painter = painterResource(id = R.drawable.img_splash),
            contentDescription = "",
        )

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Verify Number",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Chat App needs to verify your phone number",
            fontSize = 14.sp,
        )

        Spacer(Modifier.height(58.dp))

        OtpVerifyField(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth(),
            number = otp.value,
            onValueChange = {
                if (it.length <= 6) otp.value = it
            }
        )

        Spacer(Modifier.height(22.dp))

        OtpVerifyButton (
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .fillMaxWidth()
        ) {
            if (otp.value.length < 6) {
                Utils.showToast(context, "Enter valid OTP")
                return@OtpVerifyButton
            }
            viewModel.verifyCode(otp.value, verificationId)
        }

        Spacer(Modifier.height(22.dp))

        when (state) {
            is PhoneAuthState.Loading -> CircularProgressIndicator()
            is PhoneAuthState.Success -> {
                val user = (state as PhoneAuthState.Success).user
                onNavigate.invoke()
            }
            is PhoneAuthState.Error -> {
                Text("Error: ${(state as PhoneAuthState.Error).message}")
            }
            else -> {}
        }
    }

}