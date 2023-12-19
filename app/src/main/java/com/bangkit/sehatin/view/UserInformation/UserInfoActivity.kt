package com.bangkit.sehatin.view.UserInformation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sehatin.ApiService
import com.bangkit.sehatin.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        val bbText : TextView = findViewById<TextView>(R.id.beratbadan)
        val tbText : TextView = findViewById<TextView>(R.id.tinggibadan)
        val usiaText : TextView = findViewById<TextView>(R.id.usia)
        val jkText : TextView = findViewById<TextView>(R.id.jeniskelamin)
        val bmiText : TextView = findViewById<TextView>(R.id.txtBMR)
        val kkalText : TextView = findViewById<TextView>(R.id.asupanKalori)

        // Inisialisasi Retrofit dengan interceptor
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sehatin-api-64zqryr67a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())  // Tambahkan client dengan interceptor
            .build()

        // Buat ApiService
        val apiService = retrofit.create(ApiService::class.java)

        // Panggil API getUserInfo
        val call = apiService.getUserInfo()
        call.enqueue(object : Callback<ResponseBody> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle the successful response
                    val responseBody = response.body()?.string()
                    val json = responseBody?.let { JSONObject(it) }
                    // Lakukan sesuatu dengan data JSON yang diterima
                    // Periksa apakah objek JSON memiliki properti "user"
                    if (json?.has("user") == true) {
                        // Akses objek "user" dari JSON
                        val userObject = json.getJSONObject("user")
                        val calIntake = json.optString("calorie_intake")

                        // Ambil informasi pengguna yang diinginkan
                        val userName = userObject.optString("name")
                        val userAge = userObject.optInt("age")
                        val userGender = userObject.optString("gender")
                        val userHeight = userObject.optInt("height")
                        val userWeight = userObject.optInt("weight")
                        val userBMI = userObject.optDouble("bmi")
                        val userBMR = userObject.optInt("bmr")
                        val userClassification = userObject.optString("classification")
                        val userActivityLevel = userObject.optString("activity_level")
                        val userGoal = userObject.optString("goal")

                        // Tampilkan informasi pengguna di textview
                        bbText.text = "$userWeight KG"
                        tbText.text = "$userHeight CM"
                        usiaText.text = "$userAge Tahun"
                        jkText.text = userGender
                        bmiText.text = "$userBMI"
                        kkalText.text = "$calIntake KKal"
                    } else {
                        // JSON tidak memiliki properti "user"
                        Log.e("Response", "JSON tidak memiliki properti 'user'")
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

    private fun createOkHttpClient(): OkHttpClient {
        // Ambil token dari SharedPreferences atau dari tempat penyimpanan lainnya
        val sharedPreferences = getSharedPreferences("sehatin", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        // Buat interceptor untuk menambahkan header Authorization dengan token
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()

            // Tambahkan header Authorization
            if (!token.isNullOrEmpty()) {
                builder.addHeader("Auth", "$token")
            }

            // Lanjutkan dengan request yang telah dimodifikasi
            val newRequest = builder.build()
            chain.proceed(newRequest)
        }

        // Tambahkan interceptor ke OkHttpClient
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}
