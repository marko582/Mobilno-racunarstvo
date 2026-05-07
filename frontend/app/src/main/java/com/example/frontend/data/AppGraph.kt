package com.example.frontend.data

import android.content.Context
import com.example.frontend.BuildConfig
import com.example.frontend.data.remote.AuthInterceptor
import com.example.frontend.data.remote.MovieTrackerApi
import com.example.frontend.data.remote.TokenStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppGraph {
    private val BASE_URL = BuildConfig.BASE_URL

    lateinit var tokenStore: TokenStore
        private set

    lateinit var api: MovieTrackerApi
        private set

    lateinit var repository: Repository
        private set

    fun init(context: Context) {
        if (::repository.isInitialized) return

        tokenStore = TokenStore(context.applicationContext)

        val logging =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client =
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(tokenStore))
                .addInterceptor(logging)
                .build()

        api =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieTrackerApi::class.java)

        repository = Repository(api = api, tokenStore = tokenStore)
    }
}

