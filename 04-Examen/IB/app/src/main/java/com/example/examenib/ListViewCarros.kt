package com.example.examenib

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
import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario
import com.google.android.material.snackbar.Snackbar

class ListViewCarros : AppCompatActivity() {
    val arreglo = BaseDatosMemoria.arregloConcesionario
    var posicionArreglo = 0
    var posicionItemSeleccionado = 0
    var listaCarro = arrayListOf<Carro>()
    lateinit var adaptador: ArrayAdapter<Carro>

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                if (result.data != null) {
                    // logica negocio
                    val data = result.data
                    adaptador.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_carros)

        posicionArreglo = intent.getIntExtra("posicion", -1)

        val txtConcesionario = findViewById<TextView>(R.id.txt_concesionario)
        txtConcesionario.text = "Concesionario: ${arreglo[posicionArreglo].nombre}"

        listaCarro = arreglo[posicionArreglo].listaCarros
        val listView = findViewById<ListView>(R.id.lv_list_carros)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaCarro
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_carro)
        botonAnadirListView.setOnClickListener {
//            anadirCarro(adaptador)
            posicionItemSeleccionado = -1
            abrirActividadConParametros(CrudCarros::class.java)
        }

        registerForContextMenu(listView)
    }

    private fun anadirCarro(adaptador: ArrayAdapter<Carro>) {
        listaCarro.add(
            Carro(
                "MARCA",
                "MODELO",
                2023,
                20000.0,
                "NUEVO"
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
                //mostrarSnackbar("${posicionItemSeleccionado}")
//                listaCarro[posicionItemSeleccionado].precio = 29999.99
//                adaptador.notifyDataSetChanged()
                abrirActividadConParametros(CrudCarros::class.java)
                return true
            }
            R.id.mi_eliminar_c -> {
                mostrarSnackbar("Carro eliminado")
                listaCarro.removeAt(posicionItemSeleccionado)
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