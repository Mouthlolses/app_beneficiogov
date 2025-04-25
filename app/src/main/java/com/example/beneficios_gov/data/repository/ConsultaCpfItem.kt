package com.example.beneficios_gov.data.repository

import com.example.beneficios_gov.data.repository.Municipio
import com.example.beneficios_gov.data.repository.TitularBolsaFamilia

data class ConsultaCpfItem(
    val dataMesCompetencia: String,
    val dataMesReferencia: String,
    val id: Int,
    val municipio: Municipio,
    val quantidadeDependentes: Int,
    val titularBolsaFamilia: TitularBolsaFamilia,
    val valor: Int
)