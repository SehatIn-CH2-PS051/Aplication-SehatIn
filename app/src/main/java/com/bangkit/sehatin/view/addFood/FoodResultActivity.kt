package com.bangkit.sehatin.view.addFood

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.bangkit.sehatin.Detail
import com.bangkit.sehatin.EatLogData
import com.bangkit.sehatin.R
import com.bangkit.sehatin.data.network.response.FoodResponse
import com.bangkit.sehatin.data.network.retrofit.ApiConfig
import com.bangkit.sehatin.databinding.ActivityAddFoodBinding
import com.bangkit.sehatin.databinding.ActivityFoodResultBinding
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodResultActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityFoodResultBinding
    private  var FoodResult: FoodResponse? = null
    private var currentImageUri: Uri? = null
    private var porsi = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFoodResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val button : Button = findViewById(R.id.button)
        currentImageUri = Uri.parse(intent.getStringExtra("imageUri"))


         FoodResult = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("data", FoodResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("data")
        }



        binding.apply {
            nama.text = FoodResult?.nama
            karbohidrat.text = FoodResult?.data?.get(0)?.detail?.karbohidrat
            lemak.text = FoodResult?.data?.get(0)?.detail?.lemak
            previewImageView.setImageURI(currentImageUri)
        }

        button.setOnClickListener {
            // Ambil token dari SharedPreferences atau dari tempat penyimpanan lainnya
            val sharedPreferences = getSharedPreferences("sehatin", MODE_PRIVATE)
            val token = sharedPreferences.getString("token", "")

            // Inisialisasi Retrofit dengan interceptor
            // Buat ApiService
            val apiService = ApiConfig.getApiWithTokenService(token.toString())
             // Panggil API getUserInfo
            val request = EatLogData(FoodResult?.nama.toString(), Detail(
                FoodResult?.data?.get(0)?.detail?.karbohidrat ?: "0g", FoodResult?.data?.get(0)?.detail?.lemak ?: "0g", FoodResult?.data?.get(0)?.detail?.protein ?: "0g"
            ),FoodResult?.data?.get(0)?.kalori ?: "0 kkal", porsi = porsi)
            Log.d(TAG, "FoodResult: ${request.toString()}")
            val call = apiService.eatLog(request)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Sukses", Toast.LENGTH_SHORT).show()
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


        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        val adapter = FoodResult?.let {
            it.data?.let { it1 ->
                ArrayAdapter(
                    this, android.R.layout.simple_spinner_item,
                    it1.map { it?.porsi }.toMutableList()
                )
            }
        }
        if (adapter != null) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            // Set the default selection to the first item
            spinner.setSelection(0)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // Ambil item yang dipilih dari Spinner
        val selectedItem = parent.getItemAtPosition(pos).toString()

        // Ubah teks pada TextView berdasarkan item yang dipilih
        FoodResult?.data?.forEach{
            if(selectedItem.contains(it?.porsi.toString())){
                binding.jumlahKalori.text = it?.kalori
                porsi = it?.porsi ?: ""
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}