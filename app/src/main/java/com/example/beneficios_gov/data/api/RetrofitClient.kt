package com.example.beneficios_gov.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Aqui, estamos criando uma instância singleton do Retrofit para ser reutilizada em várias partes do aplicativo.

/*
object RetrofitClient {
    private const val BASE_URL = "https://api.portaltransparencia.gov.br/api-de-dados/"

    val instance: BolsaFamiliaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BolsaFamiliaApi::class.java)
    }
}*/
