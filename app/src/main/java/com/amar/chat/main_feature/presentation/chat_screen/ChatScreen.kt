package com.amar.chat.main_feature.presentation.chat_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(showSystemUi = true)
private fun PreviewChatScreen() {
    ChatScreen(phoneNumber = "12345")
}

@Composable
fun ChatScreen(
    phoneNumber: String
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "Chat Screen $phoneNumber")
        }
    }
}