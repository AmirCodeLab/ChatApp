package com.amar.chat.main_feature.presentation.update_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amar.chat.R
import com.amar.chat.main_feature.presentation._common.DropDownMenu
import com.amar.chat.ui.theme.Transparent

@Composable
@Preview(showBackground = true)
private fun PreviewHomeHeader() {
    UpdateHeader(
        isSearch = true,
        showMenu = false,
        inputValue = "",
    )
}

@Composable
fun UpdateHeader(
    modifier: Modifier = Modifier,
    isSearch: Boolean,
    showMenu: Boolean,
    inputValue: String,
    onInputChange: (String) -> Unit = {},
    onSearchInput: (String) -> Unit = {},
    onSearchToggle: () -> Unit = {},
    onCameraItemClick: () -> Unit = {},
    onMenuItemClick: () -> Unit = {}
) {

    if (isSearch) {
        UpdateSearchBar(
            modifier = modifier,
            input = inputValue,
            onChange = { onInputChange(it) },
            onCloseIconClick = { onSearchToggle() },
            onSearchInput = { onSearchInput(it) }
        )
    } else {
        UpdateAppBar(
            modifier = modifier,
            showMenu = showMenu,
            onCameraItemClick = { onCameraItemClick() },
            onSearchItemClick = { onSearchToggle() },
            onMenuItemClick = { onMenuItemClick() }
        )
    }

}

@Composable
@Preview(showBackground = true)
fun UpdateSearchBar(
    modifier: Modifier = Modifier,
    input: String = "",
    onChange: (String) -> Unit = {},
    onCloseIconClick: () -> Unit = {},
    onSearchInput: (String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .focusRequester(focusRequester),
        value = input,
        placeholder = { Text(text = "Search") },
        onValueChange = { onChange(it) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Transparent,
            unfocusedContainerColor = Transparent,
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
        ),
        trailingIcon = {
            IconButton(onClick = {
                onChange("")
                onCloseIconClick()
                focusManager.clearFocus()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearchInput(input)
            }
        )
    )
}

@Composable
@Preview(showBackground = true)
fun UpdateAppBar(
    modifier: Modifier = Modifier,
    showMenu: Boolean = false,
    onCameraItemClick: () -> Unit = {},
    onSearchItemClick: () -> Unit = {},
    onMenuItemClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "Updates",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            MyIconButton(icon = R.drawable.img_camera) { onCameraItemClick() }
            Spacer(Modifier.width(14.dp))
            MyIconButton(icon = R.drawable.img_search) { onSearchItemClick() }
            Spacer(Modifier.width(4.dp))

            Box {
                MyIconButton(icon = R.drawable.img_more) { onMenuItemClick() }
                DropDownMenu(
                    showMenu = showMenu,
                    onDismiss = { onMenuItemClick() }
                )
            }
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