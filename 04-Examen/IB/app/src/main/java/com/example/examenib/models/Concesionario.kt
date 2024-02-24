package com.example.examenib.models

data class Concesionario(
    var nombre: String?,
    var ubicacion: String?,
    var isOpen: Boolean?,
    var numeroEmpleados: Int?,
    val listaCarros: MutableList<Carro>?
) {
    constructor() : this(null, null, null, null, null)
    override fun toString(): String {
        return "Nombre: ${nombre?.uppercase()} - Ubicacion: ${ubicacion?.uppercase()} \nNo.Empleados: ${numeroEmpleados}"
    }
}