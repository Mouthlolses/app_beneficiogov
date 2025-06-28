package com.example.beneficios_gov.data.model.moeda

data class ConsultaCotacaoMoeda(
    val cotacaos: List<Cotacao>,
    val `data`: String,
    val moeda: String
)