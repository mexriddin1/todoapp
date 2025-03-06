package com.example.todoapp.presentaton.home

import com.example.todoapp.core.TypeScreen
import com.example.todoapp.presentaton.calendar.CalendarScreen
import com.example.todoapp.presentaton.new_task.NewTaskScreen
import com.example.todoapp.utils.navigation.AppNavigator
import javax.inject.Inject

class HomeDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : HomeContract.Direction {
    override suspend fun openNewTask() {
        appNavigator.navigateTo(NewTaskScreen())
    }

    override suspend fun openEditTask(id: Int, typeScreen: TypeScreen) {
        appNavigator.navigateTo(NewTaskScreen(typeScreen, id))
    }

    override suspend fun openCalendar() {
        appNavigator.navigateTo(CalendarScreen())
    }

}
