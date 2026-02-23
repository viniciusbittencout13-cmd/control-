package com.controlplus.data.local

import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate::parse)

    @TypeConverter
    fun fromDateTime(value: LocalDateTime?): String? = value?.toString()

    @TypeConverter
    fun toDateTime(value: String?): LocalDateTime? = value?.let(LocalDateTime::parse)

    @TypeConverter
    fun fromDays(days: Set<DayOfWeek>?): String = days?.joinToString(",") { it.name } ?: ""

    @TypeConverter
    fun toDays(raw: String): Set<DayOfWeek> =
        raw.split(',').filter { it.isNotBlank() }.map { DayOfWeek.valueOf(it) }.toSet()
}
