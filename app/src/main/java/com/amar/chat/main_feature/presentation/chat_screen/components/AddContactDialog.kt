package com.amar.chat.main_feature.presentation.chat_screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true)
fun AddContactDialog(
    onAdd: (String) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var phone by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add contact") },
        text = {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onAdd(phone) }) { Text("Add to chat") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}