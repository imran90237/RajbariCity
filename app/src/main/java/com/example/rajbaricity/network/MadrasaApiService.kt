package com.example.rajbaricity.network

import com.example.rajbaricity.model.MadrasaInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MadrasaApiService {
    @GET("api/madrasas")
    suspend fun getAll(): Response<List<MadrasaInfo>>

    @POST("api/madrasas")
    suspend fun create(@Body madrasaInfo: MadrasaInfo): Response<MadrasaInfo>
}