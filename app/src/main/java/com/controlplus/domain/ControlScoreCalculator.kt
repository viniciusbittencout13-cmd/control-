package com.controlplus.domain

data class ControlFactors(
    val budgetRatio: Float,
    val appLimitsRatio: Float,
    val tasksRatio: Float
)

object ControlScoreCalculator {
    fun calculate(factors: ControlFactors): Int {
        val weighted =
            (factors.budgetRatio.coerceIn(0f, 1f) * 0.45f) +
                (factors.appLimitsRatio.coerceIn(0f, 1f) * 0.30f) +
                (factors.tasksRatio.coerceIn(0f, 1f) * 0.25f)
        return (weighted * 100).toInt()
    }
}
