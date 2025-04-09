package com.example.beneficios_gov.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, "beneficios.db", null, 1
) {
    //Iniciado apenas uma vez quando o user instala o app em seu aparelho
    override fun onCreate(db: SQLiteDatabase?) {
        Log.i("info.db", "Executou onCreate")
        val sql = "CREATE TABLE IF NOT EXISTS consultas (" +
                "id_consulta integer not null PRIMARY KEY AUTOINCREMENT," +
                "titulo varchar (100)," +
                " descricao text" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info.db", "Sucesso ao criar a tabela")
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.i("info.db", "Erro ao criar a tabela")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("info.db", "Executou onUpgrade")
        TODO("Not yet implemented")
    }
}