package com.controlplus.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class UserSettings(
    @PrimaryKey val id: Long = 1,
    val budgetMode: BudgetMode = BudgetMode.FLEXIVEL,
    val dailyAppLimitMinutes: Int = 180
)

enum class BudgetMode { RIGIDO, FLEXIVEL }

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val monthlyBudget: Double,
    val isFixed: Boolean
)

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long,
    val amount: Double,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val source: String = "manual"
)

@Entity
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val targetValue: Double,
    val deadline: LocalDate? = null,
    val contributionRule: String
)

@Entity
data class GoalContribution(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val goalId: Long,
    val amount: Double,
    val date: LocalDate = LocalDate.now()
)

@Entity
data class AppLimit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val dailyMinutes: Int,
    val weeklyMinutes: Int? = null,
    val startHour: Int? = null,
    val endHour: Int? = null,
    val activeDays: Set<DayOfWeek> = DayOfWeek.entries.toSet()
)

@Entity
data class AppUsageLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val usedMinutes: Int,
    val date: LocalDate = LocalDate.now()
)

@Entity
data class CalendarEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val notes: String = ""
)

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val eventId: Long,
    val remindBeforeMinutes: Int,
    val remindEndBeforeMinutes: Int = 5
)
