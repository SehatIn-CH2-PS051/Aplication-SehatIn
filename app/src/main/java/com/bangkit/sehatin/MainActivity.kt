package com.bangkit.sehatin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.sehatin.view.addFood.AddFoodActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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