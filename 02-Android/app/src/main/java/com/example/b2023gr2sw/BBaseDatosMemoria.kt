package com.example.b2023gr2sw

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrendador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrendador.add(BEntrenador(1, "Adrian", "a@a.com"))
            arregloBEntrendador.add(BEntrenador(2, "Vicente", "b@b.com"))
            arregloBEntrendador.add(BEntrenador(3, "Carolina", "c@c.com"))
        }
    }
}