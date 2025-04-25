package com.example.beneficios_gov.data.interfaces

import com.example.beneficios_gov.data.repository.ConsultaCpfItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CpfAPI {

    @GET("bolsa-familia-disponivel-por-cpf-ou-nis")
    suspend fun consultarCpf (
        @Query("codigo") codigo: String,
        @Query("anoMesReferencia") anoMesReferencia: String,
        @Query("anoMesCompetencia") anoMesCompetencia: String,
        @Query("pagina") pagina: Int
    ): Response<List<ConsultaCpfItem>>
}