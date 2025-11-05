package com.amar.chat.main_feature.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.main_feature.presentation._common.FloatingButton
import com.amar.chat.main_feature.presentation._view_model.HomeViewModel
import com.amar.chat.main_feature.presentation.home_screen.components.AddContactDialog
import com.amar.chat.main_feature.presentation.home_screen.components.BottomNavigation
import com.amar.chat.main_feature.presentation.home_screen.components.HomeChatItem
import com.amar.chat.main_feature.presentation.home_screen.components.HomeHeader
import com.amar.chat.ui.theme.LightGray
import com.amar.chat.utils.Utils
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview(showSystemUi = true)
fun HomeScreen(
    onNavigate: (chatId: String, otherUserId: String) -> Unit = { _, _ -> },
) {

    val viewModel: HomeViewModel = koinViewModel()
    val context = LocalContext.current
    var showPopup by remember { mutableStateOf(false) }

    val chatList by viewModel.chatList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeChatList()
    }

    Scaffold(
        floatingActionButton = { FloatingButton { showPopup = true } },
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

            if (chatList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("No chats yet.")
                    Text("Add a contact to start chatting.")
                }
            } else {
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    items(chatList.size) { index ->
                        val chat = chatList[index]
                        HomeChatItem(chat = chat) {
                            onNavigate.invoke(chat.chatId, chat.otherUserId)
                        }
                    }
                }
            }
        }

        if (showPopup) {
            AddContactDialog(
                onDismiss = {
                    showPopup = false
                },
                onAdd = { number ->
                    showPopup = false
                    viewModel.addContact(number) { isSuccess, error ->
                        if (isSuccess) {
                            Utils.showToast(context, "Added successfully")
                        } else {
                            Utils.showToast(context, error ?: "Something went wrong")
                        }
                    }
                }
            )
        }
    }

}