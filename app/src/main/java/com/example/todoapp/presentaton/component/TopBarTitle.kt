package com.example.todoapp.presentaton.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.core.ui.theme.OthersText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TopBarTitle(name: String, day: Long, click: () -> Unit = {}) {
    val dateFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)
    val date = dateFormat.format(Date(day))
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            click.invoke()
        }
    ) {
        Text(
            text = name, style = TextStyle(
                color = Color.Black,
                fontSize = 35.sp,
                fontWeight = W800
            ), modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = date, style = TextStyle(
                color = OthersText,
                fontSize = 35.sp,
                fontWeight = W400
            )
        )
    }
}