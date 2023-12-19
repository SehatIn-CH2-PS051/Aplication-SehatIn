package com.bangkit.sehatin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InformationActivity : AppCompatActivity() {

    private lateinit var gender: EditText
    private lateinit var age: EditText
    private lateinit var height: EditText
    private lateinit var weight: EditText
    private lateinit var activitylevel: EditText

    private lateinit var email: String
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        gender = findViewById(R.id.gender)
        age = findViewById(R.id.age)
        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        activitylevel = findViewById(R.id.activitylevel)

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
            intent.putExtra("gender", gender.text.toString())
            intent.putExtra("age", age.text.toString().toInt())
            intent.putExtra("height", height.text.toString().toInt())
            intent.putExtra("weight", weight.text.toString().toInt())
            intent.putExtra("activity_level", activitylevel.text.toString())

            startActivity(intent)
        }
    }
}
