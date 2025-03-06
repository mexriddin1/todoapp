package com.example.todoapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem

@Dao
interface TaskItemDao {
    @Insert
    fun addTaskItem(data: TaskItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateTaskItem(data: TaskItem)

    @Query("DELETE FROM task_item WHERE id = :data")
    fun removeTaskItem(data: Int)
}