package com.example.rajbaricity.network

import com.example.rajbaricity.model.Bustime
import retrofit2.Call
import retrofit2.http.*

interface BustimeApiService {

    @GET("api/bus_time")
    fun getBusTimes(): Call<List<Bustime>>

    @POST("api/bus_time")
    fun addBusTime(@Body busTime: Bustime): Call<Bustime>

    @PUT("api/bus_time/{id}")
    fun updateBusTime(@Path("id") id: Long, @Body busTime: Bustime): Call<Bustime>

    @DELETE("api/bus_time/{id}")
    fun deleteBusTime(@Path("id") id: Long): Call<Void>
}
