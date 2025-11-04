package com.amar.chat.main_feature.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.amar.chat.R
import com.amar.chat.main_feature.domain.entities.ItemChatList
import com.amar.chat.utils.Utils

@Composable
@Preview(showBackground = true)
private fun PreviewHomeChatItem() {
    /*HomeChatItem(
        chat = ItemChatList(
            otherUserName = "amr",
            lastMessage = "hello",
            lastTimestamp = 123456
        )
    ) { }*/
}

@Composable
fun HomeChatItem(
    modifier: Modifier = Modifier,
    chat: ItemChatList,
    onClick: (ItemChatList) -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(chat) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.img_male),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(4.dp))

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = chat.otherUserName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = Utils.toChatTime(chat.lastTimestamp),
                        fontSize = 14.sp,
                    )
                }

                Spacer(Modifier.height(2.dp))

                Text(
                    text = chat.lastMessage.ifEmpty { "Last Message" },
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}
