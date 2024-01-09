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
import androidx.activity.result.contract.ActivityResultContracts
import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val arreglo = BaseDatosMemoria.arregloConcesionario
    var posicionItemSeleccionado = 0
    lateinit var adaptador: ArrayAdapter<Concesionario>

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
        setContentView(R.layout.activity_list_concesionarias)

        val listView = findViewById<ListView>(R.id.lv_listviewC)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_concesionario)
        botonAnadirListView.setOnClickListener {
            //anadirConcesionario(adaptador)
            posicionItemSeleccionado = -1
            abrirActividadConParametros(CrudConcesionario::class.java)
//            adaptador.notifyDataSetChanged()
        }

        registerForContextMenu(listView)
    }

    private fun anadirConcesionario(adaptador: ArrayAdapter<Concesionario>) {
        val listaCarros: ArrayList<Carro> = arrayListOf()
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
        inflater.inflate(R.menu.menuconcesionario, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_c -> {
//                mostrarSnackbar("${posicionItemSeleccionado}")
                abrirActividadConParametros(CrudConcesionario::class.java)
                return true
            }

            R.id.mi_eliminar_c -> {
                mostrarSnackbar("Concesionario ${arreglo[posicionItemSeleccionado].nombre} eliminado")
                // Eliminar completamente
                arreglo.removeAt(posicionItemSeleccionado)
                //arreglo[posicionItemSeleccionado].isOpen = false
                adaptador.notifyDataSetChanged()
                return true
            }

            R.id.mi_carros -> {
                //irActividad(ListViewCarros::class.java)
                abrirActividadConParametros(ListViewCarros::class.java)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_listviewC),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("posicion", posicionItemSeleccionado)

        callbackContenido.launch(intentExplicito)
    }

}