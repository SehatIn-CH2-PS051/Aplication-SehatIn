package com.bangkit.sehatin.view.LSTMInfo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bangkit.sehatin.R
import com.bangkit.sehatin.data.network.retrofit.ApiConfig
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import com.squareup.picasso.Picasso // Add this import for Picasso
import retrofit2.Callback
import retrofit2.Response

class LSTMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lstmactivity)
          // Ambil token dari SharedPreferences atau dari tempat penyimpanan lainnya
        val sharedPreferences = getSharedPreferences("sehatin", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
          // Inisialisasi Retrofit dengan interceptor
        // Buat ApiService
        val apiService = ApiConfig.getApiWithTokenService(token.toString())

        // Panggil API getUserInfo
        val call = apiService.getLSTMChart()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle the successful response
                    val responseBody = response.body()?.string()
                    val json = responseBody?.let { JSONObject(it) }
                    // Lakukan sesuatu dengan data JSON yang diterima
                    // Periksa apakah objek JSON memiliki properti "user"
                    if (json?.has("data") == true) {
                        // Akses objek "user" dari JSON
                        val dataObject = json.getJSONObject("data")

                        val caloriesChart = dataObject.optString("calories_consumption_plot_url")
                        val macroChart = dataObject.optString("macronutrient_proportion_plot_url")
                        val predChart = dataObject.optString("predicted_values_plot_url")
                        val message = dataObject.optString("message")
                         // Mendapatkan referensi ImageView
                        val imageView1 = findViewById<ImageView>(R.id.caloriesChart)
                        val imageView2 = findViewById<ImageView>(R.id.macroChart)
                        val imageView3 = findViewById<ImageView>(R.id.predChart)
                          // Menggunakan Picasso untuk memuat gambar dari URL ke ImageView
                        Picasso.get().load(caloriesChart).into(imageView1)
                        Picasso.get().load(macroChart).into(imageView2)
                        Picasso.get().load(predChart).into(imageView3)
                    } else {
                        // JSON tidak memiliki properti "user"
                        Log.e("Response", "JSON tidak memiliki properti")
                    }
                } else {
                    // Handle other error responses
                    Toast.makeText(applicationContext, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failure
                Toast.makeText(applicationContext, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}