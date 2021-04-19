package com.udacity.asteroidradar.network.apod

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.network.Constants
import com.udacity.asteroidradar.models.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApodApiService {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(ApodApi::class.java)

    suspend fun getDailyImage(): PictureOfDay {
        return retrofit.getDailyImage()
    }
}