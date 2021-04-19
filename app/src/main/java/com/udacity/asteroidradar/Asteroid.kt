package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroids")
data class Asteroid(@PrimaryKey
                    var id: Long,
                    val codename: String,
                    @ColumnInfo(name = "numeric_date")
                    val numericDate: Int,
                    @ColumnInfo(name = "close_approach_date")
                    val closeApproachDate: String,
                    @ColumnInfo(name = "absolute_magnitude")
                    val absoluteMagnitude: Double,
                    @ColumnInfo(name = "estimated_diameter")
                    val estimatedDiameter: Double,
                    @ColumnInfo(name = "relative_velocity")
                    val relativeVelocity: Double,
                    @ColumnInfo(name = "distance_from_earth")
                    val distanceFromEarth: Double,
                    @ColumnInfo(name = "is_potentially_hazardous")
                    val isPotentiallyHazardous: Boolean) : Parcelable