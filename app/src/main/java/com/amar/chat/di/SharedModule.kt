package com.amar.chat.di

import com.amar.chat.main_feature.presentation._view_model.BaseViewModel
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

    viewModel { BaseViewModel() }
    viewModel { PhoneAuthViewModel(get(), get(), get()) }

}