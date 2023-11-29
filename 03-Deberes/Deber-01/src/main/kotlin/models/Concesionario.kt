package models

data class Concesionario(
    val nombre: String,
    val ubicacion: String,
    val isOpen: Boolean,
    val numeroEmpleados: Int,
    val listaCarros: MutableList<Carro>
) {
    fun agregarCarro(carro: Carro) {
        listaCarros.add(carro)
    }

    fun obtenerCarros(): List<Carro> {
        return listaCarros.toList()
    }

    fun actualizarCarro(modelo: String, carroActualizado: Carro) {
        val indicesToUpdate = mutableListOf<Int>()

        // Encuentra los índices de los elementos que cumplen la condición
        for (index in listaCarros.indices) {
            val carro = listaCarros[index]
            if (carro.modelo == modelo) {
                indicesToUpdate.add(index)
            }
        }

        // Actualiza los elementos de la lista según los índices encontrados
        for (index in indicesToUpdate) {
            listaCarros[index] = carroActualizado
        }
    }

    fun eliminarCarro(modelo: String) {
        val carrosAEliminar = mutableListOf<Carro>()

        listaCarros.forEach { carro ->
            if (carro.modelo == modelo) {
                carrosAEliminar.add(carro)
            }
        }

        listaCarros.removeAll(carrosAEliminar)
    }
}
