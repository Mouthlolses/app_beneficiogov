package com.example.beneficios_gov.data.interceptor

import com.example.beneficios_gov.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("chave-api-dados", BuildConfig.API_KEY_TRANS2)// API key via header
            .build()
        return chain.proceed(request)
    }
}