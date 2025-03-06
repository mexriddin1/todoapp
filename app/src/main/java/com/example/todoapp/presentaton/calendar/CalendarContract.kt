package com.example.todoapp.presentaton.calendar

import kotlinx.coroutines.flow.StateFlow

interface CalendarContract {

    interface ViewModel {
        val uiState: StateFlow<UiState>
        fun onAction(intent: Intent)
    }

    interface Direction {
    }

    sealed interface Intent {

    }

    data class UiState(
        var test: String = ""
    )
}
