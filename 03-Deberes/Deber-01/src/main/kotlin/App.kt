import controllers.Concesionarios
import models.Carro
import models.Concesionario
import java.io.File
import java.time.LocalDate
import java.time.Year
import java.util.*

fun main() {
    val rutaArchivo = "src/main/kotlin/files/concesionarios.json"
    val archivoConcesionarios = File(rutaArchivo)
    val gestorConcesionarios = Concesionarios(archivoConcesionarios)
    val scanner = Scanner(System.`in`)

    while (true) {
        println("\nBienvenido a la Gestión de Concesionarios")
        println("1. Ver todos los concesionarios")
        println("2. Agregar un nuevo concesionario")
        println("3. Actualizar un concesionario")
        println("4. Eliminar un concesionario")
        println("5. Agregar Carro a un Concesionario")
        println("6. Mostrar Carros de un Concesionario")
        println("7. Actualizar Carro de un Concesionario")
        println("8. Eliminar Carro de un Concesionario")
        println("9. Salir")
        print("Por favor, ingrese el número de la opción que desea realizar: ")

        when (scanner.nextInt()) {
            1 -> {
                val concesionarios = gestorConcesionarios.leerConcesionarios()
                println("\nConcesionarios disponibles:")
                concesionarios.forEach {
                    if (it.isOpen) {
                        println(it)
                    }
                }
                pausar()
            }

            2 -> {
                println("\nIngrese el nombre del nuevo concesionario:")
                val nombre = scanner.next()
                println("Ingrese la ubicación del nuevo concesionario:")
                val ubicacion = scanner.next()
                println("Ingrese el número de empleados del nuevo concesionario:")
                val numeroEmpleados = scanner.nextInt()

                // Crear lista vacía de carros para el nuevo concesionario
                val listaCarros: MutableList<Carro> = mutableListOf()

                val nuevoConcesionario =
                    Concesionario(nombre.uppercase(), ubicacion.uppercase(), true, numeroEmpleados, listaCarros)
                gestorConcesionarios.crearConcesionario(nuevoConcesionario)
                println("¡Concesionario agregado correctamente!")
                pausar()
            }

            3 -> {
                println("\nIngrese el nombre del concesionario que desea actualizar:")
                val nombreActualizar = scanner.next()
                val nombreUpperCase = nombreActualizar.uppercase()

                val concesionarioExistente =
                    gestorConcesionarios.leerConcesionarios().find { it.nombre == nombreUpperCase }

                if (concesionarioExistente != null) {
                    if (!concesionarioExistente.isOpen) return
                    println("Ingrese la nueva ubicación del concesionario:")
                    val nuevaUbicacion = scanner.next()
                    println("Ingrese el nuevo número de empleados del concesionario:")
                    val nuevoNumeroEmpleados = scanner.nextInt()

                    val concesionarioActualizado = Concesionario(
                        concesionarioExistente.nombre,
                        nuevaUbicacion.uppercase(),
                        concesionarioExistente.isOpen,
                        nuevoNumeroEmpleados,
                        concesionarioExistente.listaCarros
                    )

                    gestorConcesionarios.actualizarConcesionario(nombreUpperCase, concesionarioActualizado)
                    println("¡Concesionario actualizado correctamente!")
                    pausar()
                } else {
                    println("El concesionario no existe.")
                    pausar()
                }
            }

            4 -> {
                println("\nIngrese el nombre del concesionario que desea eliminar:")
                val nombreActualizar = scanner.next()
                val nombreUpperCase = nombreActualizar.uppercase()

                val concesionarioExistente =
                    gestorConcesionarios.leerConcesionarios().find { it.nombre == nombreUpperCase }

                if (concesionarioExistente != null) {
                    if (!concesionarioExistente.isOpen) return

                    val concesionarioActualizado = Concesionario(
                        concesionarioExistente.nombre,
                        concesionarioExistente.ubicacion,
                        false,
                        concesionarioExistente.numeroEmpleados,
                        concesionarioExistente.listaCarros
                    )

                    gestorConcesionarios.actualizarConcesionario(nombreUpperCase, concesionarioActualizado)
                    println("¡Concesionario eliminado correctamente!")
                    pausar()
                } else {
                    println("El concesionario no existe.")
                    pausar()
                }
            }

            5 -> { // Agregar Carro a un Concesionario
                println("\nIngrese el nombre del concesionario al que desea agregar un carro:")
                val nombreConcesionario = scanner.next()
                val nombreUpperCase = nombreConcesionario.uppercase()

                val concesionario = gestorConcesionarios.leerConcesionarios()
                    .find { it.nombre == nombreUpperCase }

                if (concesionario != null && concesionario.isOpen) {
                    println("\nIngrese la marca del carro:")
                    val marca = scanner.next()
                    println("Ingrese el modelo del carro:")
                    val modelo = scanner.next()
                    println("Ingrese el año del carro:")
                    val year = scanner.nextInt()
                    println("Ingrese el precio del carro:")
                    val precio = scanner.nextDouble()
                    println("Ingrese el estado del carro (Nuevo - Usado):")
                    val estado = scanner.next()

                    val nuevoCarro = Carro(marca.uppercase(), modelo.uppercase(), year, precio, estado.uppercase())
                    concesionario.agregarCarro(nuevoCarro)
                    gestorConcesionarios.actualizarConcesionario(nombreUpperCase, concesionario)
                    println("¡Carro agregado al concesionario correctamente!")
                    pausar()
                } else {
                    println("El concesionario no existe.")
                    pausar()
                }
            }

            6 -> { // Mostrar Carros de un Concesionario
                println("\nIngrese el nombre del concesionario del que desea ver los carros:")
                val nombreConcesionario = scanner.next()
                val nombreUpperCase = nombreConcesionario.uppercase()

                val concesionario = gestorConcesionarios.leerConcesionarios()
                    .find { it.nombre == nombreUpperCase }

                if (concesionario != null && concesionario.isOpen) {
                    val carrosConcesionario = concesionario.obtenerCarros()
                    println("\nCarros del concesionario $nombreUpperCase:")
                    carrosConcesionario.forEach { println(it) }
                    pausar()
                } else {
                    println("El concesionario no existe.")
                    pausar()
                }
            }

            7 -> { // Actualizar Carro de un Concesionario
                println("\nIngrese el nombre del concesionario al que pertenece el carro a actualizar:")
                val nombreConcesionario = scanner.next()
                val nombreUpperCase = nombreConcesionario.uppercase()

                val concesionario = gestorConcesionarios.leerConcesionarios()
                    .find { it.nombre == nombreUpperCase }

                if (concesionario != null && concesionario.isOpen) {
                    println("\nIngrese el modelo del carro que desea actualizar:")
                    val modeloCarro = scanner.next()
                    val modeloCarroUpperCase = modeloCarro.uppercase()

                    val carrosConcesionario = concesionario.obtenerCarros()
                    val carro = carrosConcesionario.find { it.modelo == modeloCarroUpperCase }
                    if (carro != null) {
                        println("Ingrese el nuevo precio del carro:")
                        val precio = scanner.nextDouble()

                        val carroActualizado = Carro(carro.marca, carro.modelo, carro.year, precio, carro.estado)
                        concesionario.actualizarCarro(modeloCarroUpperCase, carroActualizado)
                        gestorConcesionarios.actualizarConcesionario(nombreUpperCase, concesionario)
                        println("¡Carro actualizado correctamente!")
                        pausar()
                    } else {
                        println("Índice de carro inválido.")
                        pausar()
                    }
                } else {
                    println("El concesionario no existe.")
                    pausar()
                }
            }

            8 -> { // Eliminar Carro de un Concesionario
                println("\nIngrese el nombre del concesionario al que pertenece el carro a eliminar:")
                val nombreConcesionario = scanner.next()
                val nombreUpperCase = nombreConcesionario.uppercase()

                val concesionario = gestorConcesionarios.leerConcesionarios()
                    .find { it.nombre == nombreUpperCase }

                if (concesionario != null && concesionario.isOpen) {
                    println("\nIngrese el modelo del carro que desea eliminar:")
                    val modeloCarro = scanner.next()
                    val modeloCarroUpperCase = modeloCarro.uppercase()

                    val carrosConcesionario = concesionario.obtenerCarros()
                    val carro = carrosConcesionario.find { it.modelo == modeloCarroUpperCase }
                    if (carro != null) {
                        concesionario.eliminarCarro(modeloCarroUpperCase)
                        gestorConcesionarios.actualizarConcesionario(nombreUpperCase, concesionario)
                        println("¡Carro eliminado correctamente!")
                        pausar()
                    } else {
                        println("Índice de carro inválido.")
                        pausar()
                    }
                } else {
                    println("El concesionario no existe.")
                    pausar()
                }
            }

            9 -> {
                println("Saliendo de la aplicación...")
                return
            }

            else -> {
                println("Opción inválida, por favor ingrese un número válido.")
                pausar()
            }
        }
    }
}

fun pausar() {
    println("\nPresiona Enter para continuar...")
    readLine()
}
