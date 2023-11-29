package controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import models.Concesionario
import java.io.File

class Concesionarios(private val archivo: File) {
    private val objectMapper = jacksonObjectMapper()
    private val concesionarios: MutableList<Concesionario> = mutableListOf()

    init {
        cargarConcesionarios()
    }

    private fun cargarConcesionarios() {
        if (archivo.exists()) {
            val concesionariosJson = archivo.readText()
            concesionarios.addAll(objectMapper.readValue<List<Concesionario>>(concesionariosJson))
        }
    }

    fun crearConcesionario(concesionario: Concesionario) {
        concesionarios.add(concesionario)
        guardarConcesionarios()
    }

    fun leerConcesionarios(): List<Concesionario>{
        return concesionarios.toList()
    }

    fun actualizarConcesionario(nombre: String, concesionarioActualizado: Concesionario) {
        val concesionarioIndex = concesionarios.indexOfFirst { it.nombre == nombre }
        if (concesionarioIndex != -1) {
            concesionarios[concesionarioIndex] = concesionarioActualizado
            guardarConcesionarios()
        } else {
            println("El concesionario no existe.")
        }
    }

    private fun guardarConcesionarios() {
        val concesionariosJson = objectMapper.writeValueAsString(concesionarios)
        archivo.writeText(concesionariosJson)
    }

}