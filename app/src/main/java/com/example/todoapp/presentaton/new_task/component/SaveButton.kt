package com.example.todoapp.presentaton.new_task.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.core.ui.theme.ButtonBg
import com.example.todoapp.core.ui.theme.ComponentRadius
import com.example.todoapp.core.ui.theme.OthersBg
import com.example.todoapp.core.ui.theme.OthersText


@Composable
fun SaveButton(
    state: Boolean = false,
    click: () -> Unit = {}
) {
    Button(
        {
            if (state) {
                click.invoke()
            }
        }, modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (state) ButtonBg else OthersBg
        ),
        shape = ComponentRadius.middle
    ) {
        Text(
            text = "Save", style = TextStyle(
                color = if (state) Color.White else OthersText,
                fontSize = 18.sp,
                fontWeight = W600
            )
        )
    }
}