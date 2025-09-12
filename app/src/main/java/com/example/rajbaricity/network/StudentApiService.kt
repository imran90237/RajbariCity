package com.example.rajbaricity.network

import com.example.rajbaricity.model.Student
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentApiService {
    @GET("api/students")
    suspend fun getAllStudents(): List<Student>

    @POST("api/students")
    suspend fun createStudent(@Body student: Student): Student

    @POST("api/students/{id}/like")
    suspend fun likeStudent(@Path("id") id: Long): Student
}
