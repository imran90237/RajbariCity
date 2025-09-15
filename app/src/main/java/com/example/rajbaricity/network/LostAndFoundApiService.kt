package com.example.rajbaricity.network

import com.example.rajbaricity.model.LostAndFound
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LostAndFoundApiService {
    @GET("/api/lostfound")
    suspend fun getLostAndFounds(): List<LostAndFound>

    @POST("/api/lostfound")
    suspend fun addLostAndFound(@Body lostAndFound: LostAndFound): LostAndFound
}
