package com.example.rajbaricity.network

import com.example.rajbaricity.model.BusCounter
import retrofit2.Call
import retrofit2.http.*

interface BusCounterApiService {

    @GET("api/bus_counter")
    fun getCounters(): Call<List<BusCounter>>

    @POST("api/bus_counter")
    fun addCounter(@Body counter: BusCounter): Call<BusCounter>

    @PUT("api/bus_counter/{id}")
    fun updateCounter(@Path("id") id: Long, @Body counter: BusCounter): Call<BusCounter>

    @DELETE("api/bus_counter/{id}")
    fun deleteCounter(@Path("id") id: Long): Call<Void>
}
