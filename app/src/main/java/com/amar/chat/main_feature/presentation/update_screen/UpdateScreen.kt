package com.amar.chat.main_feature.presentation.update_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.R
import com.amar.chat.main_feature.presentation.common.FloatingButton
import com.amar.chat.main_feature.presentation.update_screen.components.UpdateAppBar
import com.amar.chat.main_feature.presentation.update_screen.components.UpdateHeader
import com.amar.chat.ui.theme.LightGray

@Composable
@Preview(showSystemUi = true)
fun UpdateScreen() {
    Scaffold(
        floatingActionButton = { FloatingButton(icon = R.drawable.ic_camera_24) { } },
    ) {

        val context = LocalContext.current
        val isSearch = remember { mutableStateOf(false) }
        val inputValue = remember { mutableStateOf("") }
        val showMenu = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(it)
        ) {
            UpdateHeader(
                modifier = Modifier.padding(horizontal = 14.dp),
                isSearch = isSearch.value,
                showMenu = showMenu.value,
                inputValue = inputValue.value,
                onInputChange = { input -> inputValue.value = input },
                onSearchInput = { input ->
                    Toast.makeText(context, "search: $input", Toast.LENGTH_SHORT).show()
                },
                onSearchToggle = { isSearch.value = !isSearch.value },
                onMenuItemClick = { showMenu.value = !showMenu.value },
                onCameraItemClick = { }
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 6.dp),
                color = LightGray
            )

        }
    }
}