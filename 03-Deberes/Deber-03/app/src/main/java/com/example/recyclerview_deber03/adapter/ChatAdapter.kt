package com.example.recyclerview_deber03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_deber03.Message
import com.example.recyclerview_deber03.R

class ChatAdapter(private val messagesList: List<Message>) : RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChatViewHolder(layoutInflater.inflate(R.layout.chat_row, parent, false))
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = messagesList[position]
        holder.render(item)
    }

}