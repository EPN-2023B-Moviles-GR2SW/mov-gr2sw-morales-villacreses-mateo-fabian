package com.example.examib_sqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.examib_sqlite.db.BaseDatos
import com.example.examib_sqlite.models.Carro

class CrudCarros : AppCompatActivity() {

    var listaCarro = arrayListOf<Carro>()
    var posicionArreglo = -1
    var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_carros)

        posicionArreglo = intent.getIntExtra("posicionArreglo", -1)
        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        // Obtén las instancias de las bases de datos desde BaseDatos
        val dbHelperConcesionario = BaseDatos.tablaConcesionario
        val dbHelperCarro = BaseDatos.tablaCarro

        // Obtiene datos de la base de datos en lugar de la memoria
        listaCarro = (dbHelperCarro?.obtenerTodosCarros(posicionArreglo) ?: ArrayList()) as ArrayList<Carro>

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
                // Obtiene los datos desde las vistas
                val marca = findViewById<EditText>(R.id.input_marca).text.toString()
                val modelo = findViewById<EditText>(R.id.input_modelo).text.toString()
                val year = findViewById<EditText>(R.id.input_year).text.toString().toInt()
                val precio = findViewById<EditText>(R.id.input_precio).text.toString().toDouble()
                val estado = findViewById<EditText>(R.id.input_estado).text.toString()

                // Inserta el nuevo carro en la base de datos
                try {
                    dbHelperCarro?.insertarCarro(marca, modelo, year, precio, estado, posicionArreglo)
                    // Devuelve la respuesta
                    devolverRespuesta()
                } catch (e: Exception) {
                    // Manejar errores de inserción
                    e.printStackTrace()
                }
            }
        }

        val botonActualizar = findViewById<Button>(R.id.btn_actualizarCarro)
        if (posicionItemSeleccionado != -1) {
            botonActualizar.setOnClickListener {
                // Obtiene el nuevo precio desde la vista
                val nuevoPrecio = findViewById<EditText>(R.id.input_precio).text.toString()

                // Actualiza el precio del carro en la base de datos
                try {
                    dbHelperCarro?.actualizarCarro(
                        listaCarro[posicionItemSeleccionado].marca,
                        listaCarro[posicionItemSeleccionado].modelo,
                        listaCarro[posicionItemSeleccionado].year,
                        nuevoPrecio.toDouble(),
                        listaCarro[posicionItemSeleccionado].estado,
                        listaCarro[posicionArreglo].idConcesionario,
                        listaCarro[posicionItemSeleccionado].id
                    )

                    // Devuelve la respuesta
                    devolverRespuesta()
                } catch (e: Exception) {
                    // Manejar errores de actualización
                    e.printStackTrace()
                }
            }
        }
    }

    private fun devolverRespuesta() {
        // Devuelve la posición del item modificado
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("posicion", posicionItemSeleccionado)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }

    override fun onDestroy() {
        // Cierra la conexión a las bases de datos al destruir la actividad
        BaseDatos.tablaConcesionario?.close()
        BaseDatos.tablaCarro?.close()
        super.onDestroy()
    }
}