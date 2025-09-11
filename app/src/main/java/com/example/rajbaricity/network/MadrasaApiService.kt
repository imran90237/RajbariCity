package com.example.rajbaricity.network

import com.example.rajbaricity.model.MadrasaInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MadrasaApiService {
    @GET("api/madrasa")
    suspend fun getAll(): List<MadrasaInfo>

    @POST("api/madrasa")
    suspend fun create(@Body madrasaInfo: MadrasaInfo): MadrasaInfo
}