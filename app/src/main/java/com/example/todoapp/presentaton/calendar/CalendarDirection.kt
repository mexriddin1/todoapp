package com.example.todoapp.presentaton.calendar

import com.example.todoapp.utils.navigation.AppNavigator
import javax.inject.Inject

class CalendarDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : CalendarContract.Direction {

}
