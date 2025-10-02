package com.amar.chat.main_feature.presentation.profile_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.ui.theme.DarkOrange

@Composable
@Preview(showBackground = true)
private fun PreviewProfileTextField() {
    ProfileTextField(
        label = "Name",
        text = "amr",
        onTextChange = {}
    )
}

@Composable
fun ProfileTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        shape = RoundedCornerShape(10.dp),
        onValueChange = {
            onTextChange(it)
        },
        label = {
            Text(text = label)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Transparent,
            focusedContainerColor = Transparent,
            focusedIndicatorColor = DarkOrange
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}