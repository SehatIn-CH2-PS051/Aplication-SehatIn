package com.bangkit.sehatin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.sehatin.view.Dashboard.DashboardActivity
import com.bangkit.sehatin.view.addFood.AddFoodActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Periksa apakah token tersimpan
        val sharedPreferences = getSharedPreferences("sehatin", Context.MODE_PRIVATE)
        val storedToken = sharedPreferences.getString("token", null)

        if (storedToken != null) {
            // Jika token tersimpan, redirect
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()  // Tutup com.bangkit.sehatin.MainActivity agar tidak dapat kembali dengan tombol "back"
        }
    }

    fun goToSignUpActivity(view: android.view.View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun goToLogInActivity(view: android.view.View) {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }

    fun goToCameraActivity(view: android.view.View) {
        val intent = Intent(this, AddFoodActivity::class.java)
        startActivity(intent)
    }
}