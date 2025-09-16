package com.amar.chat.main_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.amar.chat.main_feature.presentation.calls_screen.CallScreen
import com.amar.chat.ui.theme.ChatAppFireBaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppFireBaseTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    CallScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}