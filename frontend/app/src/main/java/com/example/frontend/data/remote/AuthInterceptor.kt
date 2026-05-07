package com.example.frontend.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.flow.first

class AuthInterceptor(private val tokenStore: TokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenStore.accessToken.first() }
        val request =
            if (token.isNullOrBlank()) {
                chain.request()
            } else {
                chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            }
        return chain.proceed(request)
    }
}

