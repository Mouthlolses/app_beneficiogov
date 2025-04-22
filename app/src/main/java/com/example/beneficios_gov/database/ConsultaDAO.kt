package com.example.beneficios_gov.database

import android.content.Context
import android.util.Log
import com.example.beneficios_gov.model.Consulta

class ConsultaDAO(context: Context) : IConsultaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(consulta: Consulta): Boolean {
        val titulo = consulta.titulo
        val sql = "INSERT INTO ${DatabaseHelper.TABELA_CONSULTAS} " +
        "VALUES(null, '$titulo', 'Descricao..');"

        try {
            escrita.execSQL(sql)
            Log.i("info.db", "Sucesso ao inserir")
        } catch (e: Exception) {
            Log.i("info.db", "Erro ao Inserir, $e")
            return false
        }
        return true
    }

    override fun atualizar(consulta: Consulta): Boolean {
        val titulo = consulta.titulo
        val sql = "UPDATE ${DatabaseHelper.TABELA_CONSULTAS}" +
                "SET ${DatabaseHelper.TITULO} = '$titulo' " +
                "WHERE ${DatabaseHelper.ID_CONSULTA} = 1;"

        try {
            escrita.execSQL(sql)
            Log.i("info.db", "Sucesso ao Atualizar")
        } catch (e: Exception) {
            Log.i("info.db", "Erro ao Atualizar, $e")
            return false
        }
        return true
    }

    override fun remover(idConsulta: Int): Boolean {
        val sql =
            "DELETE FROM ${DatabaseHelper.TABELA_CONSULTAS}" +
                    " WHERE ${DatabaseHelper.ID_CONSULTA} = $idConsulta;"
        try {
            escrita.execSQL(sql)
            Log.i("info.db", "Sucesso ao Excluir")
        } catch (e: Exception) {
            Log.i("info.db", "Erro ao Excluir, $e")
            return false
        }
        return true
    }

    override fun listar(): List<Consulta> {

        val listaConsulta = mutableListOf<Consulta>()

        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_CONSULTAS};"
        val cursor = leitura.rawQuery(sql, null)

        val indiceConsulta = cursor.getColumnIndexOrThrow(DatabaseHelper.ID_CONSULTA)
        val indiceTitulo = cursor.getColumnIndexOrThrow(DatabaseHelper.TITULO)
        val indiceDescricao = cursor.getColumnIndexOrThrow(DatabaseHelper.DESCRICAO)

        while (cursor.moveToNext()) {
            val idConsulta = cursor.getLong(indiceConsulta)
            val titulo = cursor.getString(indiceTitulo)
            val descricao = cursor.getString(indiceDescricao)
            Log.i("info.db", "id: $idConsulta - $titulo - $descricao")

            val consulta = Consulta(idConsulta, titulo, descricao)
            listaConsulta.add(consulta)
        }
        cursor.close()
        leitura.close()
        return listaConsulta
    }


}