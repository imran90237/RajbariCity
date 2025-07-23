package com.example.rajbaricity.network

//import com.example.rajbaricity.model.User
//import retrofit2.Response
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface ApiService {
//    @POST("api/register")
//    suspend fun register(@Body user: User): Response<Any>
//}
import com.example.rajbaricity.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/register")
    suspend fun register(@Body user: User): Response<ResponseBody>
}
