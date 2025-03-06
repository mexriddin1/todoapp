package com.example.todoapp.di

import com.example.todoapp.presentaton.calendar.CalendarContract
import com.example.todoapp.presentaton.calendar.CalendarDirection
import com.example.todoapp.presentaton.home.HomeContract
import com.example.todoapp.presentaton.home.HomeDirection
import com.example.todoapp.presentaton.new_task.NewTaskContract
import com.example.todoapp.presentaton.new_task.NewTaskDirection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DirectionModule {

    @Binds
    fun bindHomeDirection(impl: HomeDirection): HomeContract.Direction

    @Binds
    fun bindNewTaskDirection(impl: NewTaskDirection): NewTaskContract.Direction

    @Binds
    fun bindCalendarDirection(impl: CalendarDirection): CalendarContract.Direction
}