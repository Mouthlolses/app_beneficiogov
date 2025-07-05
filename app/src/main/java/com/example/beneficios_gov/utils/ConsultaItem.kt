package com.example.beneficios_gov.utils

import com.example.beneficios_gov.data.model.moeda.ConsultaCotacaoMoeda
import com.example.beneficios_gov.data.model.nis.ConsultaNisItem

sealed class ConsultaItem {
    data class nisItem(val data: ConsultaNisItem) : ConsultaItem()
    data class coinItem(val data: ConsultaCotacaoMoeda) : ConsultaItem()
}