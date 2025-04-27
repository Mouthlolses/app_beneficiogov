package com.example.beneficios_gov.data.repository

data class Municipio(
    val codigoIBGE: String,
    val codigoRegiao: String,
    val nomeIBGE: String,
    val nomeRegiao: String,
    val pais: String,
    val uf: Uf
)