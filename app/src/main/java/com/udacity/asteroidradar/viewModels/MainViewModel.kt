package com.udacity.asteroidradar.viewModels

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.network.apod.ApodApiService
import com.udacity.asteroidradar.network.neoWs.NeoWsService
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.dateToNumber
import com.udacity.asteroidradar.getDate
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.dataBaseOps.AsteroidDatabase
import com.udacity.asteroidradar.models.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val neowsApiService = NeoWsService()
    private val apodApiService = ApodApiService()

    val imageOfTheDay = MutableLiveData<PictureOfDay>()
    val asteroidList = MutableLiveData<List<Asteroid>>()

    val loadError = MutableLiveData<Boolean>()
    val loadErrorMessage = MutableLiveData<String>()
    val loadingStatus = MutableLiveData<Boolean>()

    fun refresh(){
        loadingStatus.value = true
        loadError.value = false
        viewModelScope.launch { fetchFromDataBase() }
        viewModelScope.launch { getImage() }
    }

    /* Image of the day */
    private suspend fun getImage(){
        try {
            val apodResult = apodApiService.getDailyImage()
            imageOfTheDay.value = apodResult
        } catch (e: Exception){}
    }

    /* Asteroids records */

    // From local data base
    private suspend fun fetchFromDataBase() {
        val dao = AsteroidDatabase(getApplication()).asteroidDao

        val today = dateToNumber(getDate())
        val asteroidsList = withContext(Dispatchers.IO){
            dao.getAll(today)
        }

        asteroidsRetrieved("local", asteroidsList)
    }

    // From remote
    private suspend fun fetchFromRemote(){

        val todayFormatted = getDate()
        val sevenDaysFormatted = getDate(7)

        try{
            val asteroidListResult = withContext(Dispatchers.IO) {
                neowsApiService.getAsteroids(todayFormatted, sevenDaysFormatted)
            }
            val asteroidListParsed = withContext(Dispatchers.Default) {
                parseAsteroidsJsonResult(JSONObject(asteroidListResult))
            }
            storeAsteroidsLocally(asteroidListParsed)
        } catch (e: Exception){
            Log.e("RETROFIT", e.toString())
            loadErrorMessage.value = Resources.getSystem().getString(R.string.no_connection)
            loadError.value = true
            loadingStatus.value = false
        }
    }

    private suspend fun storeAsteroidsLocally(asteroidsList: List<Asteroid>){
        val dao = AsteroidDatabase(getApplication()).asteroidDao

        withContext(Dispatchers.IO) {
            dao.deleteAll()
            dao.insertAll(*asteroidsList.toTypedArray())
        }

        asteroidsRetrieved("remote", asteroidsList)
    }

    // Update View Model values
    private suspend fun asteroidsRetrieved(source: String, asteroids: List<Asteroid>){
        if(asteroids.isEmpty()){
            when (source) {
                "local" -> {
                    fetchFromRemote()
                }
                "remote" -> {
                    loadErrorMessage.value = Resources.getSystem().getString(R.string.server_error)
                    loadError.value = true
                    loadingStatus.value = false
                }
                else -> {
                    loadErrorMessage.value = Resources.getSystem().getString(R.string.no_cache)
                    loadError.value = true
                    loadingStatus.value = false
                }
            }
        } else {
            asteroidList.value = asteroids
            loadingStatus.value = false
            loadError.value = false
        }
    }

    // Filters
    fun getTodayAsteroids(){
        viewModelScope.launch {
            val dao = AsteroidDatabase(getApplication()).asteroidDao

            val today = dateToNumber(getDate())
            val asteroidsList = withContext(Dispatchers.IO) {
                dao.getTodayAsteroids(today)
            }

            asteroidsRetrieved("local-filter", asteroidsList)
        }
    }

    fun getWeekAsteroids(){
        viewModelScope.launch {
            fetchFromDataBase()
        }
    }




}