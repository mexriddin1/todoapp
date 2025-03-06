package com.example.todoapp.presentaton.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.core.ui.theme.ComponentRadius
import com.example.todoapp.core.ui.theme.OthersBg
import com.example.todoapp.core.ui.theme.OthersText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ClockItem(
    time: Long?
) {
    if (time != null) {
        Box(
            modifier = Modifier
                .background(
                    OthersBg, shape = ComponentRadius.small
                )
                .padding(5.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.time),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .height(15.dp)
                        .width(15.dp),
                    tint = OthersText
                )
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val now = dateFormat.format(Date(time)).also {
                    Text(
                        text = it, style = TextStyle(
                            color = OthersText, fontSize = 18.sp, fontWeight = W500
                        )
                    )
                }
            }
        }
    }
}