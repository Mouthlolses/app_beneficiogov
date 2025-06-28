package com.example.beneficios_gov.data.api

import com.example.beneficios_gov.data.interfaces.MoedaAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val client = OkHttpClient.Builder()
    .build()


val apiMoeda = Retrofit.Builder()
    .baseUrl("https://brasilapi.com.br/api/cambio/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val moedaApi = apiMoeda.create(MoedaAPI::class.java)