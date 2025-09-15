package com.amar.chat.main_feature.presentation.register_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.ui.theme.DarkOrange

@Composable
@Preview(showBackground = true)
private fun PreviewRegisterField() {
    RegisterField(
        number = "1234", onValueChange = {}
    )
}

@Composable
fun RegisterField(
    modifier: Modifier = Modifier,
    number: String,
    onValueChange: (String) -> Unit,
    label: String = "Phone Number",
    keyboardType: KeyboardType = KeyboardType.Phone
) {
    OutlinedTextField(
        modifier = modifier,
        value = number,
        shape = RoundedCornerShape(10.dp),
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Phone, contentDescription = label
            )
        },
        label = {
            Text(text = label)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = DarkOrange
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}