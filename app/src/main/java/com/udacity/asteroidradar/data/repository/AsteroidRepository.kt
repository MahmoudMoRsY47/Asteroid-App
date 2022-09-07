package com.udacity.asteroidradar.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.data.api.API
import com.udacity.asteroidradar.data.api.asAsteroidEntities
import com.udacity.asteroidradar.data.api.getSeventhDay
import com.udacity.asteroidradar.data.api.getToday
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.database.asAsteroids
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val db: AsteroidDatabase) {

    var startDate= getToday()
    var endDate= getToday()
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(db.dao.getData(startDate,endDate)) {
            it.asAsteroids()
        }

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val asteroids = API.getAsteroids()
            db.dao.insertData(asteroids.asAsteroidEntities())
        }
    }
    suspend fun getPicture(): PictureOfDay {
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = API.getPictureOfDay()
        }
        return pictureOfDay
    }

}