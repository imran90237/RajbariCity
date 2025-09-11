package com.example.rajbaricity.network

import com.example.rajbaricity.model.MadrasaInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MadrasaApiService {
    @GET("api/madrasa/qawmi")
    suspend fun getQawmi(): List<MadrasaInfo>

    @POST("api/madrasa/qawmi")
    suspend fun createQawmi(@Body madrasaInfo: MadrasaInfo): MadrasaInfo

    @GET("api/madrasa/alia")
    suspend fun getAlia(): List<MadrasaInfo>

    @POST("api/madrasa/alia")
    suspend fun createAlia(@Body madrasaInfo: MadrasaInfo): MadrasaInfo
}
