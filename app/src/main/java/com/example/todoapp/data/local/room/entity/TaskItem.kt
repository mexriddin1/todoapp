package com.example.todoapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_item")
data class TaskItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val task: String,
    val createTime: Long,
    val check: Boolean,
    val taskId: Int

)