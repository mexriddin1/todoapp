package com.example.todoapp.presentaton.new_task.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.todoapp.core.ui.theme.ButtonBg


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCustom(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    save: (Long?, Long?) -> Unit = { _, _ -> }
) {
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = Color.White,
            contentColor = ButtonBg,
            shape = RoundedCornerShape(7, 7, 0, 0),
            scrimColor = Color.White.copy(alpha = .3f),
            windowInsets = WindowInsets(0, 0, 0, 0),
        ) {

        }
    }
}


