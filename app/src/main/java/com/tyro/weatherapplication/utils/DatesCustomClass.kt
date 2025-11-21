package com.tyro.weatherapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getDayFromFullDate(dateString: String): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(dateString, formatter)
    return date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
}