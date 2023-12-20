package com.bangkit.sehatin.data.network.retrofit

import com.bangkit.sehatin.LoginData
import com.bangkit.sehatin.RegistrationData
import com.bangkit.sehatin.data.network.response.FoodResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("/register")
    fun signUp(@Body registrationData: RegistrationData): Call<Response<String>>

    @POST("/login")
    fun logIn(@Body loginData: LoginData): Call<Response<String>>

    @GET("/user")
    fun getUserInfo(): Call<ResponseBody>

    @Multipart
    @POST("/food")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
    ): FoodResponse



}