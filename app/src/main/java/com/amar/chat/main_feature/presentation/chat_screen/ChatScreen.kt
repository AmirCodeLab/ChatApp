package com.amar.chat.main_feature.presentation.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.main_feature.presentation._view_model.ChatViewModel
import com.amar.chat.main_feature.presentation.chat_screen.components.ChatBottomInput
import com.amar.chat.main_feature.presentation.chat_screen.components.ChatHeader
import com.amar.chat.ui.theme.Black
import com.amar.chat.ui.theme.White
import com.amar.chat.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview(showSystemUi = true)
private fun PreviewChatScreen() {
    ChatScreen("", "")
}

@Composable
fun ChatScreen(
    chatId: String,
    chatPersonName: String
) {
    val viewModel: ChatViewModel = koinViewModel()

    val context = LocalContext.current
    val messages by viewModel.messages.collectAsState()
    val currentUserId = viewModel.currentUserUid
    var input by remember { mutableStateOf("") }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(chatId) {
        viewModel.observeMessages(chatId)
    }
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            coroutineScope.launch {
                delay(100) // give Compose a moment to measure
                listState.scrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth().systemBarsPadding(),
        topBar = {
            ChatHeader(
                modifier = Modifier.padding(horizontal = 14.dp),
                chatPersonName = chatPersonName
            )
        },
        bottomBar = {
            ChatBottomInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                inputValue = input,
                onValueChange = { value -> input = value },
                onSendClick = {
                    if (input.isNotBlank()) {
                        viewModel.sendMessage(chatId, input.trim()) { isSuccess, error ->
                            if (isSuccess) input = ""
                            else Utils.showToast(context, error ?: "Something went wrong")
                        }
                    }
                }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState,
                reverseLayout = false,
            ) {
                items(messages.size) { index ->
                    val msg = messages[index]
                    val isMine = msg.senderId == currentUserId
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
                    ) {
                        Box(
                            Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isMine) 16.dp else 0.dp,
                                        bottomEnd = if (isMine) 0.dp else 16.dp
                                    )
                                )
                                .background(
                                    if (isMine) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.secondaryContainer
                                )
                                .padding(10.dp)
                        ) {
                            Text(
                                msg.text,
                                color = if (isMine) White else Black,
                                textAlign = if (isMine) TextAlign.End else TextAlign.Start
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }

        }
    }
}