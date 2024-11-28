package com.example.daisyapp.data.retrofit

import com.example.daisyapp.data.response.LoginResponse
import com.example.daisyapp.data.response.RegisterResponse
import com.example.daisyapp.data.response.ResetPasswordResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPassword(
        @Field("email") email: String
    ): ResetPasswordResponse
}