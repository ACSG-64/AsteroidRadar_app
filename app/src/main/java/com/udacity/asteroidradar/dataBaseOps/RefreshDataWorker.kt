package com.udacity.asteroidradar.dataBaseOps

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.network.neoWs.NeoWsService
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.getDate
import org.json.JSONObject
import java.lang.Exception

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }

    private val neowsApiService = NeoWsService()

    override suspend fun doWork(): Result {
        val dao = AsteroidDatabase(applicationContext).asteroidDao

        val todayFormatted = getDate()
        val sevenDaysFormatted = getDate(7)

        try {
            val asteroidListResult = neowsApiService.getAsteroids(todayFormatted, sevenDaysFormatted)
            val asteroidListParsed = parseAsteroidsJsonResult(JSONObject(asteroidListResult))

            dao.deleteAll()
            dao.insertAll(*asteroidListParsed.toTypedArray())
        } catch(e : Exception){
            return Result.retry()
        }

        return Result.success()
    }
}