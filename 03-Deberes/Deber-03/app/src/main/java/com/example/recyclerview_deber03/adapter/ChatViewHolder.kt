package com.example.recyclerview_deber03.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview_deber03.Message
import com.example.recyclerview_deber03.R

class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userMensaje = view.findViewById<TextView>(R.id.textview_from_row)
    val userFoto = view.findViewById<ImageView>(R.id.imageview_chat_from_row)

    fun render(messageModel: Message) {
        userMensaje.text = messageModel.mensaje
        Glide.with(userFoto.context).load(messageModel.photo).into(userFoto)
    }
}