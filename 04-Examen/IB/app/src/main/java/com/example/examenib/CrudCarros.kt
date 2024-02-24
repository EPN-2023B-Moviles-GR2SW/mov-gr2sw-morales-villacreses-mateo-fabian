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

class CrudCarros : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val concesionarioCollection = db.collection("concesionarios")
    private var nameF = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_carros)

        val selectedIndexItem = intent.getIntExtra("position", -1)
        val editar = intent.getIntExtra("editar", -1)
        val carMarca = intent.getStringExtra("carMarca")
        val carModelo = intent.getStringExtra("carModelo")
        val carYear = intent.getStringExtra("carYear")
        val carPrecio = intent.getStringExtra("carPrecio")
        val carEstado = intent.getStringExtra("carEstado")
        nameF = intent.getStringExtra("nameF").toString()

        if (editar == 0){
            findViewById<EditText>(R.id.input_marca).setText(carMarca)
            findViewById<EditText>(R.id.input_modelo).setText(carModelo)
            findViewById<EditText>(R.id.input_year).setText(carYear)
            findViewById<EditText>(R.id.input_precio).setText(carPrecio)
            findViewById<EditText>(R.id.input_estado).setText(carEstado)
        }

        val botonCrear = findViewById<Button>(R.id.btn_crearCarro)
        botonCrear.setOnClickListener {
            val marca = findViewById<EditText>(R.id.input_marca).text.toString()
            val modelo = findViewById<EditText>(R.id.input_modelo).text.toString()
            val year = findViewById<EditText>(R.id.input_year).text.toString().toInt()
            val precio = findViewById<EditText>(R.id.input_precio).text.toString().toDouble()
            val estado = findViewById<EditText>(R.id.input_estado).text.toString()
            val carList = arrayListOf<Carro>()

            val car = Carro(marca, modelo, year, precio, estado)

            carList.add(car)

            if (selectedIndexItem == -1){
                concesionarioCollection.document(nameF)
                    .update("listaCarros", carList)
                    .addOnSuccessListener {
                        devolverRespuesta(-1)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al agregar un nuevo carro", e)
                    }
            } else {
                concesionarioCollection.document(nameF)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val concesionario = documentSnapshot.toObject(Concesionario::class.java)
                        concesionario?.let { concesionario ->
                            val updateCarList = concesionario.listaCarros?.toMutableList()
                            val carIndex = updateCarList?.indexOfFirst { it.modelo == car.modelo }
                            if (carIndex != null && carIndex != -1){
                                updateCarList[carIndex] = car
                                concesionarioCollection.document(nameF)
                                    .set(concesionario.copy(listaCarros = updateCarList))
                                    .addOnSuccessListener {
                                        devolverRespuesta(selectedIndexItem)
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e(TAG, "Error al actualizar el carro", e)
                                    }
                            } else {
                                Log.e(TAG, "Carro no encontrado en la lista de concesionario")
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al obtener el concesionario", e)
                    }
            }
        }

    }

    private fun devolverRespuesta(position: Int) {
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("position", position)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }
}