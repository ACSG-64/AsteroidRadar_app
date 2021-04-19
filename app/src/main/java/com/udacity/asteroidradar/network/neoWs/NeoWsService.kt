package com.udacity.asteroidradar.network.neoWs

import com.udacity.asteroidradar.network.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class NeoWsService {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(NeoWsApi::class.java)

    suspend fun getAsteroids(startDate: String, endDate: String): String {
        return retrofit.getAsteroids(startDate, endDate)
    }
}