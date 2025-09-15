package com.example.rajbaricity.network

import com.example.rajbaricity.model.JobsTraining
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JobsTrainingApiService {
    @GET("/api/jobs-training")
    suspend fun getJobsTrainings(): List<JobsTraining>

    @POST("/api/jobs-training")
    suspend fun addJobsTraining(@Body jobsTraining: JobsTraining): JobsTraining
}
