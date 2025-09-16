package com.amar.chat.main_feature.presentation.update_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.amar.chat.main_feature.domain.entities.ItemStatusList

@Composable
@Preview(showBackground = true)
private fun PreviewItemUserStatus() {
    ItemUserStatus(item = ItemStatusList(
        image = R.drawable.img_male,
        name = "Amr Khn",
        time = "Just now"
    ))
}

@Composable
fun ItemUserStatus(
    modifier: Modifier = Modifier,
    item: ItemStatusList,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            modifier = Modifier.size(52.dp),
            painter = painterResource(item.image),
            contentDescription = ""
        )

        Spacer(Modifier.width(8.dp))

        Column {
            Text(
                text = item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = item.time,
                fontSize = 14.sp,
            )
        }

    }

}