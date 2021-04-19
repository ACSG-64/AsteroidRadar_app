package com.udacity.asteroidradar.views

import android.view.View
import com.udacity.asteroidradar.Asteroid

interface AsteroidClickListener {
    fun onCardClicked(view: View, asteroidDetail: Asteroid)
}