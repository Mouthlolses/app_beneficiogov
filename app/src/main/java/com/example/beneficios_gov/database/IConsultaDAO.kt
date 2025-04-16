package com.example.beneficios_gov.database

import com.example.beneficios_gov.model.Consulta

interface IConsultaDAO {

    fun salvar(consulta: Consulta): Boolean
    fun atualizar(consulta: Consulta): Boolean
    fun remover(idConsulta: Int): Boolean
    fun listar(): List<Consulta>

}