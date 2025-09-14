package com.example.rajbaricity.network

import com.example.rajbaricity.model.HotelRestaurant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HotelRestaurantApiService {
    @GET("/api/hotels")
    suspend fun getAllHotels(): List<HotelRestaurant>

    @POST("/api/hotels")
    suspend fun createHotel(@Body hotel: HotelRestaurant): HotelRestaurant
}
