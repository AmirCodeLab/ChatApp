package com.amar.chat.main_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.amar.chat.main_feature.presentation.home_screen.HomeScreen
import com.amar.chat.main_feature.presentation.update_screen.UpdateScreen
import com.amar.chat.ui.theme.ChatAppFireBaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppFireBaseTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    UpdateScreen()
                }
            }
        }
    }
}