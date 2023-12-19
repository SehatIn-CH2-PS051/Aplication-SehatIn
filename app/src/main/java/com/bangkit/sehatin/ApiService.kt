package com.bangkit.sehatin

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/register")
    fun signUp(@Body registrationData: RegistrationData): Call<Response<String>>

    @POST("/login")
    fun logIn(@Body loginData: LoginData): Call<ResponseBody>

    @GET("/user")
    fun getUserInfo(): Call<ResponseBody>
}