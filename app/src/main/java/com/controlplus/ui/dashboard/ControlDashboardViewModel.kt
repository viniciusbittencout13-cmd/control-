package com.controlplus.ui.dashboard

import androidx.lifecycle.ViewModel
import com.controlplus.domain.ControlFactors
import com.controlplus.domain.ControlScoreCalculator
import com.controlplus.domain.FinanceRules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

data class AppUsageUi(val appName: String, val usedMinutes: Int, val limitMinutes: Int)
data class GoalUi(val name: String, val progress: Float)
data class EventUi(val title: String, val start: String, val end: String)
data class ExpenseHeat(val day: Int, val intensity: Float, val exceeded: Boolean)

data class DashboardUiState(
    val controlScore: Int = 0,
    val dailyBudgetLeft: Double = 0.0,
    val budgetProgress: Float = 0f,
    val appUsages: List<AppUsageUi> = emptyList(),
    val goals: List<GoalUi> = emptyList(),
    val events: List<EventUi> = emptyList(),
    val heatmap: List<ExpenseHeat> = emptyList(),
    val limitWarning: String? = null
)

class ControlDashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(mockState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun addExpense(amount: Double) {
        val current = _uiState.value
        val monthBudget = 3000.0
        val spent = ((1f - current.budgetProgress) * monthBudget).toDouble() + amount
        val remainingDays = (LocalDate.now().lengthOfMonth() - LocalDate.now().dayOfMonth).coerceAtLeast(1)
        val dailyLimit = FinanceRules.calculateDailyLimit(monthBudget, spent, remainingDays)
        val newProgress = (spent / monthBudget).toFloat().coerceIn(0f, 1f)
        val warning = if (dailyLimit <= 0.0) "Limite diário esgotado. Modo rígido sugerido." else null
        val factors = ControlFactors(
            budgetRatio = 1f - newProgress,
            appLimitsRatio = 0.74f,
            tasksRatio = 0.80f
        )
        _uiState.value = current.copy(
            dailyBudgetLeft = dailyLimit,
            budgetProgress = newProgress,
            controlScore = ControlScoreCalculator.calculate(factors),
            limitWarning = warning
        )
    }

    private fun mockState(): DashboardUiState {
        val budget = 3000.0
        val spent = 1540.0
        val remainingDays = (LocalDate.now().lengthOfMonth() - LocalDate.now().dayOfMonth).coerceAtLeast(1)
        val daily = FinanceRules.calculateDailyLimit(budget, spent, remainingDays)
        return DashboardUiState(
            controlScore = ControlScoreCalculator.calculate(ControlFactors(0.81f, 0.78f, 0.85f)),
            dailyBudgetLeft = daily,
            budgetProgress = (spent / budget).toFloat(),
            appUsages = listOf(
                AppUsageUi("Instagram", 58, 60),
                AppUsageUi("YouTube", 35, 45),
                AppUsageUi("WhatsApp", 40, 90)
            ),
            goals = listOf(
                GoalUi("Reserva de Emergência", 0.62f),
                GoalUi("Notebook Novo", 0.38f)
            ),
            events = listOf(
                EventUi("Revisão de orçamento", "18:00", "18:30"),
                EventUi("Bloco foco", "20:00", "21:30")
            ),
            heatmap = (1..30).map { day ->
                val intensity = ((day % 6) / 5f)
                ExpenseHeat(day = day, intensity = intensity, exceeded = intensity > 0.85f)
            }
        )
    }
}
