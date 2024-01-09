package com.example.examenib

import com.example.examenib.models.Carro
import com.example.examenib.models.Concesionario
import java.util.ArrayList

class BaseDatosMemoria {
    companion object{
        val arregloConcesionario = arrayListOf<Concesionario>()
        val listaCarros1: ArrayList<Carro> = arrayListOf()
        val listaCarros2: ArrayList<Carro> = arrayListOf()
        val listaCarros3: ArrayList<Carro> = arrayListOf()
        init {

            listaCarros1.add(Carro("TOYOTA", "CORSA", 2020, 19999.0, "NUEVO"))
            listaCarros1.add(Carro("TOYOTA", "RAV4", 2020, 28999.0, "USADO"))
            listaCarros1.add(Carro("TOYOTA", "CAMRY", 2021, 31999.0, "NUEVO"))

            listaCarros2.add(Carro("FORD", "RAPTOR", 2021, 29999.99, "USADO"))
            listaCarros2.add(Carro("FORD", "FIESTA", 2020, 18999.0, "NUEVO"))
            listaCarros2.add(Carro("FORD", "MUSTANG", 2023, 39999.0, "NUEVO"))

            listaCarros3.add(Carro("TESLA", "MODEL S", 2022, 79999.0, "NUEVO"))
            listaCarros3.add(Carro("TESLA", "MODEL 3", 2021, 59999.0, "USADO"))
            listaCarros3.add(Carro("TESLA", "MODEL X", 2023, 89999.0, "NUEVO"))

            arregloConcesionario.add(Concesionario("TOYOTA", "AMBATO", true, 15, listaCarros1))
            arregloConcesionario.add(Concesionario("FORD", "QUITO", true, 20, listaCarros2))
            arregloConcesionario.add(Concesionario("TESLA", "GUAYAQUIL", true, 15, listaCarros3))
        }
    }
}