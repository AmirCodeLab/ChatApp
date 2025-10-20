package com.amar.chat

import android.app.Application
import com.amar.chat.di.SharedModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(applicationContext)
            modules(SharedModule)
        }
    }

}