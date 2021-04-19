package com.udacity.asteroidradar.network.neoWs

import com.udacity.asteroidradar.network.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NeoWsApi {
    @GET(Constants.ASTEROIDS_PATH)
    suspend fun getAsteroids(@Query("start_date") startDate : String,
                             @Query("end_date") endDate : String,
                             @Query("api_key") apiKey : String = Constants.API_KEY): String
}