package com.example.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


val json = Json {
    ignoreUnknownKeys = true
}

val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl("")
    .build()


interface ApiService {

    @GET("novo-bolsa-familia-sacado-por-nis")
    suspend fun getFamilyBagInfo()
}

object RetrofitHelper  {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}