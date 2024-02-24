package com.example.examenib

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.FirebaseFirestore

class ListViewCarros : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val concesionarioCollection = db.collection("concesionarios")

    private var nameF = ""
    private var indexSelectedItem = 0
    private var concesionarioPosition = -1
    private var carrosList = arrayListOf<Carro>()
    private lateinit var adaptador: ArrayAdapter<Carro>

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                if (result.data != null) {
                    // logica negocio
                    updateCarList()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_carros)

        val concesionarioName = intent.getStringExtra("ConcesionarioName").toString()
        nameF = intent.getStringExtra("nameF").toString()
        concesionarioPosition = intent.getIntExtra("ConsecionarioPosition", -1)

        val txtConcesionario = findViewById<TextView>(R.id.txt_concesionario)
        if (concesionarioName != null) {
            txtConcesionario.text = "Concesionario: ${concesionarioName}"
        }

        val listView = findViewById<ListView>(R.id.lv_list_carros)
        registerForContextMenu(listView)

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_carro)
        botonAnadirListView.setOnClickListener {
            indexSelectedItem = -1
            abrirActividadConParametros(CrudCarros::class.java)
        }

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            carrosList
        )

        listView.adapter = adaptador
        updateCarList()
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
        indexSelectedItem = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_c -> {
                abrirActividadConParametros(CrudCarros::class.java)
                return true
            }
            R.id.mi_eliminar_c -> {
                val deletedCar = carrosList.removeAt(indexSelectedItem)
                adaptador.notifyDataSetChanged()
                deleteCarFromFirestore(deletedCar)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteCarFromFirestore(car: Carro){
        concesionarioCollection.document(nameF)
            .update("listaCarros", carrosList)
            .addOnSuccessListener {
                mostrarSnackbar("Carro eliminado con exito")
                adaptador.clear()
                adaptador.addAll(carrosList)
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                mostrarSnackbar("Error al eliminar el carro")
                carrosList.add(indexSelectedItem, car)
                adaptador.notifyDataSetChanged()
            }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(findViewById(R.id.lv_list_carros),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun abrirActividadConParametros(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("position", indexSelectedItem)
        intentExplicito.putExtra("concesionarioPosition", concesionarioPosition)
        intentExplicito.putExtra("nameF", nameF)
        if (indexSelectedItem != -1){
            val selectedCar = carrosList[indexSelectedItem]
            intentExplicito.putExtra("carMarca", selectedCar.marca)
            intentExplicito.putExtra("carModelo", selectedCar.modelo)
            intentExplicito.putExtra("carYear", selectedCar.year)
            intentExplicito.putExtra("carPrecio", selectedCar.precio)
            intentExplicito.putExtra("carEstado", selectedCar.estado)
            intentExplicito.putExtra("editar", 0)
        }

        callbackContenido.launch(intentExplicito)
    }

    private fun updateCarList() {
        concesionarioCollection.document(nameF).get()
            .addOnSuccessListener { documentSnapshot ->
                val concesionario = documentSnapshot.toObject(Concesionario::class.java)
                if (concesionario != null) {
                    carrosList = (concesionario.listaCarros as ArrayList<Carro>?)!!
                    adaptador.clear()
                    adaptador.addAll(carrosList)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al obtener lista carros", e)
            }
    }
}