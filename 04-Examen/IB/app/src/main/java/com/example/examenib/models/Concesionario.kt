package com.example.examenib.models

import java.util.ArrayList

data class Concesionario(
    var nombre: String,
    var ubicacion: String,
    var isOpen: Boolean,
    var numeroEmpleados: Int,
    val listaCarros: ArrayList<Carro>
) {
    override fun toString(): String {
        return "Nombre: ${nombre.uppercase()} - Ubicacion: ${ubicacion.uppercase()} \nNo.Empleados: ${numeroEmpleados}"
    }
}