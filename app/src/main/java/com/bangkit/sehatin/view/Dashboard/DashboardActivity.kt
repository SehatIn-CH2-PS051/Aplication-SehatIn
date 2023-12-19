package com.bangkit.sehatin.view.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.bangkit.sehatin.R
import com.bangkit.sehatin.view.UserInformation.UserInfoActivity
import com.bangkit.sehatin.view.addFood.AddFoodActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val scanMenu : CardView = findViewById(R.id.scanKuy)
        val profilMenu : CardView = findViewById(R.id.profilku)
        val chatMenu : CardView = findViewById(R.id.chatBot)
        val logMenu : CardView = findViewById(R.id.logKu)

        scanMenu.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivity(intent)
        }
        profilMenu.setOnClickListener {
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        chatMenu.setOnClickListener {
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        logMenu.setOnClickListener {
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
    }
}