package com.example.daisyapp.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.daisyapp.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveLoginSession(userModel: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = userModel.email
            preferences[TOKEN_KEY] = userModel.token
            preferences[IS_LOGIN_KEY] = true
//            preferences[IMAGE] = userModel.image
        }
    }

    suspend fun logoutSession() {
        dataStore.edit { preferences ->
            preferences.remove(IS_LOGIN_KEY)
            preferences.remove(TOKEN_KEY)
            preferences.remove(NAME)
//            preferences.remove(IMAGE)
        }
    }

    fun getLoginSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
                )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val NAME = stringPreferencesKey("name")
    }
}