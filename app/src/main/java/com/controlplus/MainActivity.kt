package com.controlplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.controlplus.ui.dashboard.ControlDashboardScreen
import com.controlplus.ui.dashboard.ControlDashboardViewModel
import com.controlplus.ui.theme.ControlPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ControlPlusTheme {
                val vm: ControlDashboardViewModel = viewModel()
                val state by vm.uiState.collectAsStateWithLifecycle()
                ControlDashboardScreen(state = state, onAddExpense = vm::addExpense)
            }
        }
    }
}
