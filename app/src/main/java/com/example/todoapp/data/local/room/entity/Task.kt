package com.example.todoapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val task: String,
    val type: String,
    val createTime: Long,
    val alertTime: Long?,
    val startTime: Long?,
    val endTime: Long? = null,
    val checked: Boolean
)