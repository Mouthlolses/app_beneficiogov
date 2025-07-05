package com.example.beneficios_gov.data.model.moeda

import com.google.gson.annotations.SerializedName

data class Cotacao(
    @SerializedName("cotacao_compra")
    val cotacaoCompra: Double,

    @SerializedName("cotacao_venda")
    val cotacaoVenda: Double,

    @SerializedName("data_hora_cotacao")
    val dataHoraCotacao: String,

    @SerializedName("paridade_compra")
    val paridadeCompra: Int,

    @SerializedName("paridade_venda")
    val paridadeVenda: Int,

    @SerializedName("tipo_boletim")
    val tipoBoletim: String
)