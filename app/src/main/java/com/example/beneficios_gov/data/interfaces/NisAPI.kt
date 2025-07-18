package com.example.beneficios_gov.data.interfaces

import com.example.beneficios_gov.data.model.nis.ConsultaNisItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NisAPI {

    @GET("novo-bolsa-familia-sacado-por-nis")
    suspend fun consultarNis(
        @Query("nis") nis: String,
        @Query("anoMesReferencia") anoMesReferencia: String,
        @Query("pagina") pagina: Int
    ): Response<List<ConsultaNisItem>>
}