package com.udacity.asteroidradar.uptodate

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import retrofit2.HttpException

class WorkManger(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroidsWorker"
    }

    override suspend fun doWork(): Result {

        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshSeven()
            Result.success()

        } catch (e: HttpException) {
            Result.retry()
        }
    }
}