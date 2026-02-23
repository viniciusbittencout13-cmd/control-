package com.controlplus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.controlplus.data.model.AppLimit
import com.controlplus.data.model.AppUsageLog
import com.controlplus.data.model.CalendarEvent
import com.controlplus.data.model.Category
import com.controlplus.data.model.Expense
import com.controlplus.data.model.Goal
import com.controlplus.data.model.GoalContribution
import com.controlplus.data.model.Reminder
import com.controlplus.data.model.UserSettings

@Database(
    entities = [
        UserSettings::class,
        Category::class,
        Expense::class,
        Goal::class,
        GoalContribution::class,
        AppLimit::class,
        AppUsageLog::class,
        CalendarEvent::class,
        Reminder::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase()
