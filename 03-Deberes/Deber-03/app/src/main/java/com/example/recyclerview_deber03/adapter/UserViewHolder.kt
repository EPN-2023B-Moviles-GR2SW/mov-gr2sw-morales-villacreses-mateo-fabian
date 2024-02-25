package com.example.recyclerview_deber03.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview_deber03.R
import com.example.recyclerview_deber03.User

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val userNombre = view.findViewById<TextView>(R.id.tv_nombre)
    val userMensaje = view.findViewById<TextView>(R.id.tv_mensaje)
    val userFecha = view.findViewById<TextView>(R.id.tv_fecha)
    val userFoto = view.findViewById<ImageView>(R.id.iv_foto)

    fun render(userModel: User) {
        userNombre.text = userModel.nombre
        userMensaje.text = userModel.mensaje
        userFecha.text = userModel.fecha
        Glide.with(userFoto.context).load(userModel.photo).into(userFoto)
    }
}