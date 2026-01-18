package com.example.expensetracker.utils

import androidx.room.TypeConverter
import java.time.LocalDate


class LocalDateConverter {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long =
        date.toEpochDay()

    @TypeConverter
    fun toLocalDate(epochDay: Long): LocalDate =
        LocalDate.ofEpochDay(epochDay)
}
