package com.udacity.asteroidradar.network.apod

import com.udacity.asteroidradar.network.Constants
import com.udacity.asteroidradar.models.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    @GET(Constants.DAILY_IMAGE)
    suspend fun getDailyImage(@Query("api_key") apiKey : String = Constants.API_KEY): PictureOfDay
}