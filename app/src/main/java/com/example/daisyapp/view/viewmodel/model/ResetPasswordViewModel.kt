package com.example.daisyapp.view.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daisyapp.data.preference.Result
import com.example.daisyapp.data.repository.AuthRepository
import com.example.daisyapp.data.response.ResetPasswordResponse
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _resetPasswordResult = MutableLiveData<Result<ResetPasswordResponse>>()
    val resetPasswordResult: LiveData<Result<ResetPasswordResponse>> = _resetPasswordResult

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetPasswordResult.value = Result.DataLoading
            val result = repository.resetPassword(email)
            _resetPasswordResult.value = result
        }
    }
}