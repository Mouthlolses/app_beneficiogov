package com.example.beneficios_gov.data.model

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class Municipio(
    val codigoIBGE: String,
    val codigoRegiao: String,
    val nomeIBGE: String,
    val nomeRegiao: String,
    val pais: String,

    @Embedded(prefix = "uf_")
    val uf: Uf
) : Parcelable