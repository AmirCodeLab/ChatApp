package com.amar.chat.main_feature.presentation.common

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true)
private fun PreviewDropDownMenu() {
    DropDownMenu()
}

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    showMenu: Boolean = true,
    onDismiss: () -> Unit = {}
) {

    DropdownMenu(
        modifier = modifier,
        expanded = showMenu,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(text = "Status Privacy") },
            onClick = { onDismiss() }
        )
        DropdownMenuItem(
            text = { Text(text = "Create Channel") },
            onClick = { onDismiss() }
        )
        DropdownMenuItem(
            text = { Text(text = "Settings") },
            onClick = { onDismiss() }
        )
    }

}