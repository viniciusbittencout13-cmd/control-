package com.controlplus.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ControlScoreCalculatorTest {

    @Test
    fun `deve calcular score ponderado corretamente`() {
        val score = ControlScoreCalculator.calculate(
            ControlFactors(
                budgetRatio = 0.8f,
                appLimitsRatio = 0.5f,
                tasksRatio = 1.0f
            )
        )

        assertThat(score).isEqualTo(76)
    }
}
