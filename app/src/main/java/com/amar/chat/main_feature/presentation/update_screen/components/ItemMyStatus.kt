package com.amar.chat.main_feature.presentation.update_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amar.chat.R
import com.amar.chat.ui.theme.DarkOrange
import com.amar.chat.ui.theme.White

@Composable
@Preview(showBackground = true)
private fun PreviewItemAddStatus() {
    ItemMyStatus()
}

@Composable
fun ItemMyStatus(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box {
            Image(
                modifier = Modifier.size(52.dp),
                painter = painterResource(R.drawable.img_male),
                contentDescription = ""
            )

            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color = DarkOrange)
                    .align(Alignment.BottomEnd),
                imageVector = Icons.Default.Add,
                contentDescription = "",
                colorFilter = ColorFilter.tint(White)
            )

        }

        Spacer(Modifier.width(8.dp))

        Column {
            Text(
                text = "Amr Khn",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Tap to add status",
                fontSize = 14.sp,
            )
        }

    }

}