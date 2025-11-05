package com.amar.chat.main_feature.presentation.chat_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
private fun PreviewChatBottomInput() {
    ChatBottomInput()
}

@Composable
fun ChatBottomInput(
    modifier: Modifier = Modifier,
    inputValue: String = "",
    onValueChange: (String) -> Unit = {},
    onSendClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = inputValue,
            onValueChange = { onValueChange(it) },
            placeholder = { Text("Type a message...") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Button(
            shape = RoundedCornerShape(12.dp),
            onClick = { onSendClick.invoke() }
        ) {
            Text("Send")
        }
    }
}