package com.amar.chat.main_feature.presentation.home_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
@Preview(showBackground = true)
private fun PreviewHomeHeader() {
    HomeHeader()
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "ChatApp",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )

        Row (
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            MyIconButton(icon = R.drawable.img_camera) { }
            Spacer(Modifier.width(14.dp))
            MyIconButton(icon = R.drawable.img_search) { }
            Spacer(Modifier.width(4.dp))
            MyIconButton(icon = R.drawable.img_more) { }
        }
    }

}

@Composable
fun MyIconButton(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(26.dp),
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = ""
        )
    }
}