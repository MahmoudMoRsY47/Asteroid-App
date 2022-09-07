package com.udacity.asteroidradar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.data.database.AsteroidDatabase
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AsteroidDatabase.getInstance(app)
    private val repository = AsteroidRepository(database)
    val allAsteroids = repository.allAsteroids
    val oneAsteroids = repository.oneDayAsteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment
        get() = _navigateToDetailFragment



    init {

            refreshSevent()

        }




    fun onAsteroidItemClick(data: Asteroid) {
        _navigateToDetailFragment.value = data
    }

    fun onDetailFragmentNavigated() {
        _navigateToDetailFragment.value = null
    }

     private fun refreshSevent() {
        viewModelScope.launch {
            launch { try {
                repository.refreshSeven()

            } catch (e: Exception) {
                e.printStackTrace()
            } }
            launch {   try {
                _pictureOfDay.value = repository.getPicture()
            } catch (e: Exception) {
                e.printStackTrace()
            } }
        }
    }



}