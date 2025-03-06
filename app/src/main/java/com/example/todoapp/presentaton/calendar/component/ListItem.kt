package com.example.todoapp.presentaton.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.core.Type
import com.example.todoapp.core.ui.theme.HealthBg
import com.example.todoapp.core.ui.theme.HealthText
import com.example.todoapp.core.ui.theme.MentalHealthBg
import com.example.todoapp.core.ui.theme.MentalHealthText
import com.example.todoapp.core.ui.theme.OthersBg
import com.example.todoapp.core.ui.theme.OthersText
import com.example.todoapp.core.ui.theme.WorkBg
import com.example.todoapp.core.ui.theme.WorkText
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem


@Composable
fun CalendarListItem(
    task: Task,
    list: List<TaskItem>,
    clickRoot: (Int) -> Unit = {},
) {
    Column(modifier = Modifier
        .padding(top = 15.dp)
        .fillMaxWidth()
        .background(
            when (task.type) {
                Type.Health.type -> HealthBg
                Type.Work.type -> WorkBg
                Type.MentalHealth.type -> MentalHealthBg
                else -> OthersBg
            }, shape = RoundedCornerShape(12.dp)
        )
        .padding(horizontal = 20.dp, vertical = 20.dp)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(17.dp)
                    .background(
                        when (task.type) {
                            Type.Health.type -> HealthText
                            Type.Work.type -> WorkText
                            Type.MentalHealth.type -> MentalHealthText
                            else -> OthersText
                        }, shape = RoundedCornerShape(20.dp)
                    )
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Text(
                text = task.task, style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = W500
                )
            )
        }

        Column(modifier = Modifier.padding(start = 35.dp, end = 20.dp)) {
            repeat(list.size) {
                val item = list[it]
                Text(
                    text = item.task, style = TextStyle(
                        color = OthersText,
                        fontSize = 20.sp,
                        fontWeight = W400
                    ), modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
