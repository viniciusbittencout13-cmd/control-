package com.controlplus.domain

object FinanceRules {
    fun calculateDailyLimit(
        monthlyBudget: Double,
        spentSoFar: Double,
        remainingDays: Int
    ): Double {
        val safeDays = remainingDays.coerceAtLeast(1)
        return ((monthlyBudget - spentSoFar) / safeDays).coerceAtLeast(0.0)
    }
}
