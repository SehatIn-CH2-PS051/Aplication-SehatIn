package com.bangkit.sehatin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sehatin.data.network.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurposeActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var gender: String
    private var age: Int = 0
    private var height: Int = 0
    private var weight: Int = 0
    private lateinit var activityLevel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purpose)

        // Ambil data dari intent
        email = intent.getStringExtra("email") ?: ""
        username = intent.getStringExtra("name") ?: ""
        password = intent.getStringExtra("password") ?: ""
        gender = intent.getStringExtra("gender") ?: ""
        age = intent.getIntExtra("age", 0)
        height = intent.getIntExtra("height", 0)
        weight = intent.getIntExtra("weight", 0)
        activityLevel = intent.getStringExtra("activity_level") ?: ""

        // Menginisialisasi tombol-tombol
        val myButton1: Button = findViewById(R.id.myButton1)
        val myButton2: Button = findViewById(R.id.myButton2)
        val myButton3: Button = findViewById(R.id.myButton3)

        // Menambahkan OnClickListener pada setiap tombol
        myButton1.setOnClickListener {
            handleButtonClick("gain")
        }

        myButton2.setOnClickListener {
            handleButtonClick("maintain")
        }

        myButton3.setOnClickListener {
            handleButtonClick("lose")
        }
    }

    private fun handleButtonClick(goal: String) {
        // Buat objek RegistrationData
        val registrationData = RegistrationData(
            email = email,
            password = password,
            name = username,
            age = age,
            gender = gender,
            height = height,
            weight = weight,
            activity_level = activityLevel,
            goal = goal
        )

        // Melakukan proses registrasi ke API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sehatin-api-64zqryr67a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.signUp(registrationData)

        Log.d("API_REQUEST", "Request Data: $registrationData")
        call.enqueue(object : Callback<Response<String>> {
            override fun onResponse(call: Call<Response<String>>, response: Response<Response<String>>) {
                if (response.isSuccessful) {
                    // Handle response yang berhasil (jika diperlukan)
                    val result = response.body()?.body() // Assuming the response has a "body" field
                    Toast.makeText(applicationContext, "Registrasi berhasil. Response: $result", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle response yang tidak berhasil (jika diperlukan)
                    Toast.makeText(applicationContext, "Registrasi gagal. Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                // Handle kegagalan jaringan (jika diperlukan)
                Toast.makeText(applicationContext, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
