package com.bangkit.sehatin.data.network.retrofit

import android.content.ContentValues
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

object ApiConfig {
    fun getApiWithTokenService(token: String): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestBody = req.body

        // Log the request body if it exists
        requestBody?.let {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            val requestBodyString = buffer.readString(StandardCharsets.UTF_8)
            // Log or print the request body as needed
            android.util.Log.d(ContentValues.TAG, "ReqBody: ${requestBodyString.toString()}")
        }
            val requestHeaders = req.newBuilder()
                .addHeader("Auth", token)
                .build()
            chain.proceed(requestHeaders)
        }
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sehatin-api-64zqryr67a-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sehatin-api-64zqryr67a-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}