package com.bangkit.sehatin

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/register")
    fun signUp(@Body registrationData: RegistrationData): Call<Response<String>>
}