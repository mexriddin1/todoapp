package com.example.todoapp.presentaton.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.core.ui.theme.CheckBoxBg
import com.example.todoapp.core.ui.theme.ComponentRadius

@Composable
fun CalendarDayItem(
    mainColor: Color = CheckBoxBg,
    title: String,
    day: String,
    click: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .height(70.dp)
            .background(
                Color.White,
                shape = ComponentRadius.middle
            )
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }
            ) {
                click.invoke(day.toInt())
            }
            .border(1.dp, mainColor, shape = ComponentRadius.middle),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = title, style = TextStyle(
                color = mainColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.W400
            )
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = day, style = TextStyle(
                color = mainColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.W600
            )
        )
        Spacer(Modifier.height(10.dp))

    }
}