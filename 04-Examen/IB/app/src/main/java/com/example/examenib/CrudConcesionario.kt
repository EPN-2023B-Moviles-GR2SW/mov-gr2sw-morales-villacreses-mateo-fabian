package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario
import java.util.ArrayList

class CrudConcesionario : AppCompatActivity() {

    val arreglo = BaseDatosMemoria.arregloConcesionario
    var posicionItemSeleccionado = -1
    var nombre: String = ""
    var ubicacion: String = ""
    var empleados: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_concesionario)

        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        if (posicionItemSeleccionado != -1) {
            val inputNombre = findViewById<EditText>(R.id.input_nombre)
            val inputUbicacion = findViewById<EditText>(R.id.input_ubicacion)
            val inputEmpleados = findViewById<EditText>(R.id.input_empleados)

            inputNombre.setText(arreglo[posicionItemSeleccionado].nombre)
            inputUbicacion.setText(arreglo[posicionItemSeleccionado].ubicacion)
            inputEmpleados.setText(arreglo[posicionItemSeleccionado].numeroEmpleados.toString())
        }

        val botonCrear = findViewById<Button>(R.id.btn_crear)
        if (posicionItemSeleccionado == -1) {
            botonCrear.setOnClickListener {
                nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                ubicacion = findViewById<EditText>(R.id.input_ubicacion).text.toString()
                empleados = findViewById<EditText>(R.id.input_empleados).text.toString()

                val listaCarros: ArrayList<Carro> = arrayListOf()

                arreglo.add(
                    Concesionario(
                        nombre.uppercase(),
                        ubicacion.uppercase(),
                        true,
                        empleados.toInt(),
                        listaCarros
                    )
                )

                devolverRespuesta()
            }
        }


        val botonActualizar = findViewById<Button>(R.id.btn_actualizar)
        if (posicionItemSeleccionado != -1) {
            botonActualizar.setOnClickListener {
                nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                ubicacion = findViewById<EditText>(R.id.input_ubicacion).text.toString()
                empleados = findViewById<EditText>(R.id.input_empleados).text.toString()

                arreglo[posicionItemSeleccionado].nombre = nombre
                arreglo[posicionItemSeleccionado].ubicacion = ubicacion
                arreglo[posicionItemSeleccionado].numeroEmpleados = empleados.toInt()

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