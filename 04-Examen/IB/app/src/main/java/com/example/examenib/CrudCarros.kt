package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenib.models.Carro

class CrudCarros : AppCompatActivity() {

    val arreglo = BaseDatosMemoria.arregloConcesionario
    var listaCarro = arrayListOf<Carro>()
    var posicionArreglo = -1
    var posicionItemSeleccionado = -1
    var marca: String = ""
    var modelo: String = ""
    var year: String = ""
    var precio: String = ""
    var estado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_carros)

        posicionArreglo = intent.getIntExtra("posicionArreglo", -1)
        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        listaCarro = arreglo[posicionArreglo].listaCarros

        if (posicionItemSeleccionado != -1) {
            val inputMarca = findViewById<EditText>(R.id.input_marca)
            val inputModelo = findViewById<EditText>(R.id.input_modelo)
            val inputYear = findViewById<EditText>(R.id.input_year)
            val inputPrecio = findViewById<EditText>(R.id.input_precio)
            val inputEstado = findViewById<EditText>(R.id.input_estado)

            inputMarca.setText(listaCarro[posicionItemSeleccionado].marca)
            inputModelo.setText(listaCarro[posicionItemSeleccionado].modelo)
            inputYear.setText(listaCarro[posicionItemSeleccionado].year.toString())
            inputPrecio.setText(listaCarro[posicionItemSeleccionado].precio.toString())
            inputEstado.setText(listaCarro[posicionItemSeleccionado].estado)

        }

        val botonCrear = findViewById<Button>(R.id.btn_crearCarro)
        if (posicionItemSeleccionado == -1) {
            botonCrear.setOnClickListener {
                marca = findViewById<EditText>(R.id.input_marca).text.toString()
                modelo = findViewById<EditText>(R.id.input_modelo).text.toString()
                year = findViewById<EditText>(R.id.input_year).text.toString()
                precio = findViewById<EditText>(R.id.input_precio).text.toString()
                estado = findViewById<EditText>(R.id.input_estado).text.toString()

                listaCarro.add(
                    Carro(
                        marca.uppercase(),
                        modelo.uppercase(),
                        year.toInt(),
                        precio.toDouble(),
                        estado.uppercase()
                    )
                )

                devolverRespuesta()
            }
        }

        val botonActualizar = findViewById<Button>(R.id.btn_actualizarCarro)
        if (posicionItemSeleccionado != -1) {
            botonActualizar.setOnClickListener {
                precio = findViewById<EditText>(R.id.input_precio).text.toString()

                listaCarro[posicionItemSeleccionado].precio = precio.toDouble()

                devolverRespuesta()
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