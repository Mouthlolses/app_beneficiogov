package com.example.beneficios_gov.data.interceptor


/*
class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SecurePrefsManager.getToken(context) ?: ""
        val request = chain.request()
            .newBuilder()
            .addHeader("chave-api-dados", token)// API key via header
            .build()
        return chain.proceed(request)
    }
}*/
