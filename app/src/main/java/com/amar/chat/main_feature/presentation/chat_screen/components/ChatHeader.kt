package com.amar.chat.main_feature.presentation.chat_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amar.chat.R
import com.amar.chat.ui.theme.LightGray

@Composable
@Preview(showBackground = true)
private fun ChatHeaderPreview() {
    ChatHeader()
}

@Composable
fun ChatHeader(
    modifier: Modifier = Modifier,
    chatPersonName: String = "",
) {

    Column {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(38.dp),
                painter = painterResource(R.drawable.img_male),
                contentDescription = null,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = chatPersonName.ifEmpty { "ChatApp" },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 6.dp),
            color = LightGray
        )
    }

}