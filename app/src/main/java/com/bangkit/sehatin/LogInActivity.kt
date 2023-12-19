package com.bangkit.sehatin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sehatin.view.Dashboard.DashboardActivity
import okhttp3.ResponseBody
import org.json.JSONObject
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
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                 if (response.isSuccessful) {
                   val responseBody = response.body()?.string()
                    val json = responseBody?.let { JSONObject(it) }
                    val token = json?.optString("token")
                     saveTokenToLocalStorage(token)
                    navigateToNextActivity()
                    Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failure
                Toast.makeText(applicationContext, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
      private fun saveTokenToLocalStorage(token: String?) {
        // Use SharedPreferences to store the token
        val sharedPref = getSharedPreferences("sehatin", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("token", token)
        editor.apply()
    }

    private fun navigateToNextActivity() {
        // Use Intent to navigate to another activity
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity if needed
    }
}
