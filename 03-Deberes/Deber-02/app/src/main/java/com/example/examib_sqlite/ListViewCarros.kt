package com.example.examib_sqlite

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.examib_sqlite.db.BaseDatos
import com.example.examib_sqlite.db.SqliteHelperCarro
import com.example.examib_sqlite.models.Carro
import com.google.android.material.snackbar.Snackbar

class ListViewCarros : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Carro>
    private val dbHelperCarro = SqliteHelperCarro(this)
    var posicionArreglo = 0
    var posicionItemSeleccionado = 0
    var listaCarro = mutableListOf<Carro>()

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                if (result.data != null) {
                    // Actualiza el adaptador cuando se regresa de CrudCarros
                    adaptador.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_carros)

        posicionArreglo = intent.getIntExtra("posicion", -1)

        val txtConcesionario = findViewById<TextView>(R.id.txt_concesionario)
        txtConcesionario.text = "Concesionario: ${BaseDatos.tablaConcesionario?.obtenerTodosConcesionarios()?.get(posicionArreglo)?.nombre}"

        listaCarro = dbHelperCarro.obtenerTodosCarros(BaseDatos.tablaConcesionario?.obtenerTodosConcesionarios()?.get(posicionArreglo)?.id ?: -1)
            .toMutableList()
        val listView = findViewById<ListView>(R.id.lv_list_carros)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaCarro
        )
        listView.adapter = adaptador

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_carro)
        botonAnadirListView.setOnClickListener {
            abrirActividadConParametros(CrudCarros::class.java)
        }

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menucarros, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        adaptador.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_c -> {
                abrirActividadConParametros(CrudCarros::class.java)
                return true
            }
            R.id.mi_eliminar_c -> {
                dbHelperCarro.eliminarCarro(
                    dbHelperCarro.obtenerTodosCarros(
                        BaseDatos.tablaConcesionario?.obtenerTodosConcesionarios()?.get(posicionArreglo)?.id ?: -1
                    )[posicionItemSeleccionado].id
                )
                mostrarSnackbar("Carro eliminado")
                adaptador.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(findViewById(R.id.lv_list_carros),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicion", posicionItemSeleccionado)
        intentExplicito.putExtra("posicionArreglo", posicionArreglo)

        callbackContenido.launch(intentExplicito)
    }
}