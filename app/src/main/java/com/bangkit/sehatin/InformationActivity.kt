package com.bangkit.sehatin

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class InformationActivity : AppCompatActivity() {

    private lateinit var genderDropdown: AutoCompleteTextView
    private lateinit var age: EditText
    private lateinit var height: EditText
    private lateinit var weight: EditText
    private lateinit var activityLevelDropdown: AutoCompleteTextView
    private lateinit var email: String
    private lateinit var username: String
    private lateinit var password: String

    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        genderDropdown = findViewById(R.id.gender)
        age = findViewById(R.id.age)
        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        activityLevelDropdown = findViewById(R.id.activitylevel)

        val genderOptions = listOf("male", "female")
        val genderAdapter = ArrayAdapter(this, R.layout.dropdown_item, genderOptions)
        genderDropdown.setAdapter(genderAdapter)

        // Tambahkan opsi untuk level aktivitas
        val activityLevelOptions = listOf("sedentary", "lightly active", "moderately active", "very active", "extra active")
        val activityLevelAdapter = ArrayAdapter(this, R.layout.dropdown_item, activityLevelOptions)
        activityLevelDropdown.setAdapter(activityLevelAdapter)

        val genderDropdownButton: ImageView = findViewById(R.id.genderDropdownButton)
        val activityLevelDropdownButton: ImageView = findViewById(R.id.activityLevelDropdownButton)

        genderDropdownButton.setOnClickListener {
            genderDropdown.showDropDown()
        }

        activityLevelDropdownButton.setOnClickListener {
            activityLevelDropdown.showDropDown()
        }

        // Ambil data dari intent
        email = intent.getStringExtra("email") ?: ""
        username = intent.getStringExtra("name") ?: ""
        password = intent.getStringExtra("password") ?: ""

        val btnNext: Button = findViewById(R.id.NextStep2)
        btnNext.setOnClickListener {
            // Lanjut ke halaman berikutnya
            val intent = Intent(this, PurposeActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("name", username)
            intent.putExtra("password", password)
            intent.putExtra("gender", genderDropdown.text.toString()) // Tetapkan nilai dari AutoCompleteTextView
            intent.putExtra("age", age.text.toString().toInt())
            intent.putExtra("height", height.text.toString().toInt())
            intent.putExtra("weight", weight.text.toString().toInt())
            intent.putExtra("activity_level", activityLevelDropdown.text.toString()) // Tetapkan nilai dari AutoCompleteTextView

            startActivity(intent)
        }

    }
}
