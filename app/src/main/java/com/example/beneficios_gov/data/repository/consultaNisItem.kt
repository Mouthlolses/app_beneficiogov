package com.example.beneficios_gov.data.repository

data class consultaNisItem(
    val beneficiarioNovoBolsaFamilia: BeneficiarioNovoBolsaFamilia,
    val dataMesCompetencia: String,
    val dataMesReferencia: String,
    val id: Int,
    val municipio: Municipio,
    val valorSaque: Int
)