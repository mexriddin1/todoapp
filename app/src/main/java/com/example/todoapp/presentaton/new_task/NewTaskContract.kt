package com.example.todoapp.presentaton.new_task


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.todoapp.data.local.room.entity.TaskUiData
import kotlinx.coroutines.flow.StateFlow

interface NewTaskContract {

    interface ViewModel {
        val uiState: StateFlow<UiState>
        fun onAction(intent: Intent)
    }

    interface Direction {
        suspend fun back()
    }

    sealed interface Intent {
        data object Back : Intent
        data class AddNewTask(val data: TaskUiData) : Intent
        data class EditNewTask(val data: TaskUiData) : Intent
        data class LoadTask(val id: Int) : Intent
    }

    data class UiState(
        var task: TaskUiData? = null,
        var title: String = "",
        var list: SnapshotStateList<Pair<Boolean, String>> = mutableStateListOf(
            Pair(false, ""),
        )
    )
}
