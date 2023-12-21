package com.bangkit.sehatin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ChatBoxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_box)
        val startChatButton = findViewById<Button>(R.id.startChatButton)

         startChatButton.setOnClickListener {
            // Create an Intent to start the ChatBoxActivity
            val intent = Intent(this, ChatBotMessage::class.java)

            // Start the ChatBoxActivity
            startActivity(intent)
        }
    }
}