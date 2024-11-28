package com.example.daisyapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
