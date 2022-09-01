package com.udacity.asteroidradar.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.udacity.asteroidradar.data.model.Asteroid

class Diffutil : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}
