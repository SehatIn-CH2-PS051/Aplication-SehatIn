package com.bangkit.sehatin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.sehatin.data.network.retrofit.ApiConfig
import com.bangkit.sehatin.data.network.retrofit.ApiService
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        foodAdapter = FoodAdapter()
        recyclerView.adapter = foodAdapter

        // Ambil token dari SharedPreferences atau dari tempat penyimpanan lainnya
        val sharedPreferences = requireActivity().getSharedPreferences("sehatin", AppCompatActivity.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")


        // Inisialisasi ApiService
        apiService = ApiConfig.getApiWithTokenService(token.toString())

        // Contoh pemanggilan API getEatLog
        getEatLog()

        return view
    }

    private fun getEatLog() {
        val call = apiService.getEatLog()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    val foodArray = responseBody?.let { JSONObject(it).getJSONArray("data") }
                    val foodList = foodArray?.let { parseFoodList(it) }
                    //
                    if (foodList != null) {
                        foodAdapter.setData(foodList)
                    }
                } else {
                    // Handle error response here
                    val errorMessage = when (response.code()) {
                        401 -> "Unauthorized: Email atau password salah"
                        else -> "Error: ${response.code()}"
                    }
                    // Handle error message as needed
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network failure here
                val errorMessage = "Network Error: ${t.message}"
                // Handle error message as needed
            }
        })
    }

    private fun parseFoodList(foodArray: JSONArray): List<FoodItem> {
        val foodList = mutableListOf<FoodItem>()

        for (i in 0 until foodArray.length()) {
            val foodObject = foodArray.getJSONObject(i)
            val foodName = foodObject.getString("food")
            val calories = foodObject.getInt("calories")
            val image_url = foodObject.getString("image_url")

            val foodItem = FoodItem(foodName, calories,image_url)
            foodList.add(foodItem)
        }

        return foodList
    }
}
