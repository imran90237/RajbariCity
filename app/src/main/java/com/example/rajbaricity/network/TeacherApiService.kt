package com.example.rajbaricity.network

import com.example.rajbaricity.model.Teacher
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeacherApiService {
    @GET("api/teachers")
    suspend fun getAllTeachers(): List<Teacher>

    @POST("api/teachers")
    suspend fun createTeacher(@Body teacher: Teacher): Teacher

    @POST("api/teachers/{id}/like")
    suspend fun likeTeacher(@Path("id") id: Long): Teacher
}
