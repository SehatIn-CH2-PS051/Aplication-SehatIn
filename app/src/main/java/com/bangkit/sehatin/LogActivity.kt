package com.bangkit.sehatin

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bangkit.sehatin.data.network.retrofit.ApiService
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        // Inisialisasi Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sehatin-api-64zqryr67a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Inisialisasi ApiService
        apiService = retrofit.create(ApiService::class.java)

        // Contoh pemanggilan API getUserInfo
        getUserInfo()
    }

    private fun getUserInfo() {
        val call = apiService.getUserInfo()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle response here
                    val responseBody = response.body()?.string()
                    handleUserInfoResponse(responseBody)
                } else {
                    // Handle error response here
                    val errorMessage = when (response.code()) {
                        401 -> "Unauthorized: Email atau password salah"
                        else -> "Error: ${response.code()}"
                    }
                    showError(errorMessage)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failure here
                val errorMessage = "Network Error: ${t.message}"
                showError(errorMessage)
            }
        })
    }

    private fun handleUserInfoResponse(responseBody: String?) {
        // Handle user info response here
        if (responseBody != null) {
            try {
                val jsonObject = JSONObject(responseBody)
                val calorieIntake = jsonObject.getDouble("calorie_intake")

                // Set currentProgress
                val currentProgress = 500.0
                // Update ProgressBar
                updateProgressBar(currentProgress, calorieIntake)

                val calorieTextView = findViewById<TextView>(R.id.textViewBelow)
                calorieTextView.text = "$calorieIntake Kalori"
            } catch (e: JSONException) {
                // Handle JSON parsing error
                showError("Error parsing JSON")
            }
        } else {
            // Handle null response body
            showError("Empty response body")
        }
    }

    private fun updateProgressBar(currentProgress: Double, totalCalorieIntake: Double) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val progressText = findViewById<TextView>(R.id.textViewProgress)

        // Calculate the progress percentage
        val progressPercentage = ((currentProgress / totalCalorieIntake) * 100).toInt()

        // Set the progress
        progressBar.progress = progressPercentage

        // Set different colors based on the progress
        setProgressBarColor(progressBar, progressPercentage)

        // Set the progress text
        progressText.text = "$currentProgress / $totalCalorieIntake"
    }

    private fun setProgressBarColor(progressBar: ProgressBar, progressPercentage: Int) {
        val colorResId = when {
            progressPercentage < 25 -> R.color.blue
            progressPercentage < 50 -> R.color.green
            else -> android.R.color.holo_blue_light
        }

        val parsedColor = ContextCompat.getColor(this, colorResId)
        val colorStateList = ColorStateList.valueOf(parsedColor)
        progressBar.progressTintList = colorStateList
    }


    private fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}