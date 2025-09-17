package com.amar.chat.utils

import android.content.Context

class SharedPref(context: Context) {

    private val sharedPref = context.getSharedPreferences("ChatAppFireBase", Context.MODE_PRIVATE)
    private val prefEdit = sharedPref.edit()

    var isLogin: Boolean
        get() = sharedPref.getBoolean("isLogin", false)
        set(value) = prefEdit.putBoolean("isLogin", value).apply()

    var userName: String
        get() = sharedPref.getString("userName", "") ?: ""
        set(value) = prefEdit.putString("userName", value).apply()

    var user: String
        get() = sharedPref.getString("user", "") ?: ""
        set(value) = prefEdit.putString("user", value).apply()

}