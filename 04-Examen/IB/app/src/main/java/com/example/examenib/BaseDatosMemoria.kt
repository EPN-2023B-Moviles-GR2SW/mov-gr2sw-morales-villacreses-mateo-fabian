package com.example.examenib

import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario

class BaseDatosMemoria {
    companion object{
        val arregloConcesionario = arrayListOf<Concesionario>()
        val listaCarros: MutableList<Carro> = mutableListOf()
        init {
            arregloConcesionario.add(Concesionario("TOYOTA", "AMBATO", true, 15, listaCarros))
            arregloConcesionario.add(Concesionario("FORD", "QUITO", true, 20, listaCarros))
            arregloConcesionario.add(Concesionario("TESLA", "GUAYAQUIL", true, 15, listaCarros))
        }
    }
}