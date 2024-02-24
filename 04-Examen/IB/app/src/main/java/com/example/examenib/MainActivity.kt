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
import androidx.activity.result.contract.ActivityResultContracts
import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val concesionariosCollection = db.collection("concesionarios")
    private val documentNames = ArrayList<String>()

    private var indexSelectedItem = 0
    private var concesionarioList = ArrayList<Concesionario>()
    lateinit var adaptador: ArrayAdapter<Concesionario>

    val callbackContenido =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === Activity.RESULT_OK) {
                if (result.data != null) {
                    // logica negocio
                    val data = result.data
                    val position = data?.getIntExtra("position", -1)
                    if (position != null && position != -1) {
                        adaptador.notifyDataSetChanged()
                    }
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
            concesionarioList
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        loadConcesionarios()

        concesionariosCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val documentId = document.id
                    documentNames.add(documentId)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al obtener los documentos", exception)
            }

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedConcesionario = concesionarioList[position]
            val explicitIntent = Intent(this, ListViewCarros::class.java)
            explicitIntent.putExtra("ConcesionarioName", selectedConcesionario.nombre)
            explicitIntent.putExtra("nameF", documentNames[indexSelectedItem])
            callbackContenido.launch(explicitIntent)
        }

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_concesionario)
        botonAnadirListView.setOnClickListener {
            indexSelectedItem = -1
            abrirActividadConParametros(CrudConcesionario::class.java)
        }

        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        loadConcesionarios()
    }

    private fun loadConcesionarios() {
        concesionariosCollection.get()
            .addOnSuccessListener { result ->
                concesionarioList.clear()
                for (document in result) {
                    val concesionario = document.toObject(Concesionario::class.java)
                    concesionarioList.add(concesionario)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al obtener los documentos", exception)
            }
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
        indexSelectedItem = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_c -> {
                abrirActividadConParametros(CrudConcesionario::class.java)
                return true
            }

            R.id.mi_eliminar_c -> {
                val deletedConcesionario = concesionarioList[indexSelectedItem]
                deletedConcesionarioFromFirestore(deletedConcesionario)
                return true
            }

            R.id.mi_carros -> {
                abrirActividadConParametros(ListViewCarros::class.java)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun deletedConcesionarioFromFirestore(concesionario: Concesionario) {
        if (concesionario.nombre != null) {
            concesionariosCollection.document(documentNames[indexSelectedItem])
                .delete()
                .addOnSuccessListener {
                    concesionarioList.remove(concesionario)
                    adaptador.notifyDataSetChanged()
                    mostrarSnackbar("Concesionario eliminado correctamente")
                    if (indexSelectedItem >= concesionarioList.size) {
                        indexSelectedItem = concesionarioList.size - 1
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error al eliminar el distribuidor", e)
                    mostrarSnackbar("No se pudo eliminar el concesionario")
                }
        } else {
            Log.e(TAG, "No existe concesionario")
            mostrarSnackbar("No existe concesionario")
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
        intentExplicito.putExtra("posicion", indexSelectedItem)
        if (indexSelectedItem != -1) {
            val selectedConcesionario = concesionarioList[indexSelectedItem]
            intentExplicito.putExtra("concesionarioNombre", selectedConcesionario.nombre)
            intentExplicito.putExtra("concesionarioUbicacion", selectedConcesionario.ubicacion)
            intentExplicito.putExtra("concesionarioEstado", selectedConcesionario.isOpen)
            intentExplicito.putExtra(
                "concesionarioEmpleados",
                selectedConcesionario.numeroEmpleados
            )
            intentExplicito.putExtra("nameF", documentNames[indexSelectedItem])
        }

        callbackContenido.launch(intentExplicito)
    }

}