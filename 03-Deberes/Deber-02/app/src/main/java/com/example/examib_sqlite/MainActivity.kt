package com.example.examib_sqlite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.examib_sqlite.db.BaseDatos
import com.example.examib_sqlite.models.Concesionario
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var adaptador: ArrayAdapter<Concesionario>
    private val dbHelperConcesionario = BaseDatos.tablaConcesionario

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Actualiza el adaptador solo si se regresa de CrudConcesionario con éxito
                adaptador.clear()
                adaptador.addAll(dbHelperConcesionario?.obtenerTodosConcesionarios() ?: mutableListOf())
                adaptador.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.lv_listviewC)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            dbHelperConcesionario?.obtenerTodosConcesionarios() ?: mutableListOf()
        )
        listView.adapter = adaptador

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_concesionario)
        botonAnadirListView.setOnClickListener {
            abrirActividadConParametros(CrudConcesionario::class.java)
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
        inflater.inflate(R.menu.menuconcesionario, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        adaptador.notifyDataSetChanged()
        mostrarSnackbar("Posición seleccionada: $posicion")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position

        when (item.itemId) {
            R.id.mi_editar_c -> {
                abrirActividadConParametros(CrudConcesionario::class.java, posicion)
                return true
            }

            R.id.mi_eliminar_c -> {
                dbHelperConcesionario?.eliminarConcesionario(
                    dbHelperConcesionario?.obtenerTodosConcesionarios()?.get(posicion)?.id ?: 0
                )
                adaptador.clear()
                adaptador.addAll(dbHelperConcesionario?.obtenerTodosConcesionarios() ?: mutableListOf())
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Concesionario eliminado")
                return true
            }

            R.id.mi_carros -> {
                abrirActividadConParametros(ListViewCarros::class.java)
                return true
            }

            else -> return super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_listviewC),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun abrirActividadConParametros(clase: Class<*>, posicion: Int? = null) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicion", posicion ?: -1)
        callbackContenido.launch(intentExplicito)
    }
}
