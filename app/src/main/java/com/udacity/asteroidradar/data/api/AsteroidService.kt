package com.udacity.asteroidradar.data.api

import com.udacity.asteroidradar.util.Constants
import com.udacity.asteroidradar.data.model.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String): String

    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String) : PictureOfDay
}