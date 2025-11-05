package com.amar.chat.di

import com.amar.chat.main_feature.data.repository.FirebaseRepositoryImpl
import com.amar.chat.main_feature.domain.repository.FirebaseRepository
import com.amar.chat.main_feature.presentation._view_model.ChatViewModel
import com.amar.chat.main_feature.presentation._view_model.HomeViewModel
import com.amar.chat.main_feature.presentation._view_model.PhoneAuthViewModel
import com.amar.chat.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val SharedModule = module {

    single { SharedPref(get()) }

    single { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance() }
    single { FirebaseDatabase.getInstance().reference }

    viewModel { PhoneAuthViewModel(get(), get(), get()) }

    single<FirebaseRepository> { FirebaseRepositoryImpl(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ChatViewModel(get()) }

}