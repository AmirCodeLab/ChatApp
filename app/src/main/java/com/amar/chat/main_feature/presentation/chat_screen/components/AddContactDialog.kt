package com.amar.chat.main_feature.presentation.chat_screen.components

import android.content.Context
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.amar.chat.utils.Utils

@Composable
@Preview(showBackground = true)
fun AddContactDialog(
    context: Context = LocalContext.current,
    onAdd: (String) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var phone by remember { mutableStateOf("+92") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add contact") },
        text = {
            OutlinedTextField(
                value = phone,
                singleLine = true,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (phone.length != 13) {
                        Utils.showToast(context, "Invalid phone number")
                        return@TextButton
                    }
                    onAdd(phone)
                }
            ) {
                Text("Add to chat")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}