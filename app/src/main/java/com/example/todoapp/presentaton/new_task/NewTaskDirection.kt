package com.example.todoapp.presentaton.new_task

import com.example.todoapp.presentaton.home.HomeScreen
import com.example.todoapp.utils.navigation.AppNavigator
import javax.inject.Inject

class NewTaskDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : NewTaskContract.Direction {
    override suspend fun back() {
        appNavigator.replace(HomeScreen())
    }
}
