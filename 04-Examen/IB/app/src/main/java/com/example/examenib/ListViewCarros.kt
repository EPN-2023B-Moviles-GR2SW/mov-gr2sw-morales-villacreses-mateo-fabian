package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario
import com.google.android.material.snackbar.Snackbar

class ListViewCarros : AppCompatActivity() {
    val arreglo = BaseDatosMemoria.arregloConcesionario
    var posicionItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_carros)
        val listView = findViewById<ListView>(R.id.lv_list_carros)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_carro)
        botonAnadirListView.setOnClickListener {
            anadirCarro(adaptador)
        }

        registerForContextMenu(listView)
    }

    private fun anadirCarro(adaptador: ArrayAdapter<Concesionario>) {
        val listaCarros: MutableList<Carro> = mutableListOf()
        arreglo.add(
            Concesionario(
                "HONDA",
                "LOJA",
                true,
                20,
                listaCarros
            )
        )
        adaptador.notifyDataSetChanged()
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
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_c -> {
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true
            }
            R.id.mi_eliminar_c -> {
                mostrarSnackbar("${posicionItemSeleccionado}")
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
}