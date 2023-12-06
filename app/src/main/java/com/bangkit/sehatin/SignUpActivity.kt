package com.bangkit.sehatin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import okhttp3.ResponseBody
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUp: Button = findViewById(R.id.NextStep)
        btnSignUp.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val editTextConfirmPassword: EditText = findViewById(R.id.editTextConfirmPassword)

        val email: String = editTextEmail.text.toString()
        val password: String = editTextPassword.text.toString()
        val confirmPassword: String = editTextConfirmPassword.text.toString()

        // ... (Validation checks, similar to your existing code)

        val registrationData = RegistrationData(email, password, "YourName", 25, "male", 170, 70, "gain")

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sehatin-api-64zqryr67a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Call the signUp API
        val call = apiService.signUp(registrationData)
        call.enqueue(object : Callback<Response<String>> {
            override fun onResponse(call: Call<Response<String>>, response: Response<Response<String>>) {
                if (response.isSuccessful) {
                    // Handle the successful response
                    val result = response.body()?.body() // Assuming the response has a "body" field
                    Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                } else if (response.code() == 409) {
                    // Conflict: User with the same email already exists
                    Toast.makeText(applicationContext, "Error: User with this email already exists", Toast.LENGTH_SHORT).show()
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