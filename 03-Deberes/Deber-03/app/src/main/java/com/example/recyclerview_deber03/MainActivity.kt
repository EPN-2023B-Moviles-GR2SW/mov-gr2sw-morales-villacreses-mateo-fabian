package com.example.recyclerview_deber03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_deber03.adapter.UserAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserDatosMemoria.usersList
        setContentView(R.layout.activity_main)
        initRecyclerView()

        val botonMensajes = findViewById<Button>(R.id.btn_mensajes)
        botonMensajes.setOnClickListener {
            irActividad(ChatActivity::class.java)
        }
    }

    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_mensajes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserAdapter(UserDatosMemoria.usersList)
    }

    private fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}