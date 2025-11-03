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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.ui.theme.DarkOrange

@Composable
@Preview(showBackground = true)
private fun PreviewRegisterField() {
    RegisterField(
        label = "Input Field",
        inputValue = "Enter Value",
        imageVector = Icons.Default.Phone,
        keyboardType = KeyboardType.Phone,
    )
}

@Composable
fun RegisterField(
    modifier: Modifier = Modifier,
    label: String,
    inputValue: String,
    imageVector: ImageVector,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        modifier = modifier,
        value = inputValue,
        shape = RoundedCornerShape(10.dp),
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Icon(
                imageVector = imageVector, contentDescription = label
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