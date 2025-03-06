package com.example.todoapp.presentaton.calendar

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.todoapp.core.Type
import com.example.todoapp.core.ui.theme.ButtonBg
import com.example.todoapp.core.ui.theme.CheckBoxBg
import com.example.todoapp.core.ui.theme.OthersBg
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.presentaton.calendar.component.CalendarDayItem
import com.example.todoapp.presentaton.calendar.component.CalendarListItem
import com.example.todoapp.presentaton.component.TopBarTitle
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class CalendarScreen : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: CalendarContract.ViewModel = getViewModel<CalendarViewModel>()
        val uiState = viewModel.uiState.collectAsState()

        CalendarScreenContent(uiState, viewModel::onAction)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreenContent(
    uiState: State<CalendarContract.UiState> = remember { mutableStateOf(CalendarContract.UiState()) },
    onAction: (CalendarContract.Intent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppBar(
                title = {
                    TopBarTitle("Calendar", System.currentTimeMillis()) {}
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            var index by remember { mutableIntStateOf(LocalDate.now().dayOfMonth) }
            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()
            scope.launch {
                listState.scrollToItem(
                    index = LocalDate.now().dayOfMonth - 1,
                    scrollOffset = LocalDate.now().dayOfMonth - 1
                )
            }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.height(90.dp),
                verticalAlignment = Alignment.CenterVertically,
                state = listState
            ) {
                item {
                    Box(modifier = Modifier.width(8.dp))
                }

                items(getCurrentMonthDates()) { i ->
//                    if (LocalDate.now().dayOfMonth <= i.second) {
                    CalendarDayItem(
                        if (index == i.second && LocalDate.now().dayOfMonth <= i.second) ButtonBg else (if (LocalDate.now().dayOfMonth >  i.second) OthersBg else CheckBoxBg),
                        title = i.first,
                        i.second.toString()
                    ) {
                        if (LocalDate.now().dayOfMonth <= i.second) {
                            index = it
                        }
                    }
//                    }
                }

                item {
                    Box(modifier = Modifier.width(8.dp))
                }
            }

            rememberCoroutineScope()

            LazyColumn {
                items(2) {
                    CalendarListItem(
                        task = Task(
                            id = 0,
                            task = "This is test task",
                            type = Type.Health.type,
                            createTime = System.currentTimeMillis(),
                            alertTime = null,
                            startTime = null,
                            checked = false,
                        ),
                        list = listOf(
                            TaskItem(
                                0,
                                task = "This is test task 1",
                                createTime = System.currentTimeMillis(),
                                false,
                                0
                            ),
                            TaskItem(
                                0,
                                task = "This is test task 2",
                                createTime = System.currentTimeMillis(),
                                false,
                                0
                            ), TaskItem(
                                0,
                                task = "This is test task 2",
                                createTime = System.currentTimeMillis(),
                                false,
                                0
                            )
                        )
                    ) {

                    }
                }

            }
        }
    }
}

fun getCurrentMonthDates(): List<Pair<String, Int>> {
    val datesInMonth = mutableListOf<Pair<String, Int>>()
    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH)

    val englishLocale = Locale.ENGLISH
    val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, englishLocale) ?: ""

    calendar.set(Calendar.DAY_OF_MONTH, 1)
    while (calendar.get(Calendar.MONTH) == currentMonth) {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        datesInMonth.add(Pair(monthName.uppercase(englishLocale), day))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return datesInMonth
}

@Preview
@Composable
fun CalendarScreenPreview() {
//    CalendarScreenContent()
}