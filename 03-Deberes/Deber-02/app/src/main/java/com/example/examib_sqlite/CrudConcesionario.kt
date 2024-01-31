package com.example.examib_sqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.examib_sqlite.db.BaseDatos
import com.example.examib_sqlite.models.Concesionario

class CrudConcesionario : AppCompatActivity() {

    var posicionItemSeleccionado = -1
    var nombre: String = ""
    var ubicacion: String = ""
    var empleados: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_concesionario)

        posicionItemSeleccionado = intent.getIntExtra("posicion", -1)

        // Obtén la instancia de la base de datos desde BaseDatos
        val dbHelperConcesionario = BaseDatos.tablaConcesionario

        if (posicionItemSeleccionado != -1) {
            val inputNombre = findViewById<EditText>(R.id.input_nombre)
            val inputUbicacion = findViewById<EditText>(R.id.input_ubicacion)
            val inputEmpleados = findViewById<EditText>(R.id.input_empleados)

            inputNombre.setText(dbHelperConcesionario?.obtenerTodosConcesionarios()?.get(posicionItemSeleccionado)?.nombre)
            inputUbicacion.setText(dbHelperConcesionario?.obtenerTodosConcesionarios()?.get(posicionItemSeleccionado)?.ubicacion)
            inputEmpleados.setText(dbHelperConcesionario?.obtenerTodosConcesionarios()?.get(posicionItemSeleccionado)?.numeroEmpleados.toString())
        }

        val botonCrear = findViewById<Button>(R.id.btn_crear)
        if (posicionItemSeleccionado == -1) {
            botonCrear.setOnClickListener {
                nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                ubicacion = findViewById<EditText>(R.id.input_ubicacion).text.toString()
                empleados = findViewById<EditText>(R.id.input_empleados).text.toString()

                // Inserta el nuevo concesionario en la base de datos
                dbHelperConcesionario?.insertarConcesionario(nombre, ubicacion, true, empleados.toInt())

                // Cierra la conexión a la base de datos
                dbHelperConcesionario?.close()

                // Devuelve la respuesta
                devolverRespuesta()
            }
        }

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar)
        if (posicionItemSeleccionado != -1) {
            botonActualizar.setOnClickListener {
                nombre = findViewById<EditText>(R.id.input_nombre).text.toString()
                ubicacion = findViewById<EditText>(R.id.input_ubicacion).text.toString()
                empleados = findViewById<EditText>(R.id.input_empleados).text.toString()

                // Actualiza el concesionario en la base de datos
                dbHelperConcesionario?.actualizarConcesionario(
                    nombre,
                    ubicacion,
                    true,
                    empleados.toInt(),
                    dbHelperConcesionario.obtenerTodosConcesionarios()?.get(posicionItemSeleccionado)?.id ?: 0
                )

                // Cierra la conexión a la base de datos
                dbHelperConcesionario?.close()

                // Devuelve la respuesta
                devolverRespuesta()
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
}
