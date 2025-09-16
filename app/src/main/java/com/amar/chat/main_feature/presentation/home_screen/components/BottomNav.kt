package com.amar.chat.main_feature.presentation.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
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
import com.amar.chat.ui.theme.White

@Composable
@Preview(showBackground = true)
private fun PreviewBottomNavigation() {
    BottomNavigation()
}

@Composable
fun BottomNavigation() {
    BottomAppBar(
        tonalElevation = 12.dp,
        containerColor = White
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomNavItem(R.drawable.img_chat, "Chats")
            BottomNavItem(R.drawable.img_update, "Updates")
            BottomNavItem(R.drawable.img_communities, "Communities")
            BottomNavItem(R.drawable.img_telephone, "Calls")
        }

    }
}

@Composable
private fun BottomNavItem(icon: Int, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(26.dp),
            painter = painterResource(icon),
            contentDescription = ""
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}