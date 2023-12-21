package com.bangkit.sehatin.view.Dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bangkit.sehatin.LogInActivity
import com.bangkit.sehatin.R
import com.bangkit.sehatin.view.LSTMInfo.LSTMActivity
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
        val logoutButton : ImageButton = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            // Get the SharedPreferences instance
            val sharedPreferences = getSharedPreferences("sehatin", MODE_PRIVATE)

            // Edit the SharedPreferences to remove the token
            val editor = sharedPreferences.edit()
            editor.remove("token")
            editor.apply()

            val intent = Intent(this, LogInActivity::class.java)
            // Clear the activity stack and start the login activity as a new task
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // Start the login activity
            startActivity(intent)

            // Finish the current activity to remove it from the stack
            finish()
        }
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
            val intent = Intent(this, LSTMActivity::class.java)
            startActivity(intent)
        }
    }
}