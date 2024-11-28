package com.example.daisyapp.view.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daisyapp.data.injection.AuthInjection
import com.example.daisyapp.data.repository.AuthRepository
import com.example.daisyapp.view.viewmodel.model.LoginViewModel
import com.example.daisyapp.view.viewmodel.model.MainViewModel
import com.example.daisyapp.view.viewmodel.model.RegisterViewModel
import com.example.daisyapp.view.viewmodel.model.ResetPasswordViewModel

class AuthViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResetPasswordViewModel::class.java) -> {
                ResetPasswordViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context) = AuthViewModelFactory(
            AuthInjection.provideAuthRepository(context)
        )
    }
}