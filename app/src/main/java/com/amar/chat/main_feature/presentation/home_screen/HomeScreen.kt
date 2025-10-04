package com.amar.chat.main_feature.presentation.home_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.amar.chat.R
import com.amar.chat.main_feature.domain.entities.ItemChatList
import com.amar.chat.main_feature.presentation.home_screen.components.BottomNavigation
import com.amar.chat.main_feature.presentation.home_screen.components.HomeChatItem
import com.amar.chat.main_feature.presentation._common.FloatingButton
import com.amar.chat.main_feature.presentation._view_model.BaseViewModel
import com.amar.chat.main_feature.presentation.home_screen.components.HomeHeader
import com.amar.chat.ui.theme.LightGray
import com.google.firebase.auth.FirebaseAuth

@Composable
@Preview(showSystemUi = true)
fun HomeScreen(
    baseViewModel: BaseViewModel = hiltViewModel()
) {

    val showPopup = remember { mutableListOf(false) }
    val showMenu = remember { mutableListOf(false) }

    val chatData by baseViewModel.chatList.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (userId != null) {
        LaunchedEffect(Unit) {
            baseViewModel.getChatForUser(userId) { chatList ->

            }
        }
    }

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
                items(chatData) { item ->
                    HomeChatItem(baseViewModel = baseViewModel, chat = item) { chatItem ->
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
            image = R.drawable.img_male,
            name = "Amr khn",
            time = "10:00 AM",
            message = "How are you"
        ),
        ItemChatList(
            image = R.drawable.img_female,
            name = "Amr khn",
            time = "12:00 AM",
            message = "What are you doing"
        ),
        ItemChatList(
            image = R.drawable.img_male,
            name = "Amr khn",
            time = "01:00 AM",
            message = "where are you bro"
        )
    )
}