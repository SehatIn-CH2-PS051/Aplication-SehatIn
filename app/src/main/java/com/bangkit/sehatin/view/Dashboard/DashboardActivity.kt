package com.bangkit.sehatin.view.Dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.sehatin.Adapter.ImageSliderAdapter
import com.bangkit.sehatin.ChatBoxActivity
import com.bangkit.sehatin.LogActivity
import com.bangkit.sehatin.LogInActivity
import com.bangkit.sehatin.R
import com.bangkit.sehatin.view.UserInformation.UserInfoActivity
import com.bangkit.sehatin.view.addFood.AddFoodActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val imageResourceIds = listOf(
        R.drawable.item1,
        R.drawable.item2,
        R.drawable.item3
        // ... tambahkan resource ID gambar lainnya
    )

    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val currentItem = viewPager.currentItem
            viewPager.setCurrentItem(if (currentItem == imageResourceIds.size - 1) 0 else currentItem + 1, true)
            autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL)
        }
    }

    companion object {
        private const val AUTO_SCROLL_INTERVAL = 5000L // Atur interval sesuai kebutuhan Anda (dalam milidetik)
    }



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val scanMenu : CardView = findViewById(R.id.scanKuy)
        val profilMenu : CardView = findViewById(R.id.profilku)
        val chatMenu : CardView = findViewById(R.id.chatBot)
        val logMenu : CardView = findViewById(R.id.logKu)
        val logoutButton : ImageButton = findViewById(R.id.logoutButton)

        viewPager = findViewById(R.id.imageSliderViewPager)
        imageSliderAdapter = ImageSliderAdapter(imageResourceIds)
        viewPager.adapter = imageSliderAdapter


        autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL)

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
            autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL)
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
            val intent = Intent(this, ChatBoxActivity::class.java)
            startActivity(intent)
        }
        logMenu.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onDestroy() {
        super.onDestroy()
        // Hentikan otomatis geser ketika aktivitas dihancurkan
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }
}