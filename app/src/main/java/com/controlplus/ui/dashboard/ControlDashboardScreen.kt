package com.controlplus.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.controlplus.ui.theme.AlertRed
import com.controlplus.ui.theme.ElectricBlue
import com.controlplus.ui.theme.MediumYellow
import com.controlplus.ui.theme.NeonGreen
import com.controlplus.ui.theme.NeonPurple

@Composable
fun ControlDashboardScreen(state: DashboardUiState, onAddExpense: (Double) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            CentralControlIndicator(score = state.controlScore)
        }
        item {
            FinanceCard(dailyBudget = state.dailyBudgetLeft, progress = state.budgetProgress)
        }
        item {
            TimeCard(appUsages = state.appUsages)
        }
        item {
            GoalsCard(goals = state.goals)
        }
        item {
            CalendarCard(events = state.events)
        }
        item {
            HeatmapCard(heat = state.heatmap)
        }
        item {
            Button(onClick = { onAddExpense(40.0) }, modifier = Modifier.fillMaxWidth()) {
                Text("Registrar gasto manual (R$ 40)")
            }
        }
        state.limitWarning?.let { warning ->
            item { Text(warning, color = AlertRed) }
        }
    }
}

@Composable
private fun CentralControlIndicator(score: Int) {
    val color = when {
        score >= 75 -> NeonGreen
        score >= 50 -> MediumYellow
        else -> AlertRed
    }
    Card(shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Estou no controle hoje?", style = MaterialTheme.typography.titleLarge)
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(80.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text("$score%", color = color, style = MaterialTheme.typography.headlineLarge)
            }
            Text("Nível de Controle do Dia", color = Color.LightGray)
        }
    }
}

@Composable
private fun FinanceCard(dailyBudget: Double, progress: Float) {
    NeonCard("Financeiro", NeonGreen) {
        Text("Você pode gastar hoje: R$ ${"%.2f".format(dailyBudget)}")
        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun TimeCard(appUsages: List<AppUsageUi>) {
    NeonCard("Tempo", ElectricBlue) {
        appUsages.forEach {
            Text("${it.appName}: ${it.usedMinutes} / ${it.limitMinutes} min")
            LinearProgressIndicator(
                progress = { (it.usedMinutes.toFloat() / it.limitMinutes).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun GoalsCard(goals: List<GoalUi>) {
    NeonCard("Metas", NeonPurple) {
        goals.forEach {
            Text(it.name)
            LinearProgressIndicator(progress = { it.progress }, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun CalendarCard(events: List<EventUi>) {
    NeonCard("Calendário Inteligente", ElectricBlue) {
        events.forEach { event ->
            Text("${event.start} - ${event.end}: ${event.title}")
        }
        Text("Lembretes: X min antes e 5 min para terminar", color = Color.LightGray)
    }
}

@Composable
private fun HeatmapCard(heat: List<ExpenseHeat>) {
    NeonCard("Heatmap de gastos", AlertRed) {
        val rows = heat.chunked(7)
        rows.forEach { week ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                week.forEach { day ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(18.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (day.exceeded) AlertRed else Color(0xFF1A2A1A).copy(alpha = day.intensity + 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(day.day.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun NeonCard(title: String, borderColor: Color, content: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, color = borderColor, style = MaterialTheme.typography.titleLarge)
            content()
        }
    }
}
