package com.example.examenib.models

data class Carro(
    val marca: String?,
    val modelo: String?,
    val year: Int?,
    var precio: Double?,
    val estado: String?
){
    constructor() : this(null, null, null, null, null)
    override fun toString(): String {
        return "Marca: ${marca} \nModelo: ${modelo} \nPrecio: ${precio}"
    }
}