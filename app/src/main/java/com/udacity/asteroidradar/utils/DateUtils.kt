package com.udacity.asteroidradar

import com.udacity.asteroidradar.network.Constants
import java.text.SimpleDateFormat
import java.util.*

fun getDate(addDays : Int = 0) : String{
    val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    val date = Calendar.getInstance()
    date.time = Date()
    date.add(Calendar.DATE, addDays)

    return formatter.format(date.time)
}

fun dateToNumber(dateString: String) : Int{
    return dateString.replace("-", "").toInt()
}