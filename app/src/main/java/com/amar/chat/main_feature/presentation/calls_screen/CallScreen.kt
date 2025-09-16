package com.amar.chat.main_feature.presentation.calls_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amar.chat.main_feature.presentation.calls_screen.components.CallHeader
import com.amar.chat.main_feature.presentation.calls_screen.components.ItemRecentCall
import com.amar.chat.ui.theme.LightGray

@Composable
@Preview(showSystemUi = true)
fun CallScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
    ) {

        CallHeader(modifier = Modifier.padding(horizontal = 14.dp))

        HorizontalDivider(
            modifier = Modifier.padding(6.dp),
            color = LightGray
        )

        ItemRecentCall {  }

    }

}