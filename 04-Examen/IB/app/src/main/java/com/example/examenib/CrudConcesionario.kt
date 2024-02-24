package com.example.examenib

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario
import com.google.firebase.firestore.FirebaseFirestore

class CrudConcesionario : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val concesionarioCollection = db.collection("concesionarios")
    var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_concesionario)

        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)
        val concesionarioNombre = intent.getStringExtra("concesionarioNombre")
        val concesionarioUbicacion = intent.getStringExtra("concesionarioUbicacion")
        val concesionarioEstado = intent.getBooleanExtra("concesionarioEstado", true)
        val concesionarioEmpleados = intent.getIntExtra("concesionarioEmpleados", 0)

        findViewById<EditText>(R.id.input_nombre).setText(concesionarioNombre)
        findViewById<EditText>(R.id.input_ubicacion).setText(concesionarioUbicacion)
        findViewById<EditText>(R.id.input_empleados).setText(concesionarioEmpleados.toString())

        val botonCrear = findViewById<Button>(R.id.btn_crear)
        botonCrear.setOnClickListener {
            val name = findViewById<EditText>(R.id.input_nombre).text.toString()
            val ubicacion = findViewById<EditText>(R.id.input_ubicacion).text.toString()
            val estado = true
            val numeroEmpleados = findViewById<EditText>(R.id.input_empleados).text.toString()
            val listaCarros = arrayListOf<Carro>()

            val concesionario =
                Concesionario(name, ubicacion, estado, numeroEmpleados.toInt(), listaCarros)

            if (posicionItemSeleccionado == -1) {
                concesionarioCollection.add(concesionario)
                    .addOnSuccessListener {
                        devolverRespuesta()
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al agregar un nuevo concesionario", e)
                    }
            } else {
                val nameF = intent.getStringExtra("nameF")
                concesionarioCollection.document(nameF!!)
                    .set(concesionario)
                    .addOnSuccessListener {
                        devolverRespuesta()
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al actualizar el distribuidor", e)
                    }
            }
        }
    }

    private fun devolverRespuesta() {
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("posicion", posicionItemSeleccionado)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}