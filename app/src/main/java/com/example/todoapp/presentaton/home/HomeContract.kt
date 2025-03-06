package com.example.todoapp.presentaton.home


import com.example.todoapp.core.TypeScreen
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.data.local.room.entity.TaskUiData
import kotlinx.coroutines.flow.StateFlow

interface HomeContract {

    interface ViewModel {
        val uiState: StateFlow<UiState>
        fun onAction(intent: Intent)
        fun update()
    }

    interface Direction {
        suspend fun openNewTask()
        suspend fun openEditTask(id: Int, typeScreen: TypeScreen = TypeScreen.Edit)
        suspend fun openCalendar()
    }

    sealed interface Intent {
        data object GetAllItem : Intent
        data object GetAllItemCount : Intent
        data object OpenNewTask : Intent
        data object OpenCalendar : Intent
        data class OpenEditTask(val data: Int) : Intent
        data class DeleteTask(val data: Int) : Intent
        data class UpdateTask(val data: Task) : Intent
        data class UpdateTaskItem(val data: TaskItem) : Intent
    }

    data class UiState(
        var listCount: List<Int> = listOf(0, 0, 0, 0),
        var dataList: List<TaskUiData> = mutableListOf()
    )
}
