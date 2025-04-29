package com.example.beneficios_gov.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/*
class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, "beneficios.db", null, 1
) {
    companion object{
        const val TABELA_CONSULTAS = "consultas"
        const val ID_CONSULTA = "id_consulta"
        const val TITULO = "titulo"
        const val DESCRICAO = "descricao"
    }
    //Iniciado apenas uma vez quando o user instala o app em seu aparelho
    override fun onCreate(db: SQLiteDatabase?) {
        Log.i("info.db", "Executou onCreate")
        val sql = "CREATE TABLE IF NOT EXISTS $TABELA_CONSULTAS (" +
                "$ID_CONSULTA integer not null PRIMARY KEY AUTOINCREMENT," +
                "$TITULO varchar (100)," +
                "$DESCRICAO text" +
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
}*/
