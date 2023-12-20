package com.bangkit.sehatin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sehatin.data.network.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val btnLogIn: Button = findViewById(R.id.btnLogIn)
        btnLogIn.setOnClickListener {
            performLogIn()
        }
    }

    private fun performLogIn() {
        val editTextEmail: EditText = findViewById(R.id.editTextEmailLogin)
        val editTextPassword: EditText = findViewById(R.id.editTextPasswordLogin)

        val email: String = editTextEmail.text.toString()
        val password: String = editTextPassword.text.toString()

        // Inisialisasi Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sehatin-api-64zqryr67a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Buat ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Panggil API login
        val call = apiService.logIn(LoginData(email, password))
        call.enqueue(object : Callback<Response<String>> {
            override fun onResponse(call: Call<Response<String>>, response: Response<Response<String>>) {
                if (response.isSuccessful) {
                    // Handle the successful response
                    val result = response.body()?.body() // Mengasumsikan respons memiliki bidang "body"
                    Toast.makeText(applicationContext, "Login berhasil. Token: $result", Toast.LENGTH_SHORT).show()
                } else if (response.code() == 401) {
                    // Unauthorized: Email atau password salah
                    Toast.makeText(applicationContext, "Error: Email atau password salah", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle other error responses
                    Toast.makeText(applicationContext, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                // Handle network failure
                Toast.makeText(applicationContext, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
