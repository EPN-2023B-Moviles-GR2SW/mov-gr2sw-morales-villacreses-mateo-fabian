package com.example.examib_sqlite.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examib_sqlite.models.Concesionario

class SqliteHelperConcesionario(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "sqliteExam", // nombre bdd
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaConcesionario =
            """
                CREATE TABLE IF NOT EXISTS CONCESIONARIO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre STRING,
                ubicacion STRING,
                isOpen BOOLEAN,
                numeroEmpleados INT
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaConcesionario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // ("Not yet implemented")
    }

    // Función para insertar un nuevo concesionario
    fun insertarConcesionario(
        nombre: String,
        ubicacion: String,
        isOpen: Boolean,
        numeroEmpleados: Int
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("ubicacion", ubicacion)
            put("isOpen", isOpen)
            put("numeroEmpleados", numeroEmpleados)
        }

        return try {
            db.insert("CONCESIONARIO", null, valores)
        } catch (e: SQLException) {
            // Manejar errores de inserción si es necesario
            -1
        } finally {
            db.close()
        }
    }

    // Función para actualizar un concesionario
    fun actualizarConcesionario(
        nombre: String,
        ubicacion: String,
        isOpen: Boolean,
        numeroEmpleados: Int,
        id: Int
    ): Boolean {
        val db = writableDatabase
        val valoresAActualizar = ContentValues().apply {
            put("nombre", nombre)
            put("ubicacion", ubicacion)
            put("isOpen", isOpen)
            put("numeroEmpleados", numeroEmpleados)
        }

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = db.update(
            "CONCESIONARIO",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )

        db.close()
        return resultadoActualizacion != -1
    }

    // Función para eliminar un concesionario por su ID
    fun eliminarConcesionario(id: Int): Boolean {
        val db = writableDatabase
        val resultadoEliminacion = db.delete("CONCESIONARIO", "id = ?", arrayOf(id.toString()))
        db.close()

        return resultadoEliminacion != -1
    }

    // Función para obtener todos los concesionarios
    @SuppressLint("Range")
    fun obtenerTodosConcesionarios(): List<Concesionario> {
        val concesionarios = mutableListOf<Concesionario>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM CONCESIONARIO", null)
        } catch (e: SQLException) {
            // Manejar errores de consulta si es necesario
            return emptyList()
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val ubicacion = cursor.getString(cursor.getColumnIndex("ubicacion"))
                val isOpen = cursor.getInt(cursor.getColumnIndex("isOpen")) == 1
                val numeroEmpleados = cursor.getInt(cursor.getColumnIndex("numeroEmpleados"))

                concesionarios.add(Concesionario(id, nombre, ubicacion, isOpen, numeroEmpleados, ArrayList()))
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()

        return concesionarios
    }

}