package com.example.todoapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id == :id")
    fun getTaskById(id: Int): Task

    @Query("DELETE FROM task WHERE id == :id")
    fun deleteTaskById(id: Int)

    @Query("SELECT * FROM task_item WHERE taskId == :id")
    fun getAllItemById(id: Int): List<TaskItem>

    @Query("DELETE FROM task_item WHERE taskId == :id")
    fun deleteAllItemById(id: Int)

    @Insert
    fun addTask(data: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    fun updateTask(data: Task): Long
}