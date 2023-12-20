package com.bangkit.sehatin.view.addFood

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bangkit.sehatin.R
import com.bangkit.sehatin.data.network.response.FoodResponse
import com.bangkit.sehatin.databinding.ActivityAddFoodBinding
import com.bangkit.sehatin.databinding.ActivityFoodResultBinding

class FoodResultActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityFoodResultBinding
    private  var FoodResult: FoodResponse? = null
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFoodResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}