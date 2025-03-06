package com.example.todoapp.domain.usecase

import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.data.local.room.entity.TaskUiData
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun getAllTask(): Flow<Result<List<TaskUiData>>>
    fun getTaskCount(): Flow<Result<List<Int>>>

    fun addNewTask(data: TaskUiData): Flow<Result<Unit>>

    fun editTask(data: Task): Flow<Result<Unit>>
    fun editRoot(data: TaskUiData): Flow<Result<Unit>>
    fun editTaskItem(data: TaskItem): Flow<Result<Unit>>

    fun deleteTask(data: Int): Flow<Result<Unit>>
    fun deleteTaskItem(id: Int): Flow<Result<Unit>>

    fun loadItem(data: Int): Flow<Result<TaskUiData>>
}