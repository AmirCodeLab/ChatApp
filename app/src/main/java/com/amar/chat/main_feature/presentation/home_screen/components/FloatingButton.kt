package com.amar.chat.main_feature.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.R
import com.amar.chat.ui.theme.DarkOrange

@Preview
@Composable
private fun PreviewHomeFloatingButton() {
    HomeFloatingButton()
}

@Composable
fun HomeFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = modifier.size(54.dp),
        containerColor = DarkOrange,
        contentColor = Color.White,
        onClick = { onClick }
    ) {
        Image(
            modifier = Modifier.size(26.dp),
            painter = painterResource(R.drawable.img_add_chat),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}