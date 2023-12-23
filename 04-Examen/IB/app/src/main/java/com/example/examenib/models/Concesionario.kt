package com.example.examenib.models

data class Concesionario(
    val nombre: String,
    val ubicacion: String,
    val isOpen: Boolean,
    val numeroEmpleados: Int,
    val listaCarros: MutableList<Carro>
) {
    fun agregarCarro(carro: Carro){
        listaCarros.add(carro)
    }

    override fun toString(): String {
        return "Nombre: ${nombre} - Ubicacion: ${ubicacion}"
    }
}