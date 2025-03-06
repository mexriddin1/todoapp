package com.example.todoapp.presentaton.home.component

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.core.Type
import com.example.todoapp.core.ui.theme.CheckBoxBg
import com.example.todoapp.core.ui.theme.OthersText
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem


@Composable
fun ListItem(
    task: Task,
    list: List<TaskItem>,
    modifier: Modifier,
    clickRoot: (Int) -> Unit = {},
    click: (Task) -> Unit,
    clickItem: (TaskItem) -> Unit
) {
    var isCheck by remember { mutableStateOf(task.checked) }
    LaunchedEffect(task.checked) {
        isCheck = task.checked
    }
    var state by remember { mutableStateOf(false) }
    val animationState by animateFloatAsState(
        targetValue = if (state) 180f else 0f, label = ""
    )

    Column(modifier = modifier
        .padding(top = 15.dp)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            clickRoot.invoke(task.id)
        }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = if (state) 0.dp else 25.dp)
                .background(Color.White)
        ) {
                CustomCheckBox(
                    check = isCheck
                ) {
                    isCheck = it
                    click.invoke(
                        task.copy(checked = isCheck)
                    )
                }
            Column {
                Text(
                    text = task.task, style = TextStyle(
                        color = if (isCheck) OthersText else Color.Black,
                        fontSize = 20.sp,
                        fontWeight = W400
                    )
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ListTypeItem(
                        if (!isCheck) when (task.type) {
                            Type.Health.type -> Type.Health
                            Type.Work.type -> Type.Work
                            Type.MentalHealth.type -> Type.MentalHealth
                            else -> Type.Others
                        } else null, task.type
                    )
                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )
                    ClockItem(task.alertTime)
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    if (list.isNotEmpty()) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .rotate(animationState)
                                .clickable {
                                    state = !state
                                },
                            tint = CheckBoxBg
                        )
                    }
                }
            }
        }

        if (state) {
            Column(modifier = Modifier.padding(start = 40.dp, end = 20.dp, bottom = 25.dp)) {
                repeat(list.size) {
                    val item = list[it]
                    var itemCheck by remember { mutableStateOf(item.check) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                            .background(Color.White), verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomCheckBox(check = itemCheck) {
                            itemCheck = it
                            clickItem.invoke(item.copy(check = itemCheck))
                        }

                        Text(
                            text = item.task, style = TextStyle(
                                color = if (itemCheck) OthersText else Color.Black,
                                fontSize = 20.sp,
                                fontWeight = W400
                            )
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(CheckBoxBg)
        )
    }

}

enum class DragAnchors {
    Start, End,
}
