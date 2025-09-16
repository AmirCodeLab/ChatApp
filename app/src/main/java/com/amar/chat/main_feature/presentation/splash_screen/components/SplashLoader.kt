package com.amar.chat.main_feature.presentation.splash_screen.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.model.KeyPath
import com.amar.chat.R
import com.amar.chat.ui.theme.DarkOrange

@Composable
fun LottieAnimationView(modifier: Modifier = Modifier) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_loader)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    val properties = rememberLottieDynamicProperties(
        LottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = DarkOrange.toArgb(),
            keyPath = KeyPath("**")
        )
    )


    LottieAnimation(
        composition = composition,
        progress = { progress },
        dynamicProperties = properties,
        modifier = modifier.size(120.dp)
    )

}