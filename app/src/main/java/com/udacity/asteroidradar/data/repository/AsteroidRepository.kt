package com.udacity.asteroidradar.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.data.api.API
import com.udacity.asteroidradar.data.api.asAsteroidEntities
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.asAsteroids
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val db: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(db.dao.getAll()) {
            it.asAsteroids()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = API.getAsteroids()
            db.dao.insertAll(asteroids.asAsteroidEntities())
        }
    }
    suspend fun getPictureOfDay(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = API.getPictureOfDay()
        }
        return pictureOfDay
    }

}