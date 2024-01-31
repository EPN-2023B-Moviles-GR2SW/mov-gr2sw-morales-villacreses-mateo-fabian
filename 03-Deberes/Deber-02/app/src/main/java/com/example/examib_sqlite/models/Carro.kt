package com.example.examib_sqlite.models

data class Carro(
    val id: Int,
    val marca: String,
    val modelo: String,
    val year: Int,
    var precio: Double,
    val estado: String,
    val idConcesionario: Int
){
    override fun toString(): String {
        return "Marca: ${marca} \nModelo: ${modelo} \nPrecio: ${precio}"
    }
}
