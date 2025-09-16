package com.amar.chat.main_feature.presentation.update_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amar.chat.R
import com.amar.chat.main_feature.domain.entities.ItemStatusList
import com.amar.chat.main_feature.presentation.common.FloatingButton
import com.amar.chat.main_feature.presentation.update_screen.components.ItemMyStatus
import com.amar.chat.main_feature.presentation.update_screen.components.ItemUserStatus
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

            Text(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                text = "Status",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            ItemMyStatus()

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(getStatusItem()) { item ->
                    ItemUserStatus(item = item)
                }
            }

        }
    }
}

private fun getStatusItem(): List<ItemStatusList> {
    return listOf(
        ItemStatusList(
            image = R.drawable.img_female,
            name = "Amr",
            time = "11:00 AM"
        ),
        ItemStatusList(
            image = R.drawable.img_female,
            name = "Amr",
            time = "01:00 AM"
        ),
        ItemStatusList(
            image = R.drawable.img_male,
            name = "Amr",
            time = "12:00 PM"
        ),
        ItemStatusList(
            image = R.drawable.img_female,
            name = "Amr",
            time = "11:00 AM"
        ),
        ItemStatusList(
            image = R.drawable.img_female,
            name = "Amr",
            time = "01:00 AM"
        ),
        ItemStatusList(
            image = R.drawable.img_male,
            name = "Amr",
            time = "12:00 PM"
        ),
        ItemStatusList(
            image = R.drawable.img_female,
            name = "Amr",
            time = "11:00 AM"
        ),
        ItemStatusList(
            image = R.drawable.img_female,
            name = "Amr",
            time = "01:00 AM"
        ),
        ItemStatusList(
            image = R.drawable.img_male,
            name = "Amr",
            time = "12:00 PM"
        ),
    )
}