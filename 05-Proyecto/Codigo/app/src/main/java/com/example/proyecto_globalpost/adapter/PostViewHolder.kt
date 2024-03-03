package com.example.proyecto_globalpost.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_globalpost.Post
import com.example.proyecto_globalpost.R

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val postAutor = view.findViewById<TextView>(R.id.tv_autor)
    val postTitulo = view.findViewById<TextView>(R.id.tv_titulo)
    val postFecha = view.findViewById<TextView>(R.id.tv_fecha)
    val postFoto = view.findViewById<ImageView>(R.id.iv_foto)

    fun render(postModel: Post){
        postAutor.text = postModel.autor
        postTitulo.text = postModel.titulo
        postFecha.text = postModel.fecha
        Glide.with(postFecha.context).load(postModel.foto).into(postFoto)
    }
}