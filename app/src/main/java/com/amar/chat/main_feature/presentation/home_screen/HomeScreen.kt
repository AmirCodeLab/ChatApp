package com.amar.chat.main_feature.presentation.home_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.R
import com.amar.chat.main_feature.domain.entities.ItemChatList
import com.amar.chat.main_feature.presentation.home_screen.components.BottomNavigation
import com.amar.chat.main_feature.presentation.home_screen.components.HomeChatItem
import com.amar.chat.main_feature.presentation._common.FloatingButton
import com.amar.chat.main_feature.presentation.home_screen.components.HomeHeader
import com.amar.chat.ui.theme.LightGray

@Composable
@Preview(showSystemUi = true)
fun HomeScreen() {

    val context = LocalContext.current

    Scaffold(
        floatingActionButton = { FloatingButton { } },
        bottomBar = { BottomNavigation() }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            HomeHeader(
                modifier = Modifier.padding(horizontal = 14.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 6.dp),
                color = LightGray
            )

            LazyColumn {
                items(getChatData()) { item ->
                    HomeChatItem(chat = item) { chatItem ->
                        Toast.makeText(context, chatItem.name, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

}

private fun getChatData(): List<ItemChatList> {
    return listOf(
        ItemChatList(
            R.drawable.img_male,
            name = "Amr khn",
            time = "10:00 AM",
            message = "How are you"
        ),
        ItemChatList(
            R.drawable.img_female,
            name = "Amr khn",
            time = "12:00 AM",
            message = "What are you doing"
        ),
        ItemChatList(
            R.drawable.img_male,
            name = "Amr khn",
            time = "01:00 AM",
            message = "where are you bro"
        )
    )
}