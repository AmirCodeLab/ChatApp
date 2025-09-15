package com.amar.chat.main_feature.presentation.register_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amar.chat.ui.theme.DarkOrange

@Composable
@Preview(showBackground = true)
private fun PreviewRegisterButton() {
    RegisterButton { }
}

@Composable
fun RegisterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkOrange,
            contentColor = Color.White
        ),
        onClick = { onClick() }
    ) {
        Text("Register", fontSize = 16.sp)
    }
}