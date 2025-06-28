package com.example.beneficios_gov.data.interfaces

import com.example.beneficios_gov.data.model.moeda.Cotacao
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoedaAPI {

    @GET("cotacao/{moeda}/{data}")
    suspend fun searchCotacao(
        @Query("moeda") moeda: String,
        @Query("data") data: String
    ): Response<List<Cotacao>>
}