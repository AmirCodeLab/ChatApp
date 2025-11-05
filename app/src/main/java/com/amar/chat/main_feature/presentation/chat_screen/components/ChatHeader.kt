package com.amar.chat.main_feature.presentation.chat_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
private fun ChatHeaderPreview() {
    ChatHeader()
}

@Composable
fun ChatHeader(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "ChatApp",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )
    }
    
}