package com.amar.chat.utils

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    suspend fun showToastOnMain(context: Context, message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun toChatTime(time: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - time
        val oneDay = 24 * 60 * 60 * 1000

        return when {
            diff < oneDay -> SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(time))
            diff < 2 * oneDay -> "Yesterday"
            else -> SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(time))
        }
    }

}