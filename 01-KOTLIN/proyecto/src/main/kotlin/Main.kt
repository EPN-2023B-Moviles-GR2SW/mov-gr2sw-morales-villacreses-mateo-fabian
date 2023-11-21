import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Mateo";

    // MUTABLES (Re asignar)
    var mutable: String = "Mateo";
    mutable = "Fabian";

    println("INMUTABLE: $inmutable");
    println("MUTABLE: $mutable");

    // val > var
    // Duck Typing
    var ejemploVariable = " Mateo Morales "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo; //!error

    // Variables primitiva
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueto = if (esSoltero) "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) // Parametros nombrados

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println("Numero pi = ${Suma.pi}")
    println("2^2 = ${Suma.elevarAlCuadrado(2)}")
    println("Historial de sumas: ${Suma.historialSumas}")

    println("\n############# ARREGLOS #############")
    // Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico.contentToString()) // println(arregloEstatico) imprime la direccion de memoria

    // Arreglo Dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach{ valorActual: Int ->
        println("Valor actual: ${valorActual}")
    }
    // it (en ingles eso) significa el elemento iterado
    arregloDinamico.forEach{ println(it) }

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    // MAP -> Muta el arreglo (Cambia el arreglo)
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    // Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion TRUE o FALSE
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            // Expresion Condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }

    println(respuestaFilter)
    println(respuestaFilterDos)
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ) { // Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(
    // Ejemplo:
    // uno: Int, (Parametro (sin modificador de acceso))
    // private var uno: Int, // Propiedad Clase numeros.uno
    // var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
    // public var uno: Int,
    protected val numeroUno: Int, // Propiedad de la case protected numeros.numeroUno
    protected val numeroDos: Int, // Propiedad de la case protected numeros.numeroDos
) {
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init { // bloque constructor primario
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos // sin el "this", es lo mismo
        println("Inicializando")
    }
}


class Suma(
    // Constructor Primario Suma
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametro
) : Numeros(unoParametro, dosParametro) { // Extendiendo y mandando los parametros (super)
    init { // Bloque codigo constructor primario
        this.numeroDos
        this.numeroDos
    }

    constructor(
// Segundo constructor
        uno: Int?, // Parametros
        dos: Int, // Parametros
    ) : this(
        if (uno == null) 0 else uno,
        dos
    )

    constructor(
// Tercer constructor
        uno: Int, // Parametros
        dos: Int?, // Parametros
    ) : this(
        uno,
        if (dos == null) 0 else dos,
    )

    constructor(
// Cuarto constructor
        uno: Int?, // parametros
        dos: Int?, // parametros
    ) : this( // llamda a constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    // public por defecto, o usar private o protected
    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total)
        agregarHistorial(total)
        return total
    }

    companion object { // Atributos y Metodos "Compartidos"
        // entre las instancias
        val pi = 3.14

        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
        }
    }
}

// Void -> Unit
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}") // template strings
}


fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, //Opcion null -> nullable
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) + bonoEspecial
    }
}