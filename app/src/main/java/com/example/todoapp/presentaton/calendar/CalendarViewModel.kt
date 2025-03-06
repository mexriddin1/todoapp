package com.example.todoapp.presentaton.calendar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val direction: CalendarContract.Direction
) : ViewModel(), CalendarContract.ViewModel {

    override var uiState = MutableStateFlow(CalendarContract.UiState())

    private fun reduce(block: (CalendarContract.UiState) -> CalendarContract.UiState) {
        val old = uiState.value
        val new = block.invoke(old)
        uiState.value = new
    }

    override fun onAction(intent: CalendarContract.Intent) {
        when (intent) {
            else -> {

            }
        }
    }
}
