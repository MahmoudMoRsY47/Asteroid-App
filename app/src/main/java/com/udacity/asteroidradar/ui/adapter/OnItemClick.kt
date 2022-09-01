package com.udacity.asteroidradar.ui.adapter

import com.udacity.asteroidradar.data.model.Asteroid

class OnItemClick(val clickListener: (Asteroid) -> Unit) {
    fun onClick(data: Asteroid) = clickListener(data)
}