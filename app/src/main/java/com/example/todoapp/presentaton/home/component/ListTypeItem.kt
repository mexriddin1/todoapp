package com.example.todoapp.presentaton.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.core.Type
import com.example.todoapp.core.ui.theme.ButtonBg
import com.example.todoapp.core.ui.theme.CheckBoxBg
import com.example.todoapp.core.ui.theme.ComponentRadius
import com.example.todoapp.core.ui.theme.HealthBg
import com.example.todoapp.core.ui.theme.HealthText
import com.example.todoapp.core.ui.theme.MentalHealthBg
import com.example.todoapp.core.ui.theme.MentalHealthText
import com.example.todoapp.core.ui.theme.OthersBg
import com.example.todoapp.core.ui.theme.OthersText
import com.example.todoapp.core.ui.theme.WorkBg
import com.example.todoapp.core.ui.theme.WorkText

@Composable
fun ListTypeItem(
    type: Type?,
    text: String = "",
    click: () -> Unit = {},
    isChecked: Boolean = false
) {
    Box(
        modifier = Modifier
            .background(
                when (type) {
                    Type.Health -> HealthBg
                    Type.Work -> WorkBg
                    Type.MentalHealth -> MentalHealthBg
                    else -> OthersBg
                }, shape = ComponentRadius.small
            )
            .border(
                1.dp,
                if (isChecked) CheckBoxBg else Color.Transparent,
                shape = ComponentRadius.small
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                click.invoke()
            }
            .padding(5.dp),
    ) {
        Text(
            text = (type?.type ?: text).toUpperCase(), style = TextStyle(
                color = when (type) {
                    Type.Health -> HealthText
                    Type.Work -> WorkText
                    Type.MentalHealth -> MentalHealthText
                    else -> OthersText
                }, fontSize = 18.sp, fontWeight = W500
            )
        )
    }
}