package com.example.beneficios_gov.data.model.nis

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BeneficiarioNovoBolsaFamilia(
    val cpfFormatado: String,
    val nis: String,
    val nome: String
) : Parcelable