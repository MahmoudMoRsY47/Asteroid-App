package com.udacity.asteroidradar.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM AsteroidTable WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun asteroidOfToDay(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM AsteroidTable ORDER by closeApproachDate")
    fun getAllData(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(asteroids: List<AsteroidEntity>)
}