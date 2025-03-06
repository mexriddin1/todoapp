package com.example.todoapp.domain.repository

import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.data.local.room.entity.TaskUiData

interface Repository {
    suspend fun getAllTask(): Result<List<TaskUiData>>
    suspend fun getTaskCount(): Result<List<Int>>

    suspend fun addNewTask(data: TaskUiData): Result<Unit>

    suspend fun editRoot(data: TaskUiData): Result<Unit>
    suspend fun editTask(data: Task): Result<Unit>
    suspend fun editTaskItem(data: TaskItem): Result<Unit>

    suspend fun deleteTask(data: Int): Result<Unit>

    suspend fun loadItem(data: Int): Result<TaskUiData>
    suspend fun deleteTaskItem(id: Int): Result<Unit>
}