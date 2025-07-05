package com.example.beneficios_gov.data.interfaces

import com.example.beneficios_gov.data.model.moeda.ConsultaCotacaoMoeda
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoedaAPI {

    @GET("/api/cambio/v1/cotacao/{moeda}/{data}")
    suspend fun searchCotacao(
        @Path("moeda") moeda: String,
        @Path("data") data: String
    ): Response<ConsultaCotacaoMoeda>
}