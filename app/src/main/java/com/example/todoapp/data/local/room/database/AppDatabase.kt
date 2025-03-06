package com.example.todoapp.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.local.room.dao.TaskDao
import com.example.todoapp.data.local.room.dao.TaskItemDao
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem


@Database(entities = [Task::class, TaskItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskItemDao(): TaskItemDao
}