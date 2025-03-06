package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.room.dao.TaskDao
import com.example.todoapp.data.local.room.dao.TaskItemDao
import com.example.todoapp.data.local.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @[Provides Singleton]
    fun providesAppDataBase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "table.db"
        ).build()

    @[Provides Singleton]
    fun providesTaskDao(db: AppDatabase): TaskDao = db.taskDao()

    @[Provides Singleton]
    fun providesTaskItemDao(db: AppDatabase): TaskItemDao = db.taskItemDao()
}