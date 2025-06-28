package com.example.beneficios_gov.data.model.moeda

data class Cotacao(
    val cotacao_compra: Double,
    val cotacao_venda: Double,
    val data_hora_cotacao: String,
    val paridade_compra: Int,
    val paridade_venda: Int,
    val tipo_boletim: String
)