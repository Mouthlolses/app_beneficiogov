package com.example.beneficios_gov.data.model.nis

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Uf(
    val nome: String,
    val sigla: String
) : Parcelable