package com.example.todoapp.data.local.room.entity

data class TaskUiData(
    val id: Int,
    val task: String,
    val type: String,
    val createTime: Long,
    val alertTime: Long?,
    val addedTime: Long?,
    val checked: Boolean,

    val list: List<TaskItem>
)