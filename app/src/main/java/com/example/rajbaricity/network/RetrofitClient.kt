package com.example.rajbaricity.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Ensure you are using the correct IP address for your local server.
    // 10.0.2.2 is typically used for the Android emulator to connect to the host machine's localhost.
    private const val BASE_URL = "http://10.0.2.2:8081/"

    // Create a logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create an OkHttpClient and add the interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Create a lazy-initialized Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create a lazy-initialized ApiService instance
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}