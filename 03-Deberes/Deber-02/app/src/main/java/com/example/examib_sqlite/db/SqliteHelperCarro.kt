package com.example.examib_sqlite.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examib_sqlite.models.Carro

class SqliteHelperCarro(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "sqliteExam", // nombre bdd
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCarro =
            """
                CREATE TABLE IF NOT EXISTS CARRO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                marca STRING,
                modelo STRING,
                year INT,
                precio DOUBLE,
                estado STRING,
                idConcesionario INT,
                FOREIGN KEY (idConcesionario) REFERENCES CONCESIONARIO(id)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCarro)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // ("Not yet implemented")
    }

    // Función para insertar un nuevo carro
    fun insertarCarro(
        marca: String,
        modelo: String,
        year: Int,
        precio: Double,
        estado: String,
        idConcesionario: Int
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("marca", marca)
            put("modelo", modelo)
            put("year", year)
            put("precio", precio)
            put("estado", estado)
            put("idConcesionario", idConcesionario)
        }

        return try {
            db.insert("CARRO", null, valores)
        } catch (e: SQLException) {
            // Manejar errores de inserción si es necesario
            -1
        } finally {
            db.close()
        }
    }

    // Función para actualizar un carro
    fun actualizarCarro(
        marca: String,
        modelo: String,
        year: Int,
        precio: Double,
        estado: String,
        idConcesionario: Int,
        id: Int
    ): Boolean {
        val db = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("marca", marca)
            put("modelo", modelo)
            put("year", year)
            put("precio", precio)
            put("estado", estado)
            put("idConcesionario", idConcesionario)
        }

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = db.update(
            "CARRO",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )

        db.close()
        return resultadoActualizacion != -1
    }

    // Función para eliminar un carro por su ID
    fun eliminarCarro(id: Int): Boolean {
        val db = writableDatabase
        val resultadoEliminacion = db.delete("CARRO", "id = ?", arrayOf(id.toString()))
        db.close()

        return resultadoEliminacion != -1
    }

    // Función para obtener todos los carros de un concesionario
    @SuppressLint("Range")
    fun obtenerTodosCarros(idConcesionario: Int): List<Carro> {
        val carros = mutableListOf<Carro>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM CARRO WHERE idConcesionario = ?", arrayOf(idConcesionario.toString()))
        } catch (e: SQLException) {
            // Manejar errores de consulta si es necesario
            return emptyList()
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val marca = cursor.getString(cursor.getColumnIndex("marca"))
                val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                val year = cursor.getInt(cursor.getColumnIndex("year"))
                val precio = cursor.getDouble(cursor.getColumnIndex("precio"))
                val estado = cursor.getString(cursor.getColumnIndex("estado"))

                carros.add(Carro(id, marca, modelo, year, precio, estado, idConcesionario))
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()

        return carros
    }
}