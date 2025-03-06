package com.example.todoapp.presentaton.new_task

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.todoapp.R
import com.example.todoapp.core.Type
import com.example.todoapp.core.TypeScreen
import com.example.todoapp.core.ui.theme.CheckBoxBg
import com.example.todoapp.core.ui.theme.ComponentRadius
import com.example.todoapp.core.ui.theme.OthersBg
import com.example.todoapp.core.ui.theme.OthersText
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.data.local.room.entity.TaskUiData
import com.example.todoapp.data.servis.saveTaskAndScheduleAlarm
import com.example.todoapp.presentaton.home.component.CustomCheckBox
import com.example.todoapp.presentaton.home.component.ListTypeItem
import com.example.todoapp.presentaton.new_task.component.BottomSheetCustom
import com.example.todoapp.presentaton.new_task.component.SaveButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NewTaskScreen(private val screenType: TypeScreen = TypeScreen.Add, private val id: Int = -1) :
    Screen {
    @Composable
    override fun Content() {
        val viewModel: NewTaskContract.ViewModel = getViewModel<NewTaskViewModel>()
        val uiState = viewModel.uiState.collectAsState()

        if (screenType == TypeScreen.Edit) {
            viewModel.onAction(NewTaskContract.Intent.LoadTask(id))
        }

        NewTaskScreenContent(uiState, viewModel::onAction, screenType)

        BackHandler(enabled = true) {
            viewModel.onAction(NewTaskContract.Intent.Back)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun NewTaskScreenContent(
    uiState: State<NewTaskContract.UiState> = remember { mutableStateOf(NewTaskContract.UiState()) },
    onAction: (NewTaskContract.Intent) -> Unit = {},
    screenType: TypeScreen = TypeScreen.Add
) {
    var title: String by remember {
        mutableStateOf(uiState.value.title)
    }
    val list = remember { uiState.value.list }
    var count by remember { mutableIntStateOf(0) }
    var type by remember { mutableStateOf(Type.Others) }
    var alertTime: Long? by remember { mutableStateOf(null) }
    val addedTime: Long? by remember { mutableStateOf(null) }

    if (count <= 1) {
        count++
        if (count == 2) {
            if (screenType == TypeScreen.Edit) title = uiState.value.title

            list.clear()

            try {
                type = when (uiState.value.task!!.type) {
                    Type.Health.type -> Type.Health
                    Type.Work.type -> Type.Work
                    Type.MentalHealth.type -> Type.MentalHealth
                    else -> Type.Others
                }

                list.addAll(uiState.value.task!!.list.map {
                    Pair(it.check, it.task)
                })
            } catch (_: Exception) {
            }

            list.add(Pair(false, ""))
        }
    }

    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Text(text = count.toString(), color = Color.Transparent)

    BottomSheetCustom(isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        onDismiss = {
            isBottomSheetVisible = false
        }, save = { _, _ -> })

    Box(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(22.dp)
    ) {

        Box(modifier = Modifier
            .padding(5.dp)
            .background(Color.Transparent)
            .size(22.dp)
            .align(Alignment.TopEnd)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            {
                onAction.invoke(NewTaskContract.Intent.Back)
            }) {

            Icon(
                painter = painterResource(R.drawable.close),
                contentDescription = null,
            )

        }


        LazyColumn(
            modifier = Modifier
                .padding(top = 30.dp)
        ) {
            item {
                Column {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                    )

                    BasicTextField(
                        value = title, onValueChange = { title = it }, textStyle = TextStyle(
                            color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.W500
                        ), modifier = Modifier.fillMaxWidth(),

                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    ) { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 52.dp)
                        ) {
                            if (title.isEmpty()) {
                                Text(
                                    "Write a new task...", style = TextStyle(
                                        color = CheckBoxBg,
                                        fontWeight = FontWeight.W500,
                                        fontSize = 32.sp,
                                    ), modifier = Modifier.width(300.dp)
                                )
                            }

                            innerTextField()
                        }
                    }

                    key(count) {
                        Column {
                            if (title.isNotBlank() || (list.isNotEmpty() && list[0].second.isNotBlank())) {
                                repeat(list.size) { i ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 20.dp, start = 15.dp)
                                            .background(Color.White),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        CustomCheckBox(check = list[i].first) {
                                            list[i] = Pair(it, list[i].second)
                                        }

                                        BasicTextField(
                                            value = list[i].second,
                                            onValueChange = {
                                                list[i] = Pair(list[i].first, it)

                                                if (it.isBlank() && list.size >= 2) {
                                                    list.removeAt(i)
                                                }
                                                if (i == list.size - 1 && it.isNotBlank()) {
                                                    list.add(Pair(false, ""))
                                                }
                                            },
                                            textStyle = TextStyle(
                                                color = if (list[i].first) OthersText else Color.Black,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.W500
                                            ),
                                            modifier = Modifier.fillMaxWidth(),
                                            keyboardOptions = KeyboardOptions(
                                                imeAction = ImeAction.Next
                                            )
                                        ) { innerTextField ->
                                            Box(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                if (list[i].second.isEmpty()) {
                                                    Text(
                                                        "Add subtask", style = TextStyle(
                                                            color = CheckBoxBg,
                                                            fontWeight = FontWeight.W500,
                                                            fontSize = 20.sp
                                                        )
                                                    )
                                                }

                                                innerTextField()
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter).background(Color.White)) {
            val context = LocalContext.current

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 12.dp, top = 10.dp)
            ) {
                if (title.isNotBlank()) {
                    item {
                        ListTypeItem(null, Type.Others.type, click = {
                            type = Type.Others
                        }, isChecked = type == Type.Others)
                    }

                    item {
                        ListTypeItem(null, Type.Health.type, click = {
                            type = Type.Health
                        }, isChecked = type == Type.Health)
                    }

                    item {
                        ListTypeItem(null, Type.Work.type, click = {
                            type = Type.Work
                        }, isChecked = type == Type.Work)
                    }


                    item {
                        ListTypeItem(null, Type.MentalHealth.type, click = {
                            type = Type.MentalHealth
                        }, isChecked = type == Type.MentalHealth)
                    }
                }
            }

            Row {
                if (screenType == TypeScreen.Add) {
                    Box(modifier = Modifier
                        .height(60.dp)
                        .clip(ComponentRadius.middle)
                        .width(60.dp)
                        .background(OthersBg)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            val calendar = Calendar.getInstance()

                            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                            val currentMinute = calendar.get(Calendar.MINUTE)

                            val timeSetListener =
                                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                                    val now = dateFormat.format(Date(System.currentTimeMillis()))

                                    if (hourOfDay < currentHour || (hourOfDay == currentHour && minute < currentMinute)) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Tanlangan vaqt hozirgi vaqtdan keyin bo'lishi kerak : $now",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        return@OnTimeSetListener
                                    }

                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    calendar.set(Calendar.MINUTE, minute)

                                    val selectedTimeInMillis = calendar.timeInMillis
                                    Log.d(
                                        "WWW",
                                        "Tanlangan vaqt (Long format): $selectedTimeInMillis"
                                    )


                                    alertTime = selectedTimeInMillis

                                    Toast
                                        .makeText(
                                            context,
                                            "Tanlangan vaqt o'rnatildi : ${
                                                dateFormat.format(
                                                    Date(selectedTimeInMillis)
                                                )
                                            }",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()

                                }

                            val timePickerDialog = TimePickerDialog(
                                context,
                                timeSetListener,
                                currentHour,
                                currentMinute,
                                true
                            )

                            timePickerDialog.show()

                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.clock),
                            contentDescription = null,
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                                .size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                }

                SaveButton(state = title.isNotBlank()) {
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    val data = TaskUiData(
                        id = 0,
                        task = title,
                        type = type.type,
                        createTime = System.currentTimeMillis(),
                        alertTime = alertTime,
                        addedTime = addedTime,
                        checked = false,
                        list = list.map {
                            TaskItem(
                                0,
                                it.second,
                                System.currentTimeMillis(),
                                it.first,
                                0,
                            )
                        }
                    )

                    if (screenType == TypeScreen.Add) {
                        saveTaskAndScheduleAlarm(context, data)
                    }
                    onAction.invoke(
                        if (screenType == TypeScreen.Add) {
                            NewTaskContract.Intent.AddNewTask(
                                data
                            )
                        } else {
                            NewTaskContract.Intent.EditNewTask(
                                data
                            )
                        }
                    )
                }
            }
        }
    }
}




@Preview
@Composable
fun NewTaskScreenPreview() {
    NewTaskScreenContent()
}