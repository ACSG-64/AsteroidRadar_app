package com.udacity.asteroidradar.dataBaseOps

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: Asteroid) : List<Long>

    @Query("DELETE FROM asteroids")
    fun deleteAll()

    @Query("SELECT * FROM asteroids WHERE numeric_date >= :today ORDER BY numeric_date")
    fun getAll(today : Int) : List<Asteroid>

    @Query("SELECT * FROM asteroids WHERE numeric_date = :today")
    fun getTodayAsteroids(today : Int) : List<Asteroid>

    //@Query("SELECT * FROM asteroids WHERE id = :uuid LIMIT 1")
    //suspend fun getAsteroid(uuid : Long) : Asteroid
}