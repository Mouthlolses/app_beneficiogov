package com.example.beneficios_gov.data.api

import com.example.beneficios_gov.BuildConfig
import com.example.beneficios_gov.data.interfaces.CpfAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("chave-api-dados", BuildConfig.API_KEY_TRANS2)
            .build()
        chain.proceed(request)
    }
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.portaldatransparencia.gov.br/api-de-dados/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

val cpfApi = retrofit.create(CpfAPI::class.java)