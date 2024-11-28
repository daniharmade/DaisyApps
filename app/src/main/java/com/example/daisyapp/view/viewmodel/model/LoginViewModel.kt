package com.example.daisyapp.view.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daisyapp.data.model.UserModel
import com.example.daisyapp.data.preference.Result
import com.example.daisyapp.data.repository.AuthRepository
import com.example.daisyapp.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> =_loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.DataLoading
            val result = repository.login(email, password)
            _loginResult.value = result
        }
    }

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveLoginSession(userModel)
        }
    }
}