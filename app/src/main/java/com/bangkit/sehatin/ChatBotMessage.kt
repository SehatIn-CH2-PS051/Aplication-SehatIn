package com.bangkit.sehatin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.sehatin.Adapter.ChatAdapter
import com.bangkit.sehatin.Model.ChatMessage
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class ChatBotMessage : AppCompatActivity() {
    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var socket: Socket
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot_message)

        messageEditText = findViewById(R.id.et_message)
        sendButton = findViewById(R.id.btn_send)

        val mockChat = createMockChat()
        initRecyclerView(mockChat)
        // Connect to Socket.IO server
        try {
            socket = IO.socket("https://sehatin-chatbot-api-64zqryr67a-et.a.run.app")
            val options = IO.Options()
//            options.forceNew = true
            options.transports = arrayOf("websocket")
            socket.connect()
            socket.emit("message", "Halo Mr.SehatIn")

            // Listen for 'message' event
            socket.on("message") { args ->
                runOnUiThread {
                    chatAdapter.addMessage(ChatMessage("Bot", args[0].toString()))

                    // Scroll to the bottom
                    mMessageRecycler.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
            // Display success message
            runOnUiThread {
                showToast("Socket connected successfully!")
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            runOnUiThread {
                showToast("Error connecting to the socket: ${e.message}")
            }
        }

        // Send button click listener
        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val userMessage = ChatMessage("User", messageText)
                chatAdapter.addMessage(userMessage)

                // Emit 'message' event to the server
                socket.emit("message", messageText)

                // Clear the input field
                messageEditText.text.clear()

                // Scroll to the bottom
                mMessageRecycler.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    private fun initRecyclerView(chatList: MutableList<ChatMessage>) {
        chatAdapter = ChatAdapter(chatList)
        mMessageRecycler = findViewById(R.id.recycler_view);
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        mMessageRecycler.layoutManager = layoutManager
        mMessageRecycler.adapter = chatAdapter

        // Scroll ke bagian bawah setelah menambahkan pesan baru
        mMessageRecycler.scrollToPosition(chatAdapter.itemCount - 1)
    }

    private fun createMockChat(): MutableList<ChatMessage> {
        return mutableListOf(
            ChatMessage("User", "Halo Mr.SehatIn"),
            // Tambahkan pesan sesuai kebutuhan
        )
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
