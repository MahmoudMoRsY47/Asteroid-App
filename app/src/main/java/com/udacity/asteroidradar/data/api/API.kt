package com.udacity.asteroidradar.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.util.Constants
import com.udacity.asteroidradar.data.model.Asteroid
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object API {

    var startDate= getToday()
    var endDate= getToday()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()

    val apiService = retrofit.create(AsteroidService::class.java)

    suspend fun getAllAsteroid() : List<Asteroid> {
        val responseStr = apiService.getAsteroids("","", Constants.API_KEY)
        val responseJsonObject = JSONObject(responseStr)

        return parseAsteroidsJsonResult(responseJsonObject)
    }

    suspend fun getOneDayAsteroid() : List<Asteroid> {
        val responseStr = apiService.getAsteroids(startDate, endDate, Constants.API_KEY)
        val responseJsonObject = JSONObject(responseStr)

        return parseAsteroidsJsonResult(responseJsonObject)
    }

    suspend fun getPictureOfDay() = apiService.getPictureOfDay(Constants.API_KEY)

}