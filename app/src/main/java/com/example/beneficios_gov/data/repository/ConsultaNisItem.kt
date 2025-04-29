package com.example.beneficios_gov.data.repository

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class ConsultaNisItem(
    @PrimaryKey(autoGenerate = true)
    val id1: Int, // ID interno da tabela Room

    @Embedded(prefix = "beneficiario_")
    val beneficiarioNovoBolsaFamilia: BeneficiarioNovoBolsaFamilia,

    val dataMesCompetencia: String,
    val dataMesReferencia: String,
    val idAPi: Int,

    @Embedded(prefix = "municipio_")
    val municipio: Municipio,

    val valorSaque: Int
) : Parcelable