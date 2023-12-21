package com.bangkit.sehatin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.sehatin.Model.ChatMessage
import com.bangkit.sehatin.R

class ChatAdapter(private val chatList: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_BOT = 1
    private val VIEW_TYPE_USER = 2

    inner class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val botMessageTextView: TextView = itemView.findViewById(R.id.tv_bot_message)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userMessageTextView: TextView = itemView.findViewById(R.id.tv_user_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BOT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_ai, parent, false)
                BotViewHolder(view)
            }
            VIEW_TYPE_USER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_me, parent, false)
                UserViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = chatList[position]
        when (getItemViewType(position)) {
            VIEW_TYPE_BOT -> {
                val botHolder = holder as BotViewHolder
                botHolder.botMessageTextView.text = chatMessage.message
            }
            VIEW_TYPE_USER -> {
                val userHolder = holder as UserViewHolder
                userHolder.userMessageTextView.text = chatMessage.message
            }
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].sender == "Bot") {
            VIEW_TYPE_BOT
        } else {
            VIEW_TYPE_USER
        }
    }

    fun addMessage(chatMessage: ChatMessage) {
        chatList.add(chatMessage)
        notifyItemInserted(chatList.size - 1)
    }
}
