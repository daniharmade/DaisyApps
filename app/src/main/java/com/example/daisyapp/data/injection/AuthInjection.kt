package com.example.daisyapp.data.injection

import android.content.Context
import com.example.daisyapp.data.preference.UserPreference
import com.example.daisyapp.data.preference.dataStore
import com.example.daisyapp.data.repository.AuthRepository
import com.example.daisyapp.data.retrofit.AuthConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object AuthInjection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { userPreference.getLoginSession().first() }
        val authApiService = AuthConfig.getApiService(user.token)
        return AuthRepository(authApiService, userPreference)
    }
}